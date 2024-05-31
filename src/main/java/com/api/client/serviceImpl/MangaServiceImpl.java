package com.api.client.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.client.entity.Manga;
import com.api.client.repository.MangaRepository;
import com.api.client.service.MangaService;

@Service
public class MangaServiceImpl implements MangaService {

	private final MangaRepository mangaRepository;

    @Autowired
    public MangaServiceImpl(MangaRepository mangaRepository) {
        this.mangaRepository = mangaRepository;
    }


    @Override
    public List<Manga> getAllMangas() {
        return mangaRepository.findAll();
    }
}