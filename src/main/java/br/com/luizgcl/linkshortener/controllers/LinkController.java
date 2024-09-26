package br.com.luizgcl.linkshortener.controllers;

import br.com.luizgcl.linkshortener.dtos.CreateLinkDTO;
import br.com.luizgcl.linkshortener.dtos.ErrorResponseDTO;
import br.com.luizgcl.linkshortener.exceptions.LinkAlreadyExistsException;
import br.com.luizgcl.linkshortener.exceptions.LinkNotFoundException;
import br.com.luizgcl.linkshortener.services.LinkService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;

    @PostMapping
    public ResponseEntity<?> createLink(@RequestBody CreateLinkDTO createLinkDTO) {
        try {
            var response = linkService.createLink(createLinkDTO);
            return ResponseEntity.status(201).body(response);
        } catch (LinkAlreadyExistsException exception) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                    exception.getMessage(),
                    exception.getCode()
            ));
        }
    }

    @GetMapping("{slug}/details")
    public ResponseEntity<?> getLinkDetails(@PathVariable("slug") String slug) {
        try {
            var response = linkService.getLinkDetails(slug);
            return ResponseEntity.ok(response);
        } catch (LinkNotFoundException exception) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                    exception.getMessage(),
                    exception.getCode()
            ));
        }
    }

    @GetMapping("{slug}")
    public ResponseEntity<?> visitLink(@PathVariable("slug") String slug, HttpServletResponse response) {
        try {
            var originalUrl = linkService.visitLink(slug);
            response.sendRedirect(originalUrl);
            return ResponseEntity.status(301).build();
        } catch (LinkNotFoundException exception) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(
                    exception.getMessage(),
                    exception.getCode()
            ));
        } catch (IOException exception) {
            return ResponseEntity.internalServerError().body(new ErrorResponseDTO(
                    exception.getMessage(),
                    "INTERNAL_SERVER_ERROR"
            ));
        }
    }
}
