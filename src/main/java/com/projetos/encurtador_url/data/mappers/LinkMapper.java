package com.projetos.encurtador_url.data.mappers;

import com.projetos.encurtador_url.data.LinkEntity;
import com.projetos.encurtador_url.domain.entities.Link;

public class LinkMapper {

    public static LinkEntity toEntity(Link link) {
        if (link == null) return null;
        return new LinkEntity(link.getLinkCurto(), link.getLinkOriginal(), link.getClick());
    }

    public static Link toDomain(LinkEntity entity) {
        if (entity == null) return null;
        Link link = new Link(entity.getLinkCurto(), entity.getLinkOriginal());
        link.setId(entity.getId());
        link.setClick(entity.getClick());
        return link;
    }
}
