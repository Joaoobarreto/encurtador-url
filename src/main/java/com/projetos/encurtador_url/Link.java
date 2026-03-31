package com.projetos.encurtador_url;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_LINK")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private Integer click = 0;
}
