package com.patriciasantos.desafio.models.view;

import java.io.Serializable;
import java.util.List;

import com.patriciasantos.desafio.models.Filme;
import com.patriciasantos.desafio.models.to.VotoTO;

public class FilmeView implements Serializable {

    private Long id;
    private String titulo;
    private String diretor;
    private String genero;
    private String descricao;
    private List<VotoTO> votos;


    public FilmeView() {
    }

    public FilmeView(final Filme filme, Long usuarioId) {
        this.id = filme.getId();
        this.titulo = filme.getTitulo();
        this.diretor = filme.getDiretor();
        this.genero = filme.getGenero();
        this.descricao = filme.getDescricao();
        this.votos = filme.getVotos().stream().map(voto -> new VotoTO(voto, usuarioId)).toList();
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDiretor() {
        return this.diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public String getGenero() {
        return this.genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<VotoTO> getVotos() {
        return this.votos;
    }

    public void setVotos(List<VotoTO> votos) {
        this.votos = votos;
    }

    public Integer getMediaVotos() {
        if (this.votos.isEmpty()) {
            return 0;
        }

        final Integer quantidadeVotos = this.votos.size();
        final Integer somaDosVotos = this.votos.stream().map(VotoTO::getNota).reduce(0, Integer::sum);
        return somaDosVotos / quantidadeVotos;
    }

    public static FilmeView toView(final Filme filme, final Long usuarioId) {
        return new FilmeView(filme, usuarioId);
    }

}
