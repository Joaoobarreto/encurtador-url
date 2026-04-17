package com.projetos.encurtador_url.useCases;

import com.projetos.encurtador_url.mediator.ICommand;
import com.projetos.encurtador_url.mediator.Mediator;
import org.springframework.stereotype.Component;

@Component
public class CommandBus implements ICommandBus{
    private final Mediator mediator;

    public CommandBus(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public <TResult> TResult send(ICommand<TResult> command) {
        return mediator.send(command);
    }
}
