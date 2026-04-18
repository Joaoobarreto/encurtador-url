package com.projetos.encurtador_url.mediator;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

@Component
public class Mediator {
    private final Map<Class<?>, IUseCase<?, ?>> useCaseMap = new HashMap<>();

    public Mediator(ApplicationContext context) {
        var useCases = context.getBeansOfType(IUseCase.class);

        for (IUseCase<?, ?> useCase : useCases.values()) {
            var genericInterfaces = useCase.getClass().getGenericInterfaces();

            for (var genericInterface : genericInterfaces) {
                if (genericInterface instanceof ParameterizedType type) {
                    var commandType = type.getActualTypeArguments()[0];
                    useCaseMap.put((Class<?>) commandType, useCase);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <TResult> TResult send(ICommand<TResult> command) {
        IUseCase<ICommand<TResult>, TResult> useCase = (IUseCase<ICommand<TResult>, TResult>) useCaseMap.get(command.getClass());
        if (useCase == null) {
            throw new RuntimeException("UseCase não encontrado para : " + command.getClass());
        }
        return useCase.executar(command);
    }
}
