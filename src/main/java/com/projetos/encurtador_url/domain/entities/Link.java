package com.projetos.encurtador_url.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TB_LINK")
@Getter
@Setter
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LINK_ID")
    private Long id;

    @Column(name = "LINK_CURTO", nullable = false, unique = true)
    private String linkCurto;
    
    @Column(name = "LINK_ORIGINAL", nullable = false)
    private String linkOriginal;

    @Column(name = "QT_CLICKS", nullable = false)
    private Integer click;
    public Link(String linkCurto, String linkOriginal) {
        this.linkCurto = linkCurto;
        this.linkOriginal = linkOriginal;
        this.click = 0;
    }

    public Link() { }

    public Integer novoClick() { return this.click++; }
}
