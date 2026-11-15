package com.gabrielpatriota.librarymanagement.repository;

import com.gabrielpatriota.librarymanagement.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}