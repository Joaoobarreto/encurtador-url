package com.projetos.encurtador_url.useCases.deletarLink;

import com.projetos.encurtador_url.mediator.IUseCase;
import com.projetos.encurtador_url.services.LinkService;
import org.springframework.stereotype.Component;

@Component
public class DeletarLinkUseCase implements IUseCase<DeletarLinkCommand, Void> {
    private final LinkService linkService;
    
    public DeletarLinkUseCase(LinkService linkService) {
        this.linkService = linkService;
    }
    
    public Void executar(DeletarLinkCommand command) {
        linkService.deleteLink(command.shortCode());
        return null;
    }
}

