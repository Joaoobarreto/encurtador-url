package com.projetos.encurtador_url.services;

import com.projetos.encurtador_url.LinkResponse;
import com.projetos.encurtador_url.ResourceNotFoundException;
import com.projetos.encurtador_url.base62.services.Base62Service;
import com.projetos.encurtador_url.data.LinkEntity;
import com.projetos.encurtador_url.data.mappers.LinkMapper;
import com.projetos.encurtador_url.domain.entities.Contador;
import com.projetos.encurtador_url.domain.entities.Link;
import com.projetos.encurtador_url.domain.repositories.ILinkRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LinkService {

    private final ILinkRepository linkRepository;
    private final Base62Service base62Service;

    public LinkService(ILinkRepository linkRepository, Base62Service base62Service) {
        this.linkRepository = linkRepository;
        this.base62Service = base62Service;
    }

    public LinkResponse shortenUrl(String originalUrl) {
        Long codigo = Contador.getContador();

        String base62 = base62Service.base10ToBase62(codigo);
        
        Link link = new Link(base62, originalUrl);

        LinkEntity entity = LinkMapper.toEntity(link);
        LinkEntity savedEntity = linkRepository.save(entity);

        Contador.aumentar();
        
        return toResponse(LinkMapper.toDomain(savedEntity));
    }

    public LinkResponse getLinkByShortCode(String shortCode) {
        LinkEntity entity = linkRepository.findByLinkCurto(shortCode)
                .orElseThrow(() -> new ResourceNotFoundException("Link não encontrado"));

        Link link = LinkMapper.toDomain(entity);
        link.novoClick();
        LinkEntity updatedEntity = LinkMapper.toEntity(link);
        linkRepository.save(updatedEntity);

        return toResponse(link);
    }

    public List<LinkResponse> getAllLinks() {
        List<LinkEntity> entities = linkRepository.findAll();
        return entities.stream()
                .map(LinkMapper::toDomain)
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void deleteLink(String shortCode) {
        LinkEntity entity = linkRepository.findByLinkCurto(shortCode)
                .orElseThrow(() -> new ResourceNotFoundException("Link não encontrado"));
        linkRepository.delete(entity);
    }

    private LinkResponse toResponse(Link link) {
        String linkOriginal = link.getLinkOriginal();
        String normalizedOriginal = (!linkOriginal.startsWith("http://") && !linkOriginal.startsWith("https://")) 
            ? "https://" + linkOriginal 
            : linkOriginal;

        return new LinkResponse(
                "https://encurtador.localhost/" + link.getLinkCurto(),
                normalizedOriginal,
                link.getClick()
        );
    }
}
