package com.personal.urlShortener.entity;

import jakarta.persistence.*;
import lombok.Data;



import java.time.Instant;

@Data
@Entity
@Table(name = "url_mapping")
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String shortCode;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String longUrl;

    @Column(nullable = false)
    private Long clickCount = 0L;

    private Instant expiresAt;

    private Instant createdAt = Instant.now();
}
