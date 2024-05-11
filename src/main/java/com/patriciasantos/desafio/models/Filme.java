package com.patriciasantos.desafio.models;

import java.util.ArrayList;
import java.util.List;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "filme")
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "titulo", length = 100, nullable = false)
    @NonNull
    @NotEmpty
    @Size(min = 2, max = 100)
    private String titulo;

    @Column(name = "diretor", length = 100, nullable = false)
    @NonNull
    @NotEmpty
    @Size(min = 2, max = 100)
    private String diretor;

    @Column(name = "genero", length = 50, nullable = false)
    @NonNull
    @NotEmpty
    @Size(min = 2, max = 50)
    private String genero;

    @Column(name = "descricao", length = 255, nullable = false)
    @NonNull
    @NotEmpty
    @Size(min = 2, max = 255)
    private String descricao;

    @OneToMany(mappedBy = "filme")
    private List<Voto> votos = new ArrayList<Voto>();
    

    public Filme() {
    }


    public Filme(final Long id, final String titulo, final String diretor, final String genero, final String descricao) {
        this.id = id;
        this.titulo = titulo;
        this.diretor = diretor;
        this.genero = genero;
        this.descricao = descricao;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(final String titulo) {
        this.titulo = titulo;
    }

    public String getDiretor() {
        return this.diretor;
    }

    public void setDiretor(final String diretor) {
        this.diretor = diretor;
    }

    public String getGenero() {
        return this.genero;
    }

    public void setGenero(final String genero) {
        this.genero = genero;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }  


    public List<Voto> getVotos() {
        return votos;
    }


    public void setVotos(List<Voto> votos) {
        this.votos = votos;
    }

    public void addVotos(Voto votos) {
        if (this.votos.isEmpty()) {
            this.votos = new ArrayList<>();
        }

        this.votos.add(votos);
    }



    public static class FilmeBuilder {

        private Filme filme;

        public FilmeBuilder create() {
            filme = new Filme();
            return this;
        }

        public FilmeBuilder comTitulo(final String titulo) {
            filme.setTitulo(titulo);
            return this;
        }

        public FilmeBuilder comDiretor(final String diretor) {
            filme.setDiretor(diretor);
            return this;
        }

        public FilmeBuilder comGenero(final String genero) {
            filme.setGenero(genero);
            return this;
        }

        public FilmeBuilder comDescricao(final String descricao) {
            filme.setDescricao(descricao);
            return this;
        }

        public Filme build() {
            return filme;
        }

    }

}