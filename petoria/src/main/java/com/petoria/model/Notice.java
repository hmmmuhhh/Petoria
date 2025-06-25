package com.petoria.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lost_and_found_notice")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String location;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "notice_photos", joinColumns = @JoinColumn(name = "notice_id"))
    @Column(name = "photo_path")
    private List<String> photoPaths = new ArrayList<>();

    @Column(name = "is_found")
    private boolean found;

    @Enumerated(EnumType.STRING)
    private NoticeType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
