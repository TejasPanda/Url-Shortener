package com.personal.urlShortener.service;

import com.personal.urlShortener.dto.ShortenUrlRequest;
import com.personal.urlShortener.dto.UrlStatsResponse;
import com.personal.urlShortener.entity.UrlMapping;
import com.personal.urlShortener.repository.UrlRepository;
import com.personal.urlShortener.util.Base62Encoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class UrlService {

    private final UrlRepository repository;
    private final Base62Encoder encoder;

    public UrlService(UrlRepository repository, Base62Encoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public String shortenUrl(ShortenUrlRequest request) {

        UrlMapping mapping = new UrlMapping();
        mapping.setLongUrl(request.getLongUrl());

        // Expiry
        if (request.getExpiryMinutes() != null) {
            mapping.setExpiresAt(
                    Instant.now().plusSeconds(request.getExpiryMinutes() * 60)
            );


        }

        // Save first to generate ID
        repository.save(mapping);

        // 🔹 Custom alias OR Base62
        String shortCode;
        if (request.getCustomAlias() != null) {
            if (repository.existsByShortCode(request.getCustomAlias())) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Alias already exists"
                );

            }
            shortCode = request.getCustomAlias();
        } else {
            shortCode = encoder.encode(mapping.getId());
        }

        mapping.setShortCode(shortCode);
        repository.save(mapping);

        return shortCode;
    }



    @Transactional
    public String getOriginalUrl(String shortCode) {



        UrlMapping mapping = repository.findByShortCode(shortCode)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "URL not found"
                ));



        if (mapping.getExpiresAt() != null &&
                mapping.getExpiresAt().isBefore(Instant.now())) {

            throw new ResponseStatusException(
                    HttpStatus.GONE,
                    "URL expired"
            );
        }




        mapping.setClickCount(mapping.getClickCount() + 1);
        repository.save(mapping);



        return mapping.getLongUrl();
    }
    public UrlStatsResponse getStats(String shortCode) {

        UrlMapping mapping = repository.findByShortCode(shortCode)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "URL not found"
                ));

        boolean expired = mapping.getExpiresAt() != null &&
                mapping.getExpiresAt().isBefore(Instant.now());

        return new UrlStatsResponse(
                mapping.getShortCode(),
                mapping.getLongUrl(),
                mapping.getClickCount(),
                mapping.getCreatedAt(),
                mapping.getExpiresAt(),
                expired
        );
    }


}
