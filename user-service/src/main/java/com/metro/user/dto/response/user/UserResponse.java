package com.metro.user.dto.response.user;

import java.util.List;
import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;
    String username;
    String email;
    String firstName;
    String lastName;
    String phone;
    String avatarUrl;
    String address;
    String role;
    Set<String> permissions;
    Boolean isStudentVerified;
    int deleted;
    List<StudentVerificationResponse> studentVerifications;
}
