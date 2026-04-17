package com.projetos.encurtador_url.controllers;

import com.projetos.encurtador_url.LinkResponse;
import com.projetos.encurtador_url.controllers.requests.ShortenRequest;
import com.projetos.encurtador_url.useCases.ICommandBus;
import com.projetos.encurtador_url.useCases.buscarLinkPorLinkCurto.BuscarLinkPorLinkCurtoCommand;
import com.projetos.encurtador_url.useCases.deletarLink.DeletarLinkCommand;
import com.projetos.encurtador_url.useCases.listarLinks.ListarLinksCommand;
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
    private final ICommandBus commandBus;

    public LinkController(
            ICommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @PostMapping("/shorten")
    public ResponseEntity<LinkResponse> shortenUrl(@RequestBody ShortenRequest request) {
        LinkResponse response = commandBus.send(request.getCommand());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> getLinkByShortCode(@PathVariable String shortCode) {
        LinkResponse response = commandBus.send(new BuscarLinkPorLinkCurtoCommand(shortCode));
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", response.getOriginalUrl()).build();
    }

    @GetMapping("/links")
    public ResponseEntity<List<LinkResponse>> getAllLinks() {
        List<LinkResponse> links = commandBus.send(new ListarLinksCommand());
        return ResponseEntity.ok(links);
    }

    @DeleteMapping("/{shortCode}")
    public ResponseEntity<Map<String, String>> deleteLink(@PathVariable String shortCode) {
        commandBus.send(new DeletarLinkCommand(shortCode));
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Link deleted successfully");
        
        return ResponseEntity.ok(response);
    }
}
