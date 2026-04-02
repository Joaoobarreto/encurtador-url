package com.projetos.encurtador_url.useCases.buscarLinkPorLinkCurto;

import com.projetos.encurtador_url.LinkResponse;
import com.projetos.encurtador_url.services.LinkService;
import org.springframework.stereotype.Component;

@Component
public class BuscarLinkPorLinkCurtoUseCase {
    private final LinkService linkService;
    
    public BuscarLinkPorLinkCurtoUseCase(LinkService linkService) {
        this.linkService = linkService;
    }
    
    public LinkResponse executar(BuscarLinkPorLinkCurtoCommand command) {
        return linkService.getLinkByShortCode(command.shortCode());
    }
}
