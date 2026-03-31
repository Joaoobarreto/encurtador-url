package com.projetos.encurtador_url;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    /**
     * POST /api/shorten
     * Encurta uma URL
     */
    @PostMapping("/shorten")
    public ResponseEntity<LinkResponse> shortenUrl(@RequestBody ShortenRequest request) {
        LinkResponse response = linkService.shortenUrl(request.getUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> getLinkByShortCode(@PathVariable String shortCode) {
        LinkResponse response = linkService.getLinkByShortCode(shortCode);
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", response.getOriginalUrl()).build();
    }

    @GetMapping("/links")
    public ResponseEntity<List<LinkResponse>> getAllLinks() {
        List<LinkResponse> links = linkService.getAllLinks();
        return ResponseEntity.ok(links);
    }

    @DeleteMapping("/{shortCode}")
    public ResponseEntity<Map<String, String>> deleteLink(@PathVariable String shortCode) {
        linkService.deleteLink(shortCode);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Link deleted successfully");
        
        return ResponseEntity.ok(response);
    }
/**
    *//**
     * Exception handler para ResourceNotFoundException
     *//*
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    *//**
     * Exception handler para IllegalArgumentException
     *//*
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }*/
}
