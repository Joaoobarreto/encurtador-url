package com.projetos.encurtador_url.controllers;

import com.projetos.encurtador_url.LinkResponse;
import com.projetos.encurtador_url.controllers.requests.ShortenRequest;
import com.projetos.encurtador_url.mediator.ICommand;
import com.projetos.encurtador_url.mediator.Mediator;
import com.projetos.encurtador_url.useCases.encurtarUrl.EncurtarUrlUseCase;
import com.projetos.encurtador_url.useCases.buscarLinkPorLinkCurto.BuscarLinkPorLinkCurtoUseCase;
import com.projetos.encurtador_url.useCases.buscarLinkPorLinkCurto.BuscarLinkPorLinkCurtoCommand;
import com.projetos.encurtador_url.useCases.deletarLink.DeletarLinkUseCase;
import com.projetos.encurtador_url.useCases.deletarLink.DeletarLinkCommand;
import com.projetos.encurtador_url.useCases.listarLinks.ListarLinksUseCase;
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
    private Mediator mediator;
    private final EncurtarUrlUseCase encurtarUrlUseCase;
    private final BuscarLinkPorLinkCurtoUseCase buscarLinkPorLinkCurtoUseCase;
    private final DeletarLinkUseCase deletarLinkUseCase;
    private final ListarLinksUseCase listarLinksUseCase;

    public LinkController(
            Mediator mediator,
            EncurtarUrlUseCase encurtarUrlUseCase,
            BuscarLinkPorLinkCurtoUseCase buscarLinkPorLinkCurtoUseCase,
            DeletarLinkUseCase deletarLinkUseCase,
            ListarLinksUseCase listarLinksUseCase) {
        this.mediator = mediator;
        this.encurtarUrlUseCase = encurtarUrlUseCase;
        this.buscarLinkPorLinkCurtoUseCase = buscarLinkPorLinkCurtoUseCase;
        this.deletarLinkUseCase = deletarLinkUseCase;
        this.listarLinksUseCase = listarLinksUseCase;
    }

    @PostMapping("/shorten")
    public ResponseEntity<LinkResponse> shortenUrl(@RequestBody ShortenRequest request) {
        LinkResponse response = encurtarUrlUseCase.executar(request.getCommand());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> getLinkByShortCode(@PathVariable String shortCode) {
        LinkResponse response = buscarLinkPorLinkCurtoUseCase.executar(new BuscarLinkPorLinkCurtoCommand(shortCode));
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", response.getOriginalUrl()).build();
    }

    @GetMapping("/links")
    public ResponseEntity<List<LinkResponse>> getAllLinks() {
        List<LinkResponse> links = mediator.send(new ListarLinksCommand());
        return ResponseEntity.ok(links);
    }

    @DeleteMapping("/{shortCode}")
    public ResponseEntity<Map<String, String>> deleteLink(@PathVariable String shortCode) {
        deletarLinkUseCase.executar(new DeletarLinkCommand(shortCode));
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Link deleted successfully");
        
        return ResponseEntity.ok(response);
    }
}
