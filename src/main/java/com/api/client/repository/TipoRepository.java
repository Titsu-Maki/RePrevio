package com.api.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.client.entity.Tipo;

@Repository
public interface TipoRepository extends JpaRepository<Tipo, Long> {
}
