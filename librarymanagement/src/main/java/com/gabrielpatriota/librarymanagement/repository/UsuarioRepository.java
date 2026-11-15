package com.gabrielpatriota.librarymanagement.repository;

import com.gabrielpatriota.librarymanagement.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}