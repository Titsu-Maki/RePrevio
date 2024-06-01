package com.api.client.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.client.entity.Favorito;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
	
    long countByMangaId(Long mangaId);
    
    List<Favorito> findByUsuarioUsername(String username);
    
    Optional<Favorito> findByUsuarioUsernameAndMangaId(String username, Long mangaId);
}