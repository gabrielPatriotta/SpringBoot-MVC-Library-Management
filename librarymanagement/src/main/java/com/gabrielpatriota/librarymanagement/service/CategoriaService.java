package com.gabrielpatriota.librarymanagement.service;

import com.gabrielpatriota.librarymanagement.model.Categoria;
import com.gabrielpatriota.librarymanagement.repository.CategoriaRepository;
import com.gabrielpatriota.librarymanagement.repository.LivroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final LivroRepository livroRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, LivroRepository livroRepository) {
        this.categoriaRepository = categoriaRepository;
        this.livroRepository = livroRepository;
    }

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    @Transactional
    public Categoria salvar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public boolean excluir(Long id) {
        if (!categoriaRepository.existsById(id) || livroRepository.existsByCategoria_Id(id)) {
            return false;
        }
        categoriaRepository.deleteById(id);
        return true;
    }
}
