package com.api.client.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.client.dto.MangaDTO;
import com.api.client.entity.Manga;
import com.api.client.entity.Pais;
import com.api.client.entity.Tipo;
import com.api.client.repository.MangaRepository;
import com.api.client.repository.PaisRepository;
import com.api.client.repository.TipoRepository;
import com.api.client.service.MangaService;

@Service
public class MangaServiceImpl implements MangaService {

	private final MangaRepository mangaRepository;
    private final PaisRepository paisRepository;
    private final TipoRepository tipoRepository;

    @Autowired
    public MangaServiceImpl(MangaRepository mangaRepository, PaisRepository paisRepository, TipoRepository tipoRepository) {
        this.mangaRepository = mangaRepository;
        this.paisRepository = paisRepository;
        this.tipoRepository = tipoRepository;
    }

    @Override
    public List<Manga> getAllMangas() {
        return mangaRepository.findAll();
    }


    @Override
    public Optional<Manga> getMangaById(Long id) {
        return mangaRepository.findById(id);
    }
    
    
  
    @Override
    public Manga createManga(MangaDTO mangaDTO) {
        if (mangaDTO.getNombre() == null || mangaDTO.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El campo nombre es obligatorio");
        }
        if (mangaDTO.getFechaLanzamiento() == null) {
            throw new IllegalArgumentException("El campo fechaLanzamiento es obligatorio");
        }
        if (mangaDTO.getTemporadas() == 0) {
            throw new IllegalArgumentException("El campo temporadas es obligatorio");
        }
        if (mangaDTO.getPaisId() == null) {
            throw new IllegalArgumentException("El campo paisId es obligatorio");
        }
        if (mangaDTO.getTipoId() == null) {
            throw new IllegalArgumentException("El campo tipoId es obligatorio");
        }

        Optional<Pais> paisOptional = paisRepository.findById(mangaDTO.getPaisId());
        if (!paisOptional.isPresent()) {
            throw new IllegalArgumentException("Pais no existe");
        }

        Optional<Tipo> tipoOptional = tipoRepository.findById(mangaDTO.getTipoId());
        if (!tipoOptional.isPresent()) {
            throw new IllegalArgumentException("Tipo no existe");
        }

        Manga manga = new Manga();
        manga.setNombre(mangaDTO.getNombre());
        manga.setFechaLanzamiento(mangaDTO.getFechaLanzamiento());
        manga.setTemporadas(mangaDTO.getTemporadas());
        manga.setAnime(mangaDTO.isAnime());
        manga.setJuego(mangaDTO.isJuego());
        manga.setPelicula(mangaDTO.isPelicula());
        manga.setPais(paisOptional.get());
        manga.setTipo(tipoOptional.get());

        return mangaRepository.save(manga);
    }
}