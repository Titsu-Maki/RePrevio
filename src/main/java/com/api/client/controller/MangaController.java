package com.api.client.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
