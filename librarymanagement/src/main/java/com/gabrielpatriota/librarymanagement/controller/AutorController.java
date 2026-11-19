package com.gabrielpatriota.librarymanagement.controller;

import com.gabrielpatriota.librarymanagement.model.Autor;
import com.gabrielpatriota.librarymanagement.service.AutorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public String listarAutores(Model model) {
        model.addAttribute("autores", autorService.listarTodos());
        return "autores/index";
    }

    @GetMapping("/novo")
    public String novoAutorForm(Model model) {
        model.addAttribute("autor", new Autor());
        return "autores/autor";
    }

    @PostMapping("/salvar")
    public String salvarAutor(@ModelAttribute Autor autor) {
        autorService.salvar(autor);
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

    @GetMapping("/excluir/{id}")
    public String excluirAutor(@PathVariable Long id) {
        autorService.excluir(id);
        return "redirect:/autores";
    }
}