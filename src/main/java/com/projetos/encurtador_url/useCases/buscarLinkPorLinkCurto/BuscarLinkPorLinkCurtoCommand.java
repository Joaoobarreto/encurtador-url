package com.projetos.encurtador_url.useCases.buscarLinkPorLinkCurto;

import com.projetos.encurtador_url.LinkResponse;
import com.projetos.encurtador_url.mediator.ICommand;

public record BuscarLinkPorLinkCurtoCommand(String shortCode) implements ICommand<LinkResponse> { }
