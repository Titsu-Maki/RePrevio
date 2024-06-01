package com.api.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.client.entity.Pais;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {
}
