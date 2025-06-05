package com.metro.user.dto.request.user;
import com.metro.user.enums.StudentVerificationStatus;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentVerificationRequest {
    String schoolName;
    String imageUrl;
    LocalDate graduateDate;
    StudentVerificationStatus status;
}
