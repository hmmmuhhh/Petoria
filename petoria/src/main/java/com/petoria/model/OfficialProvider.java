package com.petoria.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "official_providers")
@Getter
public class OfficialProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type; // SHOP or SHELTER

    private String phone;

    private String location;

    @Column(name = "website_url")
    private String websiteUrl;

    @Column(name = "logo_url")
    private String logoUrl;
}
