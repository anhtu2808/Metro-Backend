package com.metro.content.dto.request;

import com.metro.content.enums.ContentType;
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
    String title;
    String body;
    String summary;
    String status;
    LocalDateTime publishAt;
    Long userId;
    List<String> imageUrls;
}
