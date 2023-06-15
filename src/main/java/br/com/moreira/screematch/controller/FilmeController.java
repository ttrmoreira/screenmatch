package br.com.moreira.screematch.controller;

import br.com.moreira.screematch.domain.filme.DadosAlteracaoFilme;
import br.com.moreira.screematch.domain.filme.DadosCadastroFilme;
import br.com.moreira.screematch.domain.filme.Filme;
import br.com.moreira.screematch.domain.filme.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeRepository repository;
    @GetMapping ("/formulario")
    public String carregaPaginaFormulario(Long id, Model model){
        if(id != null){
            var filme = repository.getReferenceById(id);
            model.addAttribute("filme", filme);
        }
        return "filmes/formulario";
    }

    @GetMapping
    public String carregaPaginaListagem(Model model){
        model.addAttribute("listaFilmes", repository.findAll());
        return "filmes/listagem";
    }
    @PostMapping
    @Transactional
    public String cadastraFilme(DadosCadastroFilme dados){
        var filme = new Filme(dados);
        repository.save(filme);
        return "redirect:/filmes";

    }

    @PutMapping
    @Transactional
    public String alteraFilme(DadosAlteracaoFilme dados){
        var filme = repository.getReferenceById((dados.id()));
        filme.atualizaDados(dados);
        return "redirect:/filmes";

    }


    @DeleteMapping
    @Transactional
    public String removeFilme(Long id){
        repository.deleteById(id);
        return "redirect:/filmes";
    }
}
