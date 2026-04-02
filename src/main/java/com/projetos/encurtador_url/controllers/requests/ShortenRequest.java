package com.projetos.encurtador_url.controllers.requests;

import com.projetos.encurtador_url.useCases.encurtarUrl.EncurtarUrlCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record ShortenRequest(String url) {

    public EncurtarUrlCommand getCommand() {
        return new EncurtarUrlCommand(url);
    }

}

