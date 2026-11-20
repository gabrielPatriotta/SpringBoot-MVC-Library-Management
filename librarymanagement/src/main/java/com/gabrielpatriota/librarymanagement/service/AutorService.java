package com.gabrielpatriota.librarymanagement.service;

import com.gabrielpatriota.librarymanagement.model.Autor;
import com.gabrielpatriota.librarymanagement.repository.AutorRepository;
import com.gabrielpatriota.librarymanagement.repository.LivroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;

    public AutorService(AutorRepository autorRepository, LivroRepository livroRepository) {
        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
    }

    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    public Autor buscarPorId(Long id) {
        return autorRepository.findById(id).orElse(null);
    }

    @Transactional
    public Autor salvar(Autor autor) {
        return autorRepository.save(autor);
    }

    @Transactional
    public boolean excluir(Long id) {
        if (!autorRepository.existsById(id) || livroRepository.existsByAutor_Id(id)) {
            return false;
        }
        autorRepository.deleteById(id);
        return true;
    }
}
