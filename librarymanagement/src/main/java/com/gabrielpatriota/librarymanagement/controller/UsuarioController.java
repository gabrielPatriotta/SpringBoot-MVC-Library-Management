package com.gabrielpatriota.librarymanagement.controller;

import com.gabrielpatriota.librarymanagement.model.Usuario;
import com.gabrielpatriota.librarymanagement.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listarUsuarios(Model model) {
        ArrayList<Usuario> usuarios = new ArrayList<>(usuarioService.listarTodos());
        model.addAttribute("usuarios", usuarios);
        return "usuarios/index";
    }

    @GetMapping("/novo")
    public String novoUsuarioForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/usuario";
    }

    @PostMapping("/salvar")
    public String salvarUsuario(@ModelAttribute Usuario usuario,
                                RedirectAttributes redirectAttributes) {
        usuarioService.salvar(usuario);
        redirectAttributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso.");
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuarioForm(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario == null) {
            return "redirect:/usuarios";
        }
        model.addAttribute("usuario", usuario);
        return "usuarios/usuario";
    }

    @PostMapping("/excluir/{id}")
    public String excluirUsuario(@PathVariable Long id,
                                 RedirectAttributes redirectAttributes) {
        boolean excluido = usuarioService.excluir(id);
        if (excluido) {
            redirectAttributes.addFlashAttribute("mensagem", "Usuário excluído com sucesso.");
        } else {
            redirectAttributes.addFlashAttribute(
                    "erro", "Não foi possível excluir o usuário. Ele pode possuir empréstimos registrados."
            );
        }
        return "redirect:/usuarios";
    }
}
