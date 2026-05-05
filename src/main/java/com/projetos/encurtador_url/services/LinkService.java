package com.projetos.encurtador_url.services;

import com.projetos.encurtador_url.LinkResponse;
import com.projetos.encurtador_url.ResourceNotFoundException;
import com.projetos.encurtador_url.base62.services.Base62Service;
import com.projetos.encurtador_url.data.LinkEntity;
import com.projetos.encurtador_url.data.mappers.LinkMapper;
import com.projetos.encurtador_url.domain.entities.Link;
import com.projetos.encurtador_url.domain.repositories.ILinkRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LinkService {

    private final ILinkRepository linkRepository;
    private final Base62Service base62Service;
    private final ContadorService contadorService;

    public LinkService(ILinkRepository linkRepository, Base62Service base62Service, ContadorService contadorService) {
        this.linkRepository = linkRepository;
        this.base62Service = base62Service;
        this.contadorService = contadorService;
    }

    public LinkResponse shortenUrl(String originalUrl) {
        Long codigo = contadorService.getNext();

        String base62 = base62Service.base10ToBase62(codigo);
        
        LinkEntity link = new LinkEntity(base62, originalUrl);

        LinkEntity entity = LinkMapper.toEntity(link);
        LinkEntity savedEntity = linkRepository.save(entity);

        return toResponse(savedEntity);
    }

    public LinkResponse getLinkByShortCode(String shortCode) {
        LinkEntity link = linkRepository.findByLinkCurto(shortCode)
                .orElseThrow(() -> new ResourceNotFoundException("Link não encontrado"));

        link.novoClick();
        linkRepository.save(link);

        return toResponse(link);
    }

    public List<LinkResponse> getAllLinks() {
        List<LinkEntity> entities = linkRepository.findAll();
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void deleteLink(String shortCode) {
        LinkEntity entity = linkRepository.findByLinkCurto(shortCode)
                .orElseThrow(() -> new ResourceNotFoundException("Link não encontrado"));
        linkRepository.delete(entity);
    }

    private LinkResponse toResponse(LinkEntity link) {
        String linkOriginal = link.getLinkOriginal();
        String normalizedOriginal = (!linkOriginal.startsWith("http://") && !linkOriginal.startsWith("https://")) 
            ? "http://" + linkOriginal
            : linkOriginal;

        return new LinkResponse(
                "http://encurtador.localhost/api/" + link.getLinkCurto(),
                normalizedOriginal,
                link.getClick()
        );
    }
}
