package com.api.client.entity;

import java.sql.Date;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "manga")
@Data
public class Manga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "fecha_lanzamiento")
    private Date fechaLanzamiento;

    @Column(nullable = false)
    private int temporadas;

    @Column(nullable = false)
    private boolean anime;

    @Column(nullable = false)
    private boolean juego;

    @Column(nullable = false)
    private boolean pelicula;

    @ManyToOne
    @JoinColumn(name = "pais_id")
    private Pais pais;

    @ManyToOne
    @JoinColumn(name = "tipo_id")
    private Tipo tipo;

    
}
