package com.projetos.encurtador_url.data.mappers;

import com.projetos.encurtador_url.data.LinkEntity;
import com.projetos.encurtador_url.domain.entities.Link;

public class LinkMapper {

    public static LinkEntity toEntity(LinkEntity link) {
        if (link == null) return null;
        return new LinkEntity(link.getLinkCurto(), link.getLinkOriginal());
    }
}
