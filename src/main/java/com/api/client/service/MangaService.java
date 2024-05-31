package com.api.client.service;

import java.util.List;
import java.util.Optional;

import com.api.client.dto.MangaDTO;
import com.api.client.entity.Manga;

public interface MangaService {
	
    List<Manga> getAllMangas();
    
    Optional<Manga> getMangaById(Long id);
    
    Manga createManga(MangaDTO mangaDTO) throws Exception;
}