package com.projetos.encurtador_url;

import org.springframework.stereotype.Service;

@Service
public class Base62Service {
    public String base10ToBase62(Long base10) {
        StringBuilder base62 = new StringBuilder();
        boolean marcador = true;
        while (marcador) {
            Long charactere = base10 % 62;
            base10 =  base10 / 62;
            base62.append(CaracteresBase62Enum.fromValue(charactere).getCharacter());
            if (base10 < 1) marcador = false;
        }
        return base62.toString();
    }
}
