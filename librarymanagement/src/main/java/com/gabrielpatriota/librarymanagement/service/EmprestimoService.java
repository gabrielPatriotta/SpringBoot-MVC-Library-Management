package com.gabrielpatriota.librarymanagement.service;

import com.gabrielpatriota.librarymanagement.model.Emprestimo;
import com.gabrielpatriota.librarymanagement.model.Livro;
import com.gabrielpatriota.librarymanagement.model.Usuario;
import com.gabrielpatriota.librarymanagement.repository.EmprestimoRepository;
import com.gabrielpatriota.librarymanagement.repository.LivroRepository;
import com.gabrielpatriota.librarymanagement.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository,
                             LivroRepository livroRepository,
                             UsuarioRepository usuarioRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Emprestimo> listarTodos() {
        return emprestimoRepository.findAll();
    }

    public Emprestimo buscarPorId(Long id) {
        return emprestimoRepository.findById(id).orElse(null);
    }

    @Transactional
    public boolean realizarEmprestimo(Emprestimo emprestimo) {
        if (emprestimo == null
                || emprestimo.getLivro() == null
                || emprestimo.getLivro().getId() == null
                || emprestimo.getUsuario() == null
                || emprestimo.getUsuario().getId() == null) {
            return false;
        }

        Livro livro = livroRepository.findById(emprestimo.getLivro().getId()).orElse(null);
        Usuario usuario = usuarioRepository.findById(emprestimo.getUsuario().getId()).orElse(null);

        if (livro == null || usuario == null || !livro.isDisponivel()) {
            return false;
        }

        livro.setDisponivel(false);
        livroRepository.save(livro);

        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDataDevolucao(null);
        emprestimo.setDevolvido(false);
        emprestimoRepository.save(emprestimo);
        return true;
    }

    @Transactional
    public boolean registrarDevolucao(Long id) {
        Emprestimo emprestimo = buscarPorId(id);
        if (emprestimo == null || emprestimo.isDevolvido()) {
            return false;
        }

        emprestimo.setDevolvido(true);
        emprestimo.setDataDevolucao(LocalDate.now());

        Livro livro = emprestimo.getLivro();
        if (livro != null) {
            livro.setDisponivel(true);
            livroRepository.save(livro);
        }

        emprestimoRepository.save(emprestimo);
        return true;
    }

    @Transactional
    public boolean excluir(Long id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id).orElse(null);
        if (emprestimo == null) {
            return false;
        }

        if (!emprestimo.isDevolvido() && emprestimo.getLivro() != null) {
            Livro livro = emprestimo.getLivro();
            livro.setDisponivel(true);
            livroRepository.save(livro);
        }

        emprestimoRepository.delete(emprestimo);
        return true;
    }
}
