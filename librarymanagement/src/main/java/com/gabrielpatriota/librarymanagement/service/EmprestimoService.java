package com.gabrielpatriota.librarymanagement.service;

import com.gabrielpatriota.librarymanagement.model.Emprestimo;
import com.gabrielpatriota.librarymanagement.model.Livro;
import com.gabrielpatriota.librarymanagement.repository.EmprestimoRepository;
import com.gabrielpatriota.librarymanagement.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository, LivroRepository livroRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
    }

    public List<Emprestimo> listarTodos() {
        return emprestimoRepository.findAll();
    }

    public Emprestimo buscarPorId(Long id) {
        return emprestimoRepository.findById(id).orElse(null);
    }

    public void realizarEmprestimo(Emprestimo emprestimo) {
        Livro livro = livroRepository.findById(emprestimo.getLivro().getId()).orElse(null);
        if (livro != null && livro.isDisponivel()) {
            livro.setDisponivel(false);
            livroRepository.save(livro);

            emprestimo.setLivro(livro);
            emprestimo.setDataEmprestimo(LocalDate.now());
            emprestimo.setDevolvido(false);
            emprestimoRepository.save(emprestimo);
        }
    }

    public void registrarDevolucao(Long id) {
        Emprestimo emprestimo = buscarPorId(id);
        if (emprestimo != null && !emprestimo.isDevolvido()) {
            emprestimo.setDevolvido(true);
            emprestimo.setDataDevolucao(LocalDate.now());

            Livro livro = emprestimo.getLivro();
            if (livro != null) {
                livro.setDisponivel(true);
                livroRepository.save(livro);
            }
            emprestimoRepository.save(emprestimo);
        }
    }
}