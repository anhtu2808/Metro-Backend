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
    /**
     * Page number starting from 1
     */
    int page;

    /**
     * Page size
     */
    int size;

    /**
     * Sort field
     */
    String sort;

    RoleType role;
    Integer deleted;
    String username;
    String email;
    String search;
}
