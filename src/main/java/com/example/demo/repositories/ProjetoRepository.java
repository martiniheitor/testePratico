package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
}
