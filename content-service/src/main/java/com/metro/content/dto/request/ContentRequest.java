package com.metro.content.dto.request;

import com.metro.content.enums.ContentStatus;
import com.metro.content.enums.ContentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContentRequest {

    ContentType type;
    @NotBlank(message = "Title is required")
    String title;
    @NotBlank(message = "Body is required")
    String body;
    @NotBlank(message = "Summary is required")
    String summary;
    ContentStatus status;
    LocalDateTime publishAt;
    @NotNull(message = "UserId is required")
    Long userId;
    List<String> imageUrls;
}
