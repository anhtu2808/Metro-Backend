package com.metro.content.dto.response;

import com.metro.content.enums.ContentType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
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
    String status;
    LocalDate publishAt;
    Long userId;
    List<String> imageUrls;
}
