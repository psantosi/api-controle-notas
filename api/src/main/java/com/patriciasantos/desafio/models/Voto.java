package com.patriciasantos.desafio.models;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "voto")
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "nota", nullable = false)
    @NonNull
    private Integer nota;

    @ManyToOne
    @JoinColumn(name = "filme_id", nullable = false, updatable = false)
    private Filme filme;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    public Voto() {
    }

    public Voto(final Long id, final Integer nota, final Filme filme, final Long usuario_id) {
        this.id = id;
        this.nota = nota;
        this.filme = filme;
        this.usuarioId = usuario_id;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNota() {
        return this.nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }


    public Filme getFilme() {
        return this.filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }
    

    public Long getUsuarioId() {
        return this.usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
    

    public static class VotoBuilder {

        private Voto voto;

        public VotoBuilder create() {
            voto = new Voto();
            return this;
        }

        public VotoBuilder comNota(final Integer nota) {
            voto.setNota(nota);
            return this;
        }

        public VotoBuilder comUsuario(final Long usuarioId) {
            voto.setUsuarioId(usuarioId);
            return this;
        }

        public VotoBuilder comFilme(final Filme filme) {
            voto.setFilme(filme);
            return this;
        }

        public Voto build() {
            return voto;
        }

    }
    

}