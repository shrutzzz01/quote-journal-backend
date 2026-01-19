package com.quotejournal.mapper;

import com.quotejournal.dto.QuoteRequest;
import com.quotejournal.dto.QuoteResponse;
import com.quotejournal.entity.Quote;
import org.springframework.stereotype.Component;

@Component
public class QuoteMapper {
    public static Quote toEntity(QuoteRequest quoteRequest){
        Quote quote=new Quote();
        quote.setContent(quoteRequest.content());
        quote.setPublic(quoteRequest.isPublic());
        quote.setTag(quoteRequest.tag());
        return quote;
    }
    public QuoteResponse toResponse(Quote quote){
        return new QuoteResponse(
                quote.getQuoteId(),
                quote.getContent(),
                quote.getTag(),
                quote.getCreatedAt(),
                quote.isPublic()
        );
    }
}
