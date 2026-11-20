package com.gabrielpatriota.librarymanagement.controller;

import com.gabrielpatriota.librarymanagement.model.Categoria;
import com.gabrielpatriota.librarymanagement.service.CategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listarCategorias(Model model) {
        ArrayList<Categoria> categorias = new ArrayList<>(categoriaService.listarTodas());
        model.addAttribute("categorias", categorias);
        return "categorias/index";
    }

    @GetMapping("/novo")
    public String novaCategoriaForm(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorias/categoria";
    }

    @PostMapping("/salvar")
    public String salvarCategoria(@ModelAttribute Categoria categoria,
                                  RedirectAttributes redirectAttributes) {
        categoriaService.salvar(categoria);
        redirectAttributes.addFlashAttribute("mensagem", "Categoria salva com sucesso.");
        return "redirect:/categorias";
    }

    @GetMapping("/editar/{id}")
    public String editarCategoriaForm(@PathVariable Long id, Model model) {
        Categoria categoria = categoriaService.buscarPorId(id);
        if (categoria == null) {
            return "redirect:/categorias";
        }
        model.addAttribute("categoria", categoria);
        return "categorias/categoria";
    }

    @PostMapping("/excluir/{id}")
    public String excluirCategoria(@PathVariable Long id,
                                   RedirectAttributes redirectAttributes) {
        boolean excluida = categoriaService.excluir(id);
        if (excluida) {
            redirectAttributes.addFlashAttribute("mensagem", "Categoria excluída com sucesso.");
        } else {
            redirectAttributes.addFlashAttribute(
                    "erro", "Não foi possível excluir a categoria. Verifique se ela está vinculada a algum livro."
            );
        }
        return "redirect:/categorias";
    }
}
