package com.gabrielpatriota.librarymanagement.controller;

import com.gabrielpatriota.librarymanagement.model.Emprestimo;
import com.gabrielpatriota.librarymanagement.model.Livro;
import com.gabrielpatriota.librarymanagement.model.Usuario;
import com.gabrielpatriota.librarymanagement.service.EmprestimoService;
import com.gabrielpatriota.librarymanagement.service.LivroService;
import com.gabrielpatriota.librarymanagement.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;
    private final LivroService livroService;
    private final UsuarioService usuarioService;

    public EmprestimoController(EmprestimoService emprestimoService,
                                LivroService livroService,
                                UsuarioService usuarioService) {
        this.emprestimoService = emprestimoService;
        this.livroService = livroService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listarEmprestimos(Model model) {
        ArrayList<Emprestimo> emprestimos = new ArrayList<>(emprestimoService.listarTodos());
        model.addAttribute("emprestimos", emprestimos);
        return "emprestimos/index";
    }

    @GetMapping("/novo")
    public String novoEmprestimoForm(Model model) {
        model.addAttribute("livros", new ArrayList<>(livroService.listarDisponiveis()));
        model.addAttribute("usuarios", new ArrayList<>(usuarioService.listarTodos()));
        return "emprestimos/emprestimo";
    }

    @PostMapping("/salvar")
    public String salvarEmprestimo(@RequestParam Long livroId,
                                   @RequestParam Long usuarioId,
                                   RedirectAttributes redirectAttributes) {
        Emprestimo emprestimo = new Emprestimo();
        Livro livro = new Livro();
        livro.setId(livroId);
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);

        boolean realizado = emprestimoService.realizarEmprestimo(emprestimo);
        if (realizado) {
            redirectAttributes.addFlashAttribute("mensagem", "Empréstimo registrado com sucesso.");
        } else {
            redirectAttributes.addFlashAttribute(
                    "erro", "Não foi possível realizar o empréstimo. Confira o usuário e se o livro continua disponível."
            );
        }
        return "redirect:/emprestimos";
    }

    @PostMapping("/devolver/{id}")
    public String registrarDevolucao(@PathVariable Long id,
                                     RedirectAttributes redirectAttributes) {
        boolean devolvido = emprestimoService.registrarDevolucao(id);
        if (devolvido) {
            redirectAttributes.addFlashAttribute("mensagem", "Devolução registrada com sucesso.");
        } else {
            redirectAttributes.addFlashAttribute(
                    "erro", "Não foi possível registrar a devolução. O empréstimo pode não existir ou já ter sido devolvido."
            );
        }
        return "redirect:/emprestimos";
    }

    @PostMapping("/excluir/{id}")
    public String excluirEmprestimo(@PathVariable Long id,
                                    RedirectAttributes redirectAttributes) {
        boolean excluido = emprestimoService.excluir(id);
        if (excluido) {
            redirectAttributes.addFlashAttribute("mensagem", "Registro de empréstimo excluído com sucesso.");
        } else {
            redirectAttributes.addFlashAttribute("erro", "Não foi possível excluir o registro de empréstimo.");
        }
        return "redirect:/emprestimos";
    }
}
