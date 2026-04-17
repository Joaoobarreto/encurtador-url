package com.projetos.encurtador_url.useCases.deletarLink;

import com.projetos.encurtador_url.mediator.ICommand;

public record DeletarLinkCommand(String shortCode) implements ICommand<Void> { }

