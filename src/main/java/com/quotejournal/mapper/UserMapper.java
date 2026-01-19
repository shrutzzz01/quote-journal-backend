package com.quotejournal.mapper;
import com.quotejournal.dto.UserResponse;
import com.quotejournal.entity.User;
import com.quotejournal.dto.UserRequest;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static User toEntity(UserRequest userRequest){
        User user=new User();
        user.setName(userRequest.name());
        user.setEmail(userRequest.email());
        user.setPassword(userRequest.password());
        return user;
    }
    public static UserResponse toResponse(User user){
        return new UserResponse(
                user.getName(),
                user.getEmail(),
                user.isVerified()
        );
    }
}
