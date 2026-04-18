package com.projetos.encurtador_url.domain.repositories;

import com.projetos.encurtador_url.data.LinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ILinkRepository extends JpaRepository<LinkEntity, Long> {
    Optional<LinkEntity> findByLinkCurto(String linkCurto);
    Optional<LinkEntity> findByLinkOriginal(String linkOriginal);
}
