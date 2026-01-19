package com.quotejournal.controller;

import com.quotejournal.dto.QuoteRequest;
import com.quotejournal.dto.QuoteResponse;
import com.quotejournal.entity.Tag;
import com.quotejournal.service.QuoteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {
    private final QuoteService quoteService;
    public QuoteController(QuoteService quoteService){
        this.quoteService=quoteService;
    }
    @PostMapping
    public QuoteResponse create(@RequestBody QuoteRequest quoteRequest){
        try {
            return quoteService.createQuote(quoteRequest);
        } catch (Exception e) {
            System.err.println("Authentication failed. Exception type: " + e.getClass().getName());
        }
        return quoteService.createQuote(quoteRequest);
    }
    @GetMapping
    public List<QuoteResponse> getAllQuotesByUser(){
        return quoteService.getAllQuotesByUser();
    }
    @GetMapping("/public")
    public List<QuoteResponse> getAllPublic(){
        return quoteService.getAllPublicQuotes();
    }
    @GetMapping("/private")
    public List<QuoteResponse> getAllPrivate(){
        return quoteService.getAllPrivateQuotes();
    }
    @GetMapping("/tag/{tag}")
    public List<QuoteResponse> getQuotesByTag(@PathVariable Tag tag){
        return quoteService.getQuotesByTag(tag);
    }
    @GetMapping("/search/{keyword}")
    public List<QuoteResponse> searchByKeyword(@PathVariable String keyword){
        return quoteService.searchQuotes(keyword);
    }
    @PutMapping("/{quoteId}")
    public QuoteResponse update(@RequestBody QuoteRequest quoteRequest, @PathVariable Long quoteId){
        return quoteService.updateQuote(quoteRequest, quoteId);
    }
    @DeleteMapping("/{quoteId}")
    public void delete(@PathVariable Long quoteId){
        quoteService.deleteQuote(quoteId);
    }
}
