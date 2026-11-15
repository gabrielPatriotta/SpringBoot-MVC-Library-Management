package com.gabrielpatriota.librarymanagement.repository;

import com.gabrielpatriota.librarymanagement.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
}