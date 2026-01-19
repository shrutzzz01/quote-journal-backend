package com.quotejournal.dto;

import com.quotejournal.entity.Tag;

import java.time.LocalDateTime;

public record QuoteResponse(
        Long quoteId,
        String content,
        Tag tag,
        LocalDateTime createdAt,
        boolean isPublic
)
{}
