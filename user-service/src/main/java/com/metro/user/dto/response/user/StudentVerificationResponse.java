package com.metro.user.dto.response.user;

import java.time.LocalDate;

import com.metro.user.enums.StudentVerificationStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentVerificationResponse {
    Long id;
    String schoolName;
    String imageUrl;
    LocalDate graduateDate;
    StudentVerificationStatus status;
}
