package com.api.client.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "favorito")
@Data
public class Favorito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "manga_id")
    private Manga manga;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    
}