package com.backendoori.ootw.post.dto;

import java.time.LocalDateTime;
import com.backendoori.ootw.post.domain.Post;

public record PostSaveResponse(
    Long postId,
    String title,
    String content,
    String image,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    WeatherDto weather
) {

    public static PostSaveResponse from(Post savedPost) {
        return new PostSaveResponse(
            savedPost.getId(),
            savedPost.getTitle(),
            savedPost.getContent(),
            savedPost.getImage(),
            savedPost.getCreatedAt(),
            savedPost.getUpdatedAt(),
            WeatherDto.from(savedPost.getWeather())
        );
    }

}
