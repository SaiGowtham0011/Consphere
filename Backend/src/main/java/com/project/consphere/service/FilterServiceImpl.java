package com.project.consphere.service;

import com.project.consphere.dto.CreateFilterRequest;
import com.project.consphere.dto.FilterResponse;
import com.project.consphere.dto.PostResponse;
import com.project.consphere.dto.UpdateFilterRequest;
import com.project.consphere.exception.BadRequestException;
import com.project.consphere.exception.ResourceNotFoundException;
import com.project.consphere.exception.UnauthorizedException;
import com.project.consphere.model.Filter;
import com.project.consphere.model.Hashtag;
import com.project.consphere.model.Post;
import com.project.consphere.model.User;
import com.project.consphere.repository.FilterRepository;
import com.project.consphere.repository.HashtagRepository;
import com.project.consphere.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilterServiceImpl implements FilterService {

    public static final int MIN_HASHTAGS = 3;
    public static final int MAX_HASHTAGS = 10;

    @Autowired
    private FilterRepository filterRepository;

    @Autowired
    private HashtagRepository hashtagRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostServiceImpl postServiceImpl;

    @Override
    @Transactional
    public FilterResponse createFilter(CreateFilterRequest request) {
        User currentUser = userService.getCurrentUser();

        validateHashtagsCount(request.getHashtags());

        if (filterRepository.existsByNameAndOwnerId(request.getName().trim(), currentUser.getId())) {
            throw new BadRequestException("Filter with name '" + request.getName() + "' already exists");
        }

        Set<Hashtag> hashtags = processHashtags(request.getHashtags());

        Filter filter = Filter.builder()
                .name(request.getName().trim())
                .description(request.getDescription())
                .builtIn(false)
                .owner(currentUser)
                .hashtags(hashtags)
                .build();

        Filter savedFilter = filterRepository.save(filter);
        return convertToFilterResponse(savedFilter);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilterResponse> getAllFilters() {
        User currentUser = null;
        try {
            currentUser = userService.getCurrentUser();
        } catch (Exception e) {
            // Unauthenticated user sees built-in filters only
        }

        List<Filter> filters;
        if (currentUser != null) {
            filters = filterRepository.findByBuiltInTrueOrOwnerId(currentUser.getId());
        } else {
            filters = filterRepository.findByBuiltInTrue();
        }

        return filters.stream()
                .map(this::convertToFilterResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilterResponse> getMyFilters() {
        User currentUser = userService.getCurrentUser();
        List<Filter> filters = filterRepository.findByOwnerId(currentUser.getId());
        return filters.stream()
                .map(this::convertToFilterResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilterResponse> getBuiltInFilters() {
        List<Filter> filters = filterRepository.findByBuiltInTrue();
        return filters.stream()
                .map(this::convertToFilterResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public FilterResponse getFilterById(Long id) {
        Filter filter = filterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Filter not found with ID: " + id));

        return convertToFilterResponse(filter);
    }

    @Override
    @Transactional
    public FilterResponse updateFilter(Long id, UpdateFilterRequest request) {
        User currentUser = userService.getCurrentUser();
        Filter filter = filterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Filter not found with ID: " + id));

        if (filter.isBuiltIn()) {
            throw new BadRequestException("Built-in filters cannot be modified");
        }

        if (!filter.getOwner().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You are not authorized to update this filter");
        }

        validateHashtagsCount(request.getHashtags());

        filter.setName(request.getName().trim());
        filter.setDescription(request.getDescription());

        Set<Hashtag> hashtags = processHashtags(request.getHashtags());
        filter.setHashtags(hashtags);

        Filter updatedFilter = filterRepository.save(filter);
        return convertToFilterResponse(updatedFilter);
    }

    @Override
    @Transactional
    public void deleteFilter(Long id) {
        User currentUser = userService.getCurrentUser();
        Filter filter = filterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Filter not found with ID: " + id));

        if (filter.isBuiltIn()) {
            throw new BadRequestException("Built-in filters cannot be deleted");
        }

        if (!filter.getOwner().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You are not authorized to delete this filter");
        }

        filterRepository.delete(filter);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getFilterFeed(Long id) {
        Filter filter = filterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Filter not found with ID: " + id));

        User currentUser = null;
        try {
            currentUser = userService.getCurrentUser();
        } catch (Exception e) {
            // Unauthenticated view
        }

        if (!filter.isBuiltIn()) {
            if (currentUser == null || !filter.getOwner().getId().equals(currentUser.getId())) {
                throw new UnauthorizedException("You do not have access to this custom filter");
            }
        }

        Set<Hashtag> filterHashtags = filter.getHashtags();
        if (filterHashtags.isEmpty()) {
            return Collections.emptyList();
        }

        List<Post> posts = postRepository.findDistinctByHashtagsInOrderByCreatedAtDesc(filterHashtags);

        User finalCurrentUser = currentUser;
        return posts.stream()
                .map(post -> postServiceImpl.convertToPostResponse(post, finalCurrentUser))
                .collect(Collectors.toList());
    }

    private void validateHashtagsCount(List<String> hashtags) {
        if (hashtags == null || hashtags.size() < MIN_HASHTAGS || hashtags.size() > MAX_HASHTAGS) {
            throw new BadRequestException("A filter must contain between " + MIN_HASHTAGS + " and " + MAX_HASHTAGS + " hashtags");
        }
    }

    private Set<Hashtag> processHashtags(List<String> hashtagNames) {
        Set<Hashtag> hashtags = new HashSet<>();
        for (String rawName : hashtagNames) {
            if (rawName == null || rawName.isBlank()) continue;
            String cleanName = rawName.replaceAll("^#+", "").trim().toLowerCase();
            if (cleanName.isBlank()) continue;

            Hashtag hashtag = hashtagRepository.findByName(cleanName)
                    .orElseGet(() -> hashtagRepository.save(Hashtag.builder().name(cleanName).build()));
            hashtags.add(hashtag);
        }
        return hashtags;
    }

    private FilterResponse convertToFilterResponse(Filter filter) {
        List<String> hashtagNames = filter.getHashtags().stream()
                .map(Hashtag::getName)
                .sorted()
                .collect(Collectors.toList());

        String ownerUsername = filter.getOwner() != null ? filter.getOwner().getUsername() : "SYSTEM";

        return FilterResponse.builder()
                .id(filter.getId())
                .name(filter.getName())
                .description(filter.getDescription())
                .builtIn(filter.isBuiltIn())
                .hashtags(hashtagNames)
                .ownerUsername(ownerUsername)
                .build();
    }
}
