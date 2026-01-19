package com.quotejournal.dto;

import com.quotejournal.entity.User;

import java.util.*;
public record AdminDashboardResponse(
        List<User> allUsers,
        long totalUsers,
        long unverifiedUsers,
        long totalQuotes,
        long publicQuotes,
        long privateQuotes,
        Map<String, Long> topTags

) {}
