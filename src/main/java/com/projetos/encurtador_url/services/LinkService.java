package com.projetos.encurtador_url.services;

import com.projetos.encurtador_url.LinkResponse;
import com.projetos.encurtador_url.ResourceNotFoundException;
import com.projetos.encurtador_url.base62.services.Base62Service;
import com.projetos.encurtador_url.data.LinkRepository;
import com.projetos.encurtador_url.domain.entities.Contador;
import com.projetos.encurtador_url.domain.entities.Link;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LinkService {

    private final LinkRepository linkRepository;
    private final Base62Service base62Service;

    public LinkService(LinkRepository linkRepository, Base62Service base62Service) {
        this.linkRepository = linkRepository;
        this.base62Service = base62Service;
    }

    public LinkResponse shortenUrl(String originalUrl) {
        Long codigo = Contador.getContador();

        String base62 = base62Service.base10ToBase62(codigo);
        
        Link link = new Link(base62, originalUrl);

        Link savedLink = linkRepository.save(link);

        Contador.aumentar();
        
        return toResponse(savedLink);
    }

    public LinkResponse getLinkByShortCode(String shortCode) {
        Link link = linkRepository.findByLinkCurto(shortCode)
                .orElseThrow(() -> new ResourceNotFoundException("Link não encontrado"));

        link.novoClick();
        linkRepository.save(link);

        return toResponse(link);
    }

    public List<LinkResponse> getAllLinks() {
        List<Link> links = linkRepository.findAll();
        return links.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public void deleteLink(String shortCode) {
        Link link = linkRepository.findByLinkCurto(shortCode)
                .orElseThrow(() -> new ResourceNotFoundException("Link não encontrado"));
        linkRepository.delete(link);
    }

    private LinkResponse toResponse(Link link) {
        String linkOriginal = link.getLinkOriginal();
        if (!linkOriginal.startsWith("http://") && !linkOriginal.startsWith("https://")) {
            link.setLinkOriginal("https://" + linkOriginal);
        }

        return new LinkResponse(
                "https://encurtador.localhost/" + link.getLinkCurto(),
                link.getLinkOriginal(),
                link.getClick()
        );
    }
}

