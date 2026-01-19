package com.quotejournal.repository;

import com.quotejournal.entity.Quote;
import com.quotejournal.entity.Tag;
import com.quotejournal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
    List<Quote> findByContentContainingIgnoreCaseAndIsPublicTrue(String content);
    List<Quote> findByContentContainingIgnoreCaseAndUser(String content, User user);
    List<Quote> findByTagAndUser(Tag tag, User user);
    List<Quote> findByIsPublicTrue();
    List<Quote> findByIsPublicFalseAndUser(User user);
    List<Quote> findByTagAndIsPublicTrue(Tag tag);
    List<Quote> findByUser(User user);
    Quote findByQuoteIdAndUser(Long quoteId, User user);
    long countByIsPublic(boolean isPublic);
}
