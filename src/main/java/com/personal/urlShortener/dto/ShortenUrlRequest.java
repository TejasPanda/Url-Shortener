package com.personal.urlShortener.dto;

import lombok.Data;

@Data
public class ShortenUrlRequest {

    private String longUrl;

    // optional
    private String customAlias;

    // optional (minutes)
    private Long expiryMinutes;
}
