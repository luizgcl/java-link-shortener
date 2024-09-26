package br.com.luizgcl.linkshortener.repository;

import br.com.luizgcl.linkshortener.entities.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<Link, Integer> {

    Optional<Link> findBySlug(String slug);

}
