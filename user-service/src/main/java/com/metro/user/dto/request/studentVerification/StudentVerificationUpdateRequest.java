package com.metro.user.dto.request.studentVerification;

import com.metro.user.enums.StudentVerificationStatus;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentVerificationUpdateRequest {

    @Size(min = 3, max = 100, message = "School name must be between 3 and 100 characters")
    String schoolName;

    @Pattern(
            regexp = "^(https?://.*\\.(?:png|jpg|jpeg|gif|pdf))?$",
            message = "Image URL must be a valid URL ending with .png, .jpg, .jpeg, .gif, or .pdf"
    )
    String imageUrl;

    @PastOrPresent(message = "Graduate date must be in the future or present")
    LocalDate graduateDate;

    StudentVerificationStatus status;

}
