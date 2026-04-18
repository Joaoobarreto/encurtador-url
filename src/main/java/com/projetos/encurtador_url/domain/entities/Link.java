package com.projetos.encurtador_url.domain.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Link {
    private Long id;
    private String linkCurto;
    private String linkOriginal;
    private Integer click;

    public Link(String linkCurto, String linkOriginal) {
        this.linkCurto = linkCurto;
        this.linkOriginal = linkOriginal;
        this.click = 0;
    }

    public Link() { }

    public void novoClick() { this.click++; }
}
