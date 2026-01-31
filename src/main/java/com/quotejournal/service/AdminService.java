package com.quotejournal.service;

import com.quotejournal.dto.AdminDashboardResponse;
import com.quotejournal.dto.AdminUserRequest;
import com.quotejournal.dto.UserResponse;
import com.quotejournal.entity.*;
import com.quotejournal.exception.ResourceNotFoundException;
import com.quotejournal.repository.QuoteRepository;
import com.quotejournal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final QuoteRepository quoteRepository;
    public AdminService(UserRepository userRepository, QuoteRepository quoteRepository){
        this.userRepository=userRepository;
        this.quoteRepository=quoteRepository;
    }
    public AdminDashboardResponse getDashboardData(){
        List<User> allUsers=userRepository.findAll();
        long totalUsers=userRepository.count();
        long unverifiedUsers=userRepository.countByIsVerified(false);
        long totalQuotes=quoteRepository.count();
        long publicQuotes=quoteRepository.countByIsPublic(true);
        long privateQuotes=quoteRepository.countByIsPublic(false);
        Map<String, Long> tagCountMap=new HashMap<>();
        List<Quote> allQuotes=quoteRepository.findAll();
        for(Quote quote:allQuotes){
            Tag tag=quote.getTag();
            String tagStr=tag.toString();
            if(tagStr!=null && !tagStr.isBlank()){
                tagCountMap.put(tagStr, tagCountMap.getOrDefault(tagStr, 0L)+1);
            }
        }

        return new AdminDashboardResponse(
                allUsers,
                totalUsers,
                unverifiedUsers,
                totalQuotes,
                publicQuotes,
                privateQuotes,
                tagCountMap
        );
    }
    public UserResponse changeUserRole(AdminUserRequest adminRequest){
        User user=userRepository.findById(adminRequest.userId()).orElseThrow(()-> new ResourceNotFoundException("No user exists"));
        user.setRole(Role.valueOf(adminRequest.role().toUpperCase()));
        userRepository.save(user);
        return new UserResponse(user.getName(), user.getEmail(), user.isVerified());
    }
    public void deleteUserByAdmin(Long userId){
        try{
            User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("Resource not found"));
            userRepository.delete(user);
        }catch(Exception e){
            throw new ResourceNotFoundException(e.getMessage());
        }
    }
}
