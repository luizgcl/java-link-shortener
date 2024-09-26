package br.com.luizgcl.linkshortener.dtos;

import java.time.LocalDateTime;

public record ResponseLinkDTO(
        String originalUrl,
        String slug,
        Integer clicks,
        LocalDateTime updatedAt
) {
}
