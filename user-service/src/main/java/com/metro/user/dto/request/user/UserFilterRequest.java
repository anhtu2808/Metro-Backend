package com.metro.user.dto.request.user;

import com.metro.user.enums.RoleType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserFilterRequest {
    RoleType role;
    Integer deleted;
    String username;
    String email;
    String search;
}
