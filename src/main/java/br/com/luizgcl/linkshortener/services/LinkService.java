package br.com.luizgcl.linkshortener.services;

import br.com.luizgcl.linkshortener.dtos.CreateLinkDTO;
import br.com.luizgcl.linkshortener.dtos.ResponseLinkDTO;
import br.com.luizgcl.linkshortener.entities.Link;
import br.com.luizgcl.linkshortener.exceptions.LinkAlreadyExistsException;
import br.com.luizgcl.linkshortener.exceptions.LinkNotFoundException;
import br.com.luizgcl.linkshortener.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;

    public ResponseLinkDTO createLink(CreateLinkDTO createLinkDTO) {
        var link = new Link();

        link.setOriginalUrl(createLinkDTO.originalUrl());

        var slug = this.slugfy(createLinkDTO.slug());

        var linkWithSameSlug = this.linkRepository.findBySlug(slug);

        if (linkWithSameSlug.isPresent()) {
            throw new LinkAlreadyExistsException();
        }

        if (slug.isEmpty() || slug.isBlank()) {
            slug = this.generateCode();
        }

        link.setSlug(slug);
        link.setClicks(0);

        this.linkRepository.save(link);

        return new ResponseLinkDTO(
                link.getOriginalUrl(),
                link.getSlug(),
                link.getClicks(),
                link.getUpdatedAt()
        );
    }

    public ResponseLinkDTO getLinkDetails(String slug) {
        var link = getLinkBySlug(slug);

        return new ResponseLinkDTO(
                link.getOriginalUrl(),
                link.getSlug(),
                link.getClicks(),
                link.getUpdatedAt()
        );
    }

    public String visitLink(String slug) {
        var link = getLinkBySlug(slug);

        link.setClicks(link.getClicks() + 1);
        link.setUpdatedAt(LocalDateTime.now());

        this.linkRepository.save(link);

        return link.getOriginalUrl();
    }

    private Link getLinkBySlug(String slug) {
        var link = this.linkRepository.findBySlug(slug);

        if (link.isEmpty()) {
            throw new LinkNotFoundException();
        }

        return link.get();
    }

    private String generateCode() {
        return UUID.randomUUID().toString().substring(0, 4);
    }

    private String slugfy(String slug) {
        String normalizedSlug = Normalizer.normalize(slug, Normalizer.Form.NFD);

        return normalizedSlug
                .replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "-")
                .toLowerCase();
    }

}
