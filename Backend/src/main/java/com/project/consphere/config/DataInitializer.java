package com.project.consphere.config;

import com.project.consphere.model.*;
import com.project.consphere.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private FilterRepository filterRepository;

    @Autowired
    private HashtagRepository hashtagRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Pattern HASHTAG_PATTERN = Pattern.compile("#([A-Za-z0-9_]+)");

    @Override
    public void run(String... args) throws Exception {
        initializeBuiltInFilters();
        initializeSampleUsersAndPosts();
    }

    private void initializeBuiltInFilters() {
        createBuiltInFilterIfAbsent("Programming", "Posts related to coding, DSA, Java, and software development",
                Arrays.asList("reactjs", "coding", "webdev", "programming", "javascript"));

        createBuiltInFilterIfAbsent("Education", "Academic content, placement prep, and study notes",
                Arrays.asList("education", "learning", "study", "exam", "placement"));

        createBuiltInFilterIfAbsent("Entertainment", "Memes, movies, anime, and fun content",
                Arrays.asList("movies", "netflix", "recommendations", "memes", "anime"));

        createBuiltInFilterIfAbsent("Sports", "Cricket, football, and outdoor sports updates",
                Arrays.asList("sports", "football", "fitness", "cricket", "match"));

        createBuiltInFilterIfAbsent("Fitness", "Gym workouts, healthy lifestyle, and diet tips",
                Arrays.asList("fitness", "morningrun", "healthylifestyle", "gym", "workout"));

        createBuiltInFilterIfAbsent("Technology", "AI, gadgets, cloud computing, and tech news",
                Arrays.asList("tech", "ai", "coding", "python", "cloud", "javascript"));

        createBuiltInFilterIfAbsent("Gaming", "eSports, PC gaming, consoles, and game releases",
                Arrays.asList("gaming", "esports", "playstation", "gamer", "steam"));

        createBuiltInFilterIfAbsent("Music", "Songs, albums, live concerts, and beats",
                Arrays.asList("music", "songs", "artist", "beats", "melody"));

        createBuiltInFilterIfAbsent("Movies", "Cinema reviews, trailers, and box office discussion",
                Arrays.asList("movies", "cinema", "hollywood", "bollywood", "netflix"));
    }

    private void createBuiltInFilterIfAbsent(String name, String description, List<String> tagNames) {
        if (!filterRepository.existsByNameAndBuiltInTrue(name)) {
            Set<Hashtag> hashtags = new HashSet<>();
            for (String tag : tagNames) {
                Hashtag hashtag = hashtagRepository.findByName(tag.toLowerCase())
                        .orElseGet(() -> hashtagRepository.save(Hashtag.builder().name(tag.toLowerCase()).build()));
                hashtags.add(hashtag);
            }

            Filter filter = Filter.builder()
                    .name(name)
                    .description(description)
                    .builtIn(true)
                    .owner(null)
                    .hashtags(hashtags)
                    .build();

            filterRepository.save(filter);
        }
    }

    private void initializeSampleUsersAndPosts() {
        // Create default user Lohith if not existing
        User lohith = userRepository.findByUsername("lohith_dev").orElseGet(() -> {
            User u = User.builder()
                    .username("lohith_dev")
                    .password(passwordEncoder.encode("password123"))
                    .email("lohith@example.com")
                    .firstName("Lohith")
                    .lastName("Dev")
                    .profilePicURL("https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=150")
                    .build();
            return userRepository.save(u);
        });

        // Sample Creator 1: Ananya Sharma
        User ananya = userRepository.findByUsername("ananya_s").orElseGet(() -> {
            User u = User.builder()
                    .username("ananya_s")
                    .password(passwordEncoder.encode("password123"))
                    .email("ananya@example.com")
                    .firstName("Ananya")
                    .lastName("Sharma")
                    .profilePicURL("https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=150")
                    .build();
            return userRepository.save(u);
        });

        // Sample Creator 2: Rohan Verma
        User rohan = userRepository.findByUsername("rohan_v").orElseGet(() -> {
            User u = User.builder()
                    .username("rohan_v")
                    .password(passwordEncoder.encode("password123"))
                    .email("Rohan")
                    .lastName("Verma")
                    .profilePicURL("https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?w=150")
                    .build();
            return userRepository.save(u);
        });

        // Sample Creator 3: Neha Singh
        User neha = userRepository.findByUsername("neha_singh").orElseGet(() -> {
            User u = User.builder()
                    .username("neha_singh")
                    .password(passwordEncoder.encode("password123"))
                    .email("neha@example.com")
                    .firstName("Neha")
                    .lastName("Singh")
                    .profilePicURL("https://images.unsplash.com/photo-1438761681033-6461ffad8d80?w=150")
                    .build();
            return userRepository.save(u);
        });

        // Seed Sample Posts if empty
        if (postRepository.count() == 0) {
            createSamplePost(ananya,
                    "Just completed a great project in React! 🚀 #reactjs #coding #webdev #programming",
                    "https://images.unsplash.com/photo-1555066931-4365d14bab8c?w=1000");

            createSamplePost(rohan,
                    "Early morning runs hit different! 🏃‍♂️ 🇮🇳 #fitness #morningrun #healthylifestyle #sports",
                    "https://images.unsplash.com/photo-1476480862126-209bfaa8edc8?w=1000");

            createSamplePost(neha,
                    "Movie night recommendations? 🍿 #movies #netflix #recommendations #entertainment",
                    "https://images.unsplash.com/photo-1489599849927-2ee91cede3ba?w=1000");
        }
    }

    private void createSamplePost(User user, String caption, String imageUrl) {
        Set<Hashtag> hashtags = new HashSet<>();
        Matcher matcher = HASHTAG_PATTERN.matcher(caption);
        while (matcher.find()) {
            String tagName = matcher.group(1).toLowerCase();
            Hashtag hashtag = hashtagRepository.findByName(tagName)
                    .orElseGet(() -> hashtagRepository.save(Hashtag.builder().name(tagName).build()));
            hashtags.add(hashtag);
        }

        Post post = Post.builder()
                .user(user)
                .caption(caption)
                .imageUrl(imageUrl)
                .hashtags(hashtags)
                .build();

        postRepository.save(post);
    }
}
