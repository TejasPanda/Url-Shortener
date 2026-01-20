package com.personal.urlShortener.controller;

import com.personal.urlShortener.dto.ShortenUrlRequest;
import com.personal.urlShortener.dto.UrlStatsResponse;
import com.personal.urlShortener.service.UrlService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
public class UrlController {

    private final UrlService service;

    public UrlController(UrlService service) {
        this.service = service;
    }



    @PostMapping("/shorten")
    public Map<String, String> shorten(
            @RequestBody ShortenUrlRequest request,
            HttpServletRequest httpRequest) {

        String shortCode = service.shortenUrl(request);

        String baseUrl =
                httpRequest.getScheme() + "://" +
                        httpRequest.getServerName() +
                        (httpRequest.getServerPort() == 80 || httpRequest.getServerPort() == 443
                                ? ""
                                : ":" + httpRequest.getServerPort());

        return Map.of(
                "shortUrl", baseUrl + "/" + shortCode
        );
    }

    @GetMapping("/favicon.ico")
    @ResponseBody
    public void ignoreFavicon() {
        // do nothing
    }


    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {

        String longUrl = service.getOriginalUrl(shortCode);

        return ResponseEntity
                .status(HttpStatus.FOUND)   // 302
                .header(HttpHeaders.LOCATION, longUrl)
                .build();
    }
    @GetMapping("/stats/{shortCode}")
    public UrlStatsResponse getStats(@PathVariable String shortCode) {
        return service.getStats(shortCode);
    }



}
