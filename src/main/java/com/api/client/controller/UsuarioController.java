package com.api.client.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.client.dto.MangaDTO;
import com.api.client.service.MangaService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private MangaService mangaService;

    @GetMapping("/{username}/favoritos")
    public ResponseEntity<?> obtenerFavoritos(@PathVariable String username) {
        try {
            List<MangaDTO> favoritos = mangaService.obtenerFavoritosPorUsuario(username);
            return ResponseEntity.ok(favoritos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("{\"error\":true,\"msg\":\"" + e.getMessage() + "\"}");
        }
    }
    
    @DeleteMapping("/{username}/favoritos/{mangaId}")
    public ResponseEntity<?> eliminarFavorito(@PathVariable String username, @PathVariable Long mangaId) {
        try {
            mangaService.eliminarFavorito(username, mangaId);
            List<MangaDTO> favoritos = mangaService.obtenerFavoritosPorUsuario(username);
            return ResponseEntity.ok(favoritos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("{\"error\":true,\"msg\":\"" + e.getMessage() + "\"}");
        }
    }
}