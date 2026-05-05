package com.projetos.encurtador_url.services;

import com.projetos.encurtador_url.LinkResponse;
import com.projetos.encurtador_url.ResourceNotFoundException;
import com.projetos.encurtador_url.base62.services.Base62Service;
import com.projetos.encurtador_url.data.LinkEntity;
import com.projetos.encurtador_url.domain.entities.Link;
import com.projetos.encurtador_url.domain.repositories.ILinkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LinkServiceTest {

    @Mock
    private ILinkRepository linkRepository;

    @Mock
    private Base62Service base62Service;

    @InjectMocks
    private LinkService linkService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShortenUrl() {
        // Arrange
        String originalUrl = "https://example.com";
        when(base62Service.base10ToBase62(2000000L)).thenReturn("abc");
        LinkEntity entity = new LinkEntity("abc", originalUrl);
        entity.setId(1L);
        when(linkRepository.save(any(LinkEntity.class))).thenReturn(entity);

        // Act
        LinkResponse response = linkService.shortenUrl(originalUrl);

        // Assert
        assertNotNull(response);
        assertEquals("https://encurtador.localhost/abc", response.getShortUrl());
        assertEquals(originalUrl, response.getOriginalUrl());
        assertEquals(0, response.getClicks());
    }

    @Test
    void testGetLinkByShortCode() {
        // Arrange
        String shortCode = "abc";
        LinkEntity entity = new LinkEntity(shortCode, "https://example.com");
        entity.setId(1L);
        when(linkRepository.findByLinkCurto(shortCode)).thenReturn(Optional.of(entity));
        when(linkRepository.save(any(LinkEntity.class))).thenReturn(entity);

        // Act
        LinkResponse response = linkService.getLinkByShortCode(shortCode);

        // Assert
        assertNotNull(response);
        assertEquals("https://encurtador.localhost/abc", response.getShortUrl());
        assertEquals("https://example.com", response.getOriginalUrl());
        assertEquals(1, response.getClicks());
    }

    @Test
    void testGetLinkByShortCodeNotFound() {
        // Arrange
        String shortCode = "nonexistent";
        when(linkRepository.findByLinkCurto(shortCode)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> linkService.getLinkByShortCode(shortCode));
    }

    @Test
    void testGetAllLinks() {
        // Arrange
        LinkEntity entity1 = new LinkEntity("abc", "https://example1.com");
        LinkEntity entity2 = new LinkEntity("def", "https://example2.com");
        when(linkRepository.findAll()).thenReturn(List.of(entity1, entity2));

        // Act
        List<LinkResponse> responses = linkService.getAllLinks();

        // Assert
        assertEquals(2, responses.size());
        assertEquals("https://encurtador.localhost/abc", responses.get(0).getShortUrl());
        assertEquals("https://encurtador.localhost/def", responses.get(1).getShortUrl());
    }

    @Test
    void testDeleteLink() {
        // Arrange
        String shortCode = "abc";
        LinkEntity entity = new LinkEntity(shortCode, "https://example.com");
        when(linkRepository.findByLinkCurto(shortCode)).thenReturn(Optional.of(entity));

        // Act
        linkService.deleteLink(shortCode);

        // Assert
        verify(linkRepository).delete(entity);
    }

    @Test
    void testDeleteLinkNotFound() {
        // Arrange
        String shortCode = "nonexistent";
        when(linkRepository.findByLinkCurto(shortCode)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> linkService.deleteLink(shortCode));
    }
}
