package com.quotejournal.dto;
import com.quotejournal.entity.Role;
public record AdminUserRequest(
    Long userId,
    String role
) {
}
