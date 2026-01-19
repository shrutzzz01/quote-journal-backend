package com.quotejournal.service;

import com.quotejournal.dto.QuoteRequest;
import com.quotejournal.dto.QuoteResponse;
import com.quotejournal.entity.Quote;
import com.quotejournal.entity.Tag;
import com.quotejournal.entity.User;
import com.quotejournal.exception.ResourceNotFoundException;
import com.quotejournal.mapper.QuoteMapper;
import com.quotejournal.repository.QuoteRepository;
import com.quotejournal.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuoteService {
    private final QuoteRepository quoteRepository;
    private final UserRepository userRepository;
    private final QuoteMapper quoteMapper;
    public QuoteService(QuoteRepository quoteRepository, UserRepository userRepository, QuoteMapper quoteMapper){
        this.quoteRepository=quoteRepository;
        this.userRepository=userRepository;
        this.quoteMapper=quoteMapper;
    }
    public QuoteResponse createQuote(QuoteRequest quoteRequest){
        String currEmail= SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByEmail(currEmail).orElseThrow(()->new ResourceNotFoundException("Can't find email"));
        Quote quote=new Quote();
        quote.setContent(quoteRequest.content());
        quote.setTag(quoteRequest.tag());
        quote.setPublic(quoteRequest.isPublic());
        quote.setUser(user);
        quote = quoteRepository.save(quote);
        return quoteMapper.toResponse(quote);
    }
    public List<QuoteResponse> getAllQuotesByUser(){
        String currEmail= SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByEmail(currEmail).orElseThrow(()->new RuntimeException("Can't find email"));
        List<Quote> quotesList=quoteRepository.findByUser(user);

        return quotesList.stream().map(quoteMapper::toResponse)
                .collect(Collectors.toList());
    }
    public List<QuoteResponse> getAllPublicQuotes(){
        List<Quote> publicQuotesList=quoteRepository.findByIsPublicTrue();

        return publicQuotesList.stream()
                .map(quoteMapper::toResponse)
                .collect(Collectors.toList());
    }
    public List<QuoteResponse> getAllPrivateQuotes(){
        String currEmail= SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByEmail(currEmail).orElseThrow(()->new ResourceNotFoundException("Can't find email"));
        List<Quote> privateQuotesList=quoteRepository.findByIsPublicFalseAndUser(user);
        return privateQuotesList.stream()
                .map(quoteMapper::toResponse)
                .collect(Collectors.toList());
    }
    public List<QuoteResponse> getQuotesByTag(Tag tag){
        String currEmail= SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByEmail(currEmail).orElseThrow(()->new ResourceNotFoundException("Can't find email"));
        List<Quote> publicQuotes=quoteRepository.findByTagAndIsPublicTrue(tag);
        List<Quote> userQuotes=quoteRepository.findByTagAndUser(tag, user);
        Set<Quote> combinedQuotes = new HashSet<>();
        combinedQuotes.addAll(publicQuotes);
        combinedQuotes.addAll(userQuotes);

        return combinedQuotes.stream()
                .map(quoteMapper::toResponse)
                .collect(Collectors.toList());
    }
    public List<QuoteResponse> searchQuotes(String keyword){
        String currEmail= SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByEmail(currEmail).orElseThrow(()->new ResourceNotFoundException("Can't find email"));
        List<Quote> publicQuotes=quoteRepository.findByContentContainingIgnoreCaseAndIsPublicTrue(keyword);
        List<Quote> userQuotes=quoteRepository.findByContentContainingIgnoreCaseAndUser(keyword, user);
        Set<Quote> combinedQuotes = new HashSet<>();
        combinedQuotes.addAll(publicQuotes);
        combinedQuotes.addAll(userQuotes);
        return combinedQuotes.stream()
                .map(quoteMapper::toResponse)
                .collect(Collectors.toList());
    }
    public QuoteResponse updateQuote(QuoteRequest quoteRequest, Long quoteId){
        String currEmail= SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByEmail(currEmail).orElseThrow(()->new RuntimeException("Can't find email"));
        Quote quote=quoteRepository.findByQuoteIdAndUser(quoteId, user);

        if(quote==null)
            throw new ResourceNotFoundException("No quote to be updated");
        quote.setContent(quoteRequest.content());
        quote.setTag(quoteRequest.tag());
        quote.setPublic(quoteRequest.isPublic());
        quoteRepository.save(quote);
        return quoteMapper.toResponse(quote);
    }
    public void deleteQuote(Long quoteId){
        String currEmail= SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByEmail(currEmail).orElseThrow(()->new RuntimeException("Can't find email"));
        Quote quote=quoteRepository.findByQuoteIdAndUser(quoteId, user);
        if(quote==null)
            throw new ResourceNotFoundException("No quote to be deleted");
        quoteRepository.delete(quote);
    }
}
