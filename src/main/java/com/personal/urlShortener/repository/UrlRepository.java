package com.personal.urlShortener.repository;

import com.personal.urlShortener.entity.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlMapping, Long> {

    Optional<UrlMapping> findByShortCode(String shortCode);
    boolean existsByShortCode(String shortCode);
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update UrlMapping u set u.clickCount = u.clickCount + 1 where u.shortCode = :code")
    int incrementClickCount(@Param("code") String code);




}
