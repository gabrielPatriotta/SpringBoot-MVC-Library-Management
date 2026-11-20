package com.gabrielpatriota.librarymanagement.service;

import com.gabrielpatriota.librarymanagement.model.Autor;
import com.gabrielpatriota.librarymanagement.model.Categoria;
import com.gabrielpatriota.librarymanagement.model.Livro;
import com.gabrielpatriota.librarymanagement.repository.AutorRepository;
import com.gabrielpatriota.librarymanagement.repository.CategoriaRepository;
import com.gabrielpatriota.librarymanagement.repository.EmprestimoRepository;
import com.gabrielpatriota.librarymanagement.repository.LivroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;
    private final EmprestimoRepository emprestimoRepository;

    public LivroService(LivroRepository livroRepository,
                        AutorRepository autorRepository,
                        CategoriaRepository categoriaRepository,
                        EmprestimoRepository emprestimoRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.categoriaRepository = categoriaRepository;
        this.emprestimoRepository = emprestimoRepository;
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public List<Livro> listarDisponiveis() {
        return livroRepository.findByDisponivelTrue();
    }

    public Livro buscarPorId(Long id) {
        return livroRepository.findById(id).orElse(null);
    }


    @Transactional
    public Livro salvar(Livro livro) {
        if (livro == null) {
            return null;
        }

        Autor autor = null;
        if (livro.getAutor() != null && livro.getAutor().getId() != null) {
            autor = autorRepository.findById(livro.getAutor().getId()).orElse(null);
            if (autor == null) {
                return null;
            }
        }

        Categoria categoria = null;
        if (livro.getCategoria() != null && livro.getCategoria().getId() != null) {
            categoria = categoriaRepository.findById(livro.getCategoria().getId()).orElse(null);
            if (categoria == null) {
                return null;
            }
        }

        if (livro.getId() != null) {
            Livro existente = livroRepository.findById(livro.getId()).orElse(null);
            if (existente == null) {
                return null;
            }

            existente.setTitulo(livro.getTitulo());
            existente.setIsbn(livro.getIsbn());
            existente.setAnoPublicacao(livro.getAnoPublicacao());
            existente.setAutor(autor);
            existente.setCategoria(categoria);

            return livroRepository.save(existente);
        }

        livro.setAutor(autor);
        livro.setCategoria(categoria);
        livro.setDisponivel(true);
        return livroRepository.save(livro);
    }

    @Transactional
    public boolean excluir(Long id) {
        if (!livroRepository.existsById(id) || emprestimoRepository.existsByLivro_Id(id)) {
            return false;
        }
        livroRepository.deleteById(id);
        return true;
    }
}
