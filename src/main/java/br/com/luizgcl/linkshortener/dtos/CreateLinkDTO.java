package br.com.luizgcl.linkshortener.dtos;

public record CreateLinkDTO(
        String originalUrl,
        String slug
) {
}
