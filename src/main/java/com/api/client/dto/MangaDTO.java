package com.api.client.dto;

import java.util.Date;

import lombok.Data;

@Data
public class MangaDTO {
    private Long id;
    private String nombre;
    private Date fechaLanzamiento;
    private int temporadas;
    private Long paisId;
    private boolean anime;
    private boolean juego;
    private boolean pelicula;
    private Long tipoId;
    private String pais;
    private String tipo;

    // Constructor, Getters y Setters

    public MangaDTO() {
    }

    public MangaDTO(Long id, String nombre, Date fechaLanzamiento, int temporadas, String pais, boolean anime, boolean juego, boolean pelicula, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.fechaLanzamiento = fechaLanzamiento;
        this.temporadas = temporadas;
        this.pais = pais;
        this.anime = anime;
        this.juego = juego;
        this.pelicula = pelicula;
        this.tipo = tipo;
    }

    // Getters y Setters
}
