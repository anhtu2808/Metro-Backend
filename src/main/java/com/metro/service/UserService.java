package com.metro.service;

import com.metro.dto.request.UserCreationRequest;
import com.metro.dto.request.UserUpdateRequest;
import com.metro.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    /**
     * Creates a new user
     * @param request The user creation details
     * @return The created user data
     */
    UserResponse createUser(UserCreationRequest request);
    
    /**
     * Retrieves the information of the currently authenticated user
     * @return The current user's data
     */
    UserResponse getMyInfo();
    
    /**
     * Updates an existing user
     * @param userId The ID of the user to update
     * @param request The updated user details
     * @return The updated user data
     */
    UserResponse updateUser(String userId, UserUpdateRequest request);
    
    /**
     * Deletes a user
     * @param userId The ID of the user to delete
     */
    void deleteUser(String userId);
    
    /**
     * Retrieves all users
     * @return A list of all users
     */
    List<UserResponse> getUsers();
    
    /**
     * Retrieves a specific user by ID
     * @param userId The ID of the user to retrieve
     * @return The user data
     */
    UserResponse getUser(String userId);
}
