package com.gabrielpatriota.librarymanagement.repository;

import com.gabrielpatriota.librarymanagement.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    List<Emprestimo> findByDevolvidoFalse();

    boolean existsByLivro_Id(Long livroId);

    boolean existsByLivro_IdAndDevolvidoFalse(Long livroId);

    boolean existsByUsuario_Id(Long usuarioId);
}
