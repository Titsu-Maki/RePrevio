package com.api.client.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.client.dto.MangaDTO;
import com.api.client.entity.Manga;
import com.api.client.service.MangaService;

@RestController
@RequestMapping("/mangas")
public class MangaController {

	  private final MangaService mangaService;

	    @Autowired
	    public MangaController(MangaService mangaService) {
	        this.mangaService = mangaService;
	    }

    @GetMapping
    public List<Manga> getAllMangas() {
        return mangaService.getAllMangas();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getMangaById(@PathVariable Long id) {
        Optional<Manga> mangaOptional = mangaService.getMangaById(id);
        if (mangaOptional.isPresent()) {
            Manga manga = mangaOptional.get();
            MangaDTO mangaDTO = new MangaDTO();
            mangaDTO.setId(manga.getId());
            mangaDTO.setNombre(manga.getNombre());
            mangaDTO.setFechaLanzamiento(manga.getFechaLanzamiento());
            mangaDTO.setTemporadas(manga.getTemporadas());
            mangaDTO.setPais(manga.getPais().getNombre());
            mangaDTO.setAnime(manga.isAnime());
            mangaDTO.setJuego(manga.isJuego());
            mangaDTO.setPelicula(manga.isPelicula());
            mangaDTO.setTipo(manga.getTipo().getNombre());
            return ResponseEntity.ok(mangaDTO);
        } else {
            return ResponseEntity.status(404).body("{\"error\":true,\"msg\":\"Objeto no encontrado\"}");
        }
    }
    
    @PostMapping
    public ResponseEntity<?> createManga(@RequestBody MangaDTO mangaDTO) throws Exception {
        try {
            Manga manga = mangaService.createManga(mangaDTO);
            return ResponseEntity.ok(new MangaDTO(
                    manga.getId(),
                    manga.getNombre(),
                    manga.getFechaLanzamiento(),
                    manga.getTemporadas(),
                    manga.getPais().getNombre(),
                    manga.isAnime(),
                    manga.isJuego(),
                    manga.isPelicula(),
                    manga.getTipo().getNombre()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("{\"error\":true,\"msg\":\"" + e.getMessage() + "\"}");
        }
    }
}
