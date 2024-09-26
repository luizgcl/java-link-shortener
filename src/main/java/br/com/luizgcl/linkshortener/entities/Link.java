package br.com.luizgcl.linkshortener.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "links")
@NoArgsConstructor
@Getter
@Setter
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "original_url", nullable = false)
    public String originalUrl;

    @Column(unique = true, nullable = false)
    public String slug;

    @Column(nullable = false)
    public Integer clicks;

    @Column(name = "updated_at")
    public LocalDateTime updatedAt;
}
