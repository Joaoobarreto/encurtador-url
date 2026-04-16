package com.projetos.encurtador_url.useCases.listarLinks;

import com.projetos.encurtador_url.LinkResponse;
import com.projetos.encurtador_url.mediator.IUseCase;
import com.projetos.encurtador_url.services.LinkService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListarLinksUseCase implements IUseCase<ListarLinksCommand, List<LinkResponse>> {
    private final LinkService linkService;
    
    public ListarLinksUseCase(LinkService linkService) {
        this.linkService = linkService;
    }
    
    public List<LinkResponse> executar(ListarLinksCommand command) {
        return linkService.getAllLinks();
    }
}

