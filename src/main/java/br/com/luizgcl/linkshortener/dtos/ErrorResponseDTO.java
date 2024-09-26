package br.com.luizgcl.linkshortener.dtos;

public record ErrorResponseDTO(
        String message,
        String code
) {
}
