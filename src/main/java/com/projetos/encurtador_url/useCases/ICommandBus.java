package com.projetos.encurtador_url.useCases;


import com.projetos.encurtador_url.mediator.ICommand;
import org.springframework.web.context.annotation.ApplicationScope;

public interface ICommandBus {
    <TResult> TResult send(ICommand<TResult> command);
}
