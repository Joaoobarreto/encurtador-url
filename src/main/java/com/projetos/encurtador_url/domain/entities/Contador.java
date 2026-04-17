package com.projetos.encurtador_url.domain.entities;

import lombok.Getter;

public class Contador {
    @Getter
    private static Long contador = 2_000_000L;

    public static void aumentar() {
        contador++;
    }
}
