package com.projetos.encurtador_url;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LinkResponse {
    private String shortUrl;
    private String originalUrl;
    private Integer clicks;
}

