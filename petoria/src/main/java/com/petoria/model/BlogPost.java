package com.petoria.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "blog_post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000)
    private String content;

    @ElementCollection
    @CollectionTable(name = "blog_post_images", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "image_path")
    private List<String> imagePaths;

    @ElementCollection
    @CollectionTable(name = "blog_post_videos", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "video_path")
    private List<String> videoPaths;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;
}
