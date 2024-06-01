package com.api.client.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.client.dto.MangaDTO;
import com.api.client.entity.Favorito;
import com.api.client.entity.Manga;
import com.api.client.entity.Pais;
import com.api.client.entity.Tipo;
import com.api.client.entity.Usuario;
import com.api.client.repository.FavoritoRepository;
import com.api.client.repository.MangaRepository;
import com.api.client.repository.PaisRepository;
import com.api.client.repository.TipoRepository;
import com.api.client.repository.UsuarioRepository;
import com.api.client.service.MangaService;

@Service
public class MangaServiceImpl implements MangaService {

	private final MangaRepository mangaRepository;
    private final PaisRepository paisRepository;
    private final TipoRepository tipoRepository;
    
    @Autowired
    private FavoritoRepository favoritoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;


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
    
    @Override
    public Manga actualizarManga(Long id, MangaDTO mangaDTO) {
        Optional<Manga> mangaOptional = mangaRepository.findById(id);
        if (!mangaOptional.isPresent()) {
            throw new IllegalArgumentException("Objeto no encontrado");
        }

        Manga manga = mangaOptional.get();

        if (mangaDTO.getNombre() == null) {
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
    
    @Override
    public void eliminarManga(Long id) {
        Optional<Manga> mangaOptional = mangaRepository.findById(id);
        if (!mangaOptional.isPresent()) {
            throw new IllegalArgumentException("Objeto no encontrado");
        }

        Manga manga = mangaOptional.get();

        long favoritosCount = favoritoRepository.countByMangaId(id);
        if (favoritosCount > 0) {
            throw new IllegalArgumentException("Manga tiene usuarios asociados");
        }

        mangaRepository.delete(manga);
    }
    
    @Override
    public List<MangaDTO> obtenerFavoritosPorUsuario(String username) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByUsername(username);
        if (!usuarioOptional.isPresent()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        List<Favorito> favoritos = favoritoRepository.findByUsuarioUsername(username);
        return favoritos.stream().map(favorito -> {
            Manga manga = favorito.getManga();
            return new MangaDTO(
                    manga.getId(),
                    manga.getNombre(),
                    manga.getFechaLanzamiento(),
                    manga.getTemporadas(),
                    manga.getPais().getNombre(),
                    manga.isAnime(),
                    manga.isJuego(),
                    manga.isPelicula(),
                    manga.getTipo().getNombre()
            );
        }).collect(Collectors.toList());
    }

    @Override
    public void eliminarFavorito(String username, Long mangaId) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByUsername(username);
        if (!usuarioOptional.isPresent()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        Optional<Manga> mangaOptional = mangaRepository.findById(mangaId);
        if (!mangaOptional.isPresent()) {
            throw new IllegalArgumentException("Manga no encontrado");
        }

        Optional<Favorito> favoritoOptional = favoritoRepository.findByUsuarioUsernameAndMangaId(username, mangaId);
        if (!favoritoOptional.isPresent()) {
            throw new IllegalArgumentException("Favorito no encontrado");
        }

        favoritoRepository.delete(favoritoOptional.get());
    }
    
}
  