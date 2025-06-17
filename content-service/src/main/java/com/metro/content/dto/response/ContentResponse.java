package com.metro.content.dto.response;

import com.metro.content.enums.ContentStatus;
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
public class ContentResponse {
    Long id;
    ContentType type;
    String title;
    String body;
    String summary;
    ContentStatus status;
    LocalDateTime publishAt;
    Long userId;
    List<String> imageUrls;
}
