package com.projetos.encurtador_url.useCases.encurtarUrl;

import com.projetos.encurtador_url.LinkResponse;
import com.projetos.encurtador_url.mediator.ICommand;

public record EncurtarUrlCommand(String url) implements ICommand<LinkResponse> { }
