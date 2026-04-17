package com.projetos.encurtador_url.useCases.encurtarUrl;

import com.projetos.encurtador_url.LinkResponse;
import com.projetos.encurtador_url.mediator.IUseCase;
import com.projetos.encurtador_url.services.LinkService;
import org.springframework.stereotype.Component;

@Component
public class EncurtarUrlUseCase implements IUseCase<EncurtarUrlCommand, LinkResponse> {
    private final LinkService linkService;

    public EncurtarUrlUseCase(LinkService linkService) {
        this.linkService = linkService;
    }

    public LinkResponse executar(EncurtarUrlCommand command) {
        return linkService.shortenUrl(command.url());
    }
}
