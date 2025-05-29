package com.metro.user.dto.request.user;

import lombok.*;
import lombok.experimental.FieldDefaults;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    String username;
    String password;
    String email;
    String phone;
    String address;
    String firstName;
    String lastName;
    String avatarUrl;
}