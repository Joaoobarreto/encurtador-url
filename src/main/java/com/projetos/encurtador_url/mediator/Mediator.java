package com.projetos.encurtador_url.mediator;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;

@Component
public class Mediator {
    private final ApplicationContext context;

    public Mediator(ApplicationContext context) {
        this.context = context;
    }

    public <TResult> TResult send(ICommand<TResult> command) {
        var useCases = context.getBeansOfType(IUseCase.class);

        for (IUseCase<?,?> useCase : useCases.values()) {
            var genericInterfaces = useCase.getClass().getGenericInterfaces();

            for (var genericInterface : genericInterfaces) {
                if(genericInterface instanceof ParameterizedType type) {
                    var commandType = type.getActualTypeArguments()[0];
                    if(commandType.equals(command.getClass())) {
                        return ((IUseCase<ICommand<TResult>, TResult>) useCase).executar(command);
                    }
                }
            }
        }

        throw new RuntimeException("UseCase não encontrado para : " + command.getClass());
    }
}
