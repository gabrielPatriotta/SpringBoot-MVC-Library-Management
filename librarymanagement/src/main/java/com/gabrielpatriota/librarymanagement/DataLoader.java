package com.gabrielpatriota.librarymanagement;

import com.gabrielpatriota.librarymanagement.model.Autor;
import com.gabrielpatriota.librarymanagement.model.Categoria;
import com.gabrielpatriota.librarymanagement.model.Livro;
import com.gabrielpatriota.librarymanagement.repository.AutorRepository;
import com.gabrielpatriota.librarymanagement.repository.CategoriaRepository;
import com.gabrielpatriota.librarymanagement.repository.LivroRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner carregarDadosIniciais(AutorRepository autorRepo,
                                            CategoriaRepository categoriaRepo,
                                            LivroRepository livroRepo) {
        return args -> {
            if (livroRepo.count() == 0) {
                Autor a1 = autorRepo.save(new Autor(null, "Machado de Assis", "Brasileira"));
                Autor a2 = autorRepo.save(new Autor(null, "Robert C. Martin", "Americana"));
                Autor a3 = autorRepo.save(new Autor(null, "Clarice Lispector", "Brasileira"));

                Categoria c1 = categoriaRepo.save(new Categoria(null, "Literatura Brasileira", "Clássicos nacionais"));
                Categoria c2 = categoriaRepo.save(new Categoria(null, "Tecnologia", "Engenharia de Software e Programação"));
                Categoria c3 = categoriaRepo.save(new Categoria(null, "Ficção", "Romances e contos"));

                livroRepo.save(new Livro(null, "Dom Casmurro", "978-8535914849", 1899, a1, c1));
                livroRepo.save(new Livro(null, "Código Limpo", "978-8576082675", 2008, a2, c2));
                livroRepo.save(new Livro(null, "A Hora da Estrela", "978-8532508126", 1977, a3, c3));
            }
        };
    }
}