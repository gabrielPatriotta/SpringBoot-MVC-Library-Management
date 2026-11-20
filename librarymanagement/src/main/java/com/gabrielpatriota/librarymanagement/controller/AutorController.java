package com.gabrielpatriota.librarymanagement.controller;

import com.gabrielpatriota.librarymanagement.model.Autor;
import com.gabrielpatriota.librarymanagement.service.AutorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping("/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public String listarAutores(Model model) {
        ArrayList<Autor> autores = new ArrayList<>(autorService.listarTodos());
        model.addAttribute("autores", autores);
        return "autores/index";
    }

    @GetMapping("/novo")
    public String novoAutorForm(Model model) {
        model.addAttribute("autor", new Autor());
        return "autores/autor";
    }

    @PostMapping("/salvar")
    public String salvarAutor(@ModelAttribute Autor autor,
                              RedirectAttributes redirectAttributes) {
        autorService.salvar(autor);
        redirectAttributes.addFlashAttribute("mensagem", "Autor salvo com sucesso.");
        return "redirect:/autores";
    }

    @GetMapping("/editar/{id}")
    public String editarAutorForm(@PathVariable Long id, Model model) {
        Autor autor = autorService.buscarPorId(id);
        if (autor == null) {
            return "redirect:/autores";
        }
        model.addAttribute("autor", autor);
        return "autores/autor";
    }

    @PostMapping("/excluir/{id}")
    public String excluirAutor(@PathVariable Long id,
                               RedirectAttributes redirectAttributes) {
        boolean excluido = autorService.excluir(id);
        if (excluido) {
            redirectAttributes.addFlashAttribute("mensagem", "Autor excluído com sucesso.");
        } else {
            redirectAttributes.addFlashAttribute(
                    "erro", "Não foi possível excluir o autor. Verifique se ele está vinculado a algum livro."
            );
        }
        return "redirect:/autores";
    }
}
