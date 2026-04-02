package com.projetos.encurtador_url.useCases.deletarLink;

import com.projetos.encurtador_url.services.LinkService;
import org.springframework.stereotype.Component;

@Component
public class DeletarLinkUseCase {
    private final LinkService linkService;
    
    public DeletarLinkUseCase(LinkService linkService) {
        this.linkService = linkService;
    }
    
    public void executar(DeletarLinkCommand command) {
        linkService.deleteLink(command.shortCode());
    }
}

