package com.gabrielpatriota.librarymanagement.controller;

import com.gabrielpatriota.librarymanagement.model.Livro;
import com.gabrielpatriota.librarymanagement.service.AutorService;
import com.gabrielpatriota.librarymanagement.service.CategoriaService;
import com.gabrielpatriota.librarymanagement.service.LivroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LivroController {

    private final LivroService livroService;
    private final AutorService autorService;
    private final CategoriaService categoriaService;

    public LivroController(LivroService livroService, AutorService autorService, CategoriaService categoriaService) {
        this.livroService = livroService;
        this.autorService = autorService;
        this.categoriaService = categoriaService;
    }

    @GetMapping({"/", "/livros"})
    public String listarLivros(Model model) {
        model.addAttribute("livros", livroService.listarTodos());
        return "livros/index";
    }

    @GetMapping("/livros/novo")
    public String novoLivroForm(Model model) {
        model.addAttribute("livro", new Livro());
        model.addAttribute("autores", autorService.listarTodos());
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "livros/livro";
    }

    @PostMapping("/livros/salvar")
    public String salvarLivro(@ModelAttribute Livro livro) {
        if (livro.getAutor() != null && livro.getAutor().getId() != null) {
            livro.setAutor(autorService.buscarPorId(livro.getAutor().getId()));
        } else {
            livro.setAutor(null);
        }

        if (livro.getCategoria() != null && livro.getCategoria().getId() != null) {
            livro.setCategoria(categoriaService.buscarPorId(livro.getCategoria().getId()));
        } else {
            livro.setCategoria(null);
        }

        livroService.salvar(livro);
        return "redirect:/livros";
    }

    @GetMapping("/livros/editar/{id}")
    public String editarLivroForm(@PathVariable Long id, Model model) {
        Livro livro = livroService.buscarPorId(id);
        if (livro == null) {
            return "redirect:/livros";
        }
        model.addAttribute("livro", livro);
        model.addAttribute("autores", autorService.listarTodos());
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "livros/livro";
    }

    @GetMapping("/livros/excluir/{id}")
    public String excluirLivro(@PathVariable Long id) {
        livroService.excluir(id);
        return "redirect:/livros";
    }
}