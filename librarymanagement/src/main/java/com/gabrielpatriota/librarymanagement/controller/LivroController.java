package com.gabrielpatriota.librarymanagement.controller;

import com.gabrielpatriota.librarymanagement.model.Livro;
import com.gabrielpatriota.librarymanagement.service.AutorService;
import com.gabrielpatriota.librarymanagement.service.CategoriaService;
import com.gabrielpatriota.librarymanagement.service.LivroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
public class LivroController {

    private final LivroService livroService;
    private final AutorService autorService;
    private final CategoriaService categoriaService;

    public LivroController(LivroService livroService,
                           AutorService autorService,
                           CategoriaService categoriaService) {
        this.livroService = livroService;
        this.autorService = autorService;
        this.categoriaService = categoriaService;
    }

    @GetMapping({"/", "/livros"})
    public String listarLivros(Model model) {
        ArrayList<Livro> livros = new ArrayList<>(livroService.listarTodos());
        model.addAttribute("livros", livros);
        return "livros/index";
    }

    @GetMapping("/livros/novo")
    public String novoLivroForm(Model model) {
        model.addAttribute("livro", new Livro());
        carregarOpcoesDoFormulario(model);
        return "livros/livro";
    }

    @PostMapping("/livros/salvar")
    public String salvarLivro(@ModelAttribute Livro livro,
                              RedirectAttributes redirectAttributes) {
        Livro salvo = livroService.salvar(livro);
        if (salvo == null) {
            redirectAttributes.addFlashAttribute(
                    "erro", "Não foi possível salvar o livro. Confira os dados e selecione um autor e uma categoria válidos."
            );
            return "redirect:/livros/novo";
        }
        redirectAttributes.addFlashAttribute("mensagem", "Livro salvo com sucesso.");
        return "redirect:/livros";
    }

    @GetMapping("/livros/editar/{id}")
    public String editarLivroForm(@PathVariable Long id, Model model) {
        Livro livro = livroService.buscarPorId(id);
        if (livro == null) {
            return "redirect:/livros";
        }
        model.addAttribute("livro", livro);
        carregarOpcoesDoFormulario(model);
        return "livros/livro";
    }

    @PostMapping("/livros/excluir/{id}")
    public String excluirLivro(@PathVariable Long id,
                               RedirectAttributes redirectAttributes) {
        boolean excluido = livroService.excluir(id);
        if (excluido) {
            redirectAttributes.addFlashAttribute("mensagem", "Livro excluído com sucesso.");
        } else {
            redirectAttributes.addFlashAttribute(
                    "erro", "Não foi possível excluir o livro. Ele pode estar vinculado a um empréstimo."
            );
        }
        return "redirect:/livros";
    }

    private void carregarOpcoesDoFormulario(Model model) {
        model.addAttribute("autores", new ArrayList<>(autorService.listarTodos()));
        model.addAttribute("categorias", new ArrayList<>(categoriaService.listarTodas()));
    }
}
