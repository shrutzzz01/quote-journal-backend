package com.quotejournal.dto;

import com.quotejournal.entity.Tag;
import jakarta.validation.constraints.NotBlank;

public record QuoteRequest(
        @NotBlank(message = "content is required")
        String content,
        Tag tag,
        boolean isPublic
)
{}
