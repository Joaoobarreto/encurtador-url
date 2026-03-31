package com.projetos.encurtador_url;

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
        Long codigo = Contador.contador;

        String base62 = base62Service.base10ToBase62(codigo);
        
        Link link = new Link();
        link.setLinkCurto(base62);
        link.setLinkOriginal(originalUrl);

        Link savedLink = linkRepository.save(link);

        Contador.contador++;
        
        return toResponse(savedLink);
    }

    public LinkResponse getLinkByShortCode(String shortCode) {
        Link link = linkRepository.findByLinkCurto(shortCode)
                .orElseThrow(() -> new ResourceNotFoundException("Link não encontrado"));

        link.setClick(link.getClick() + 1);
        linkRepository.save(link);

        return toResponse(link);
    }

    public List<LinkResponse> getAllLinks() {
        List<Link> links = linkRepository.findAll();
        return links.stream().map(this::toResponse).collect(Collectors.toList());
    }

    /**
     * Deleta um link pelo código curto
     */
    public void deleteLink(String shortCode) {
        Link link = linkRepository.findByLinkCurto(shortCode)
                .orElseThrow(() -> new ResourceNotFoundException("Link não encontrado"));
        linkRepository.delete(link);
    }

    /**
     * Converte uma entidade Link para LinkResponse
     */
    private LinkResponse toResponse(Link link) {
        String linkOriginal = link.getLinkOriginal();
        if (!linkOriginal.startsWith("http://") && !linkOriginal.startsWith("https://")) {
            link.setLinkOriginal("https://" + linkOriginal);
        }

        return new LinkResponse(
                "https://short.local/" + link.getLinkCurto(),
                link.getLinkOriginal(),
                link.getClick()
        );
    }
}

