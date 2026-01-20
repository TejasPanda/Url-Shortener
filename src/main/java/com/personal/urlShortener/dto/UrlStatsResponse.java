package com.personal.urlShortener.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class UrlStatsResponse {

    private String shortCode;
    private String longUrl;
    private Long clickCount;
    private Instant createdAt;
    private Instant expiresAt;
    private boolean expired;
}
