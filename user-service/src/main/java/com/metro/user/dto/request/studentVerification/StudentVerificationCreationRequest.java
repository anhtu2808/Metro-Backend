package com.metro.user.dto.request.studentVerification;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentVerificationCreationRequest {

    @NotBlank(message = "School name is required")
    @Size(min = 3, max = 100, message = "School name must be between 3 and 100 characters")
    String schoolName;

    @Pattern(
            regexp = "^(https?://.*\\.(?:png|jpg|jpeg|gif|pdf))?$",
            message = "Image URL must be a valid URL ending with .png, .jpg, .jpeg, .gif, or .pdf"
    )
    String imageUrl;

    @NotNull(message = "Graduate date is required")
    @PastOrPresent(message = "Graduate date must be in the present or future")
    LocalDate graduateDate;

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be a positive number")
    Long userId;
}
