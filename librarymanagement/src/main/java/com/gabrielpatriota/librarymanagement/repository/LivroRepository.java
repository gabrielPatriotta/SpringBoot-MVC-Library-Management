package com.gabrielpatriota.librarymanagement.repository;

import com.gabrielpatriota.librarymanagement.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByDisponivelTrue();
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
}