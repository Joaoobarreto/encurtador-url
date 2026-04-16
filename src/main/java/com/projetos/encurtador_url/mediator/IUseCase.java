package com.projetos.encurtador_url.mediator;

public interface IUseCase<TCommand, TResult> {
    TResult executar(TCommand command);
}
