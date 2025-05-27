package com.kolayik.dto.request;

public record CommentRequest(
        String token,
        String description
) {
}
