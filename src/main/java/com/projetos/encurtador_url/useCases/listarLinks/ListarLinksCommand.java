package com.projetos.encurtador_url.useCases.listarLinks;

import com.projetos.encurtador_url.LinkResponse;
import com.projetos.encurtador_url.mediator.ICommand;

import java.util.List;

public record ListarLinksCommand() implements ICommand<List<LinkResponse>> { }

