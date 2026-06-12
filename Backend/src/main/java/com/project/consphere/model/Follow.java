package com.project.consphere.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "follow")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "followerId")
    private User follower;

    @ManyToOne
    @JoinColumn(name = "followingId")
    private User following;
}
