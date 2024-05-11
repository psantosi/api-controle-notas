package com.patriciasantos.desafio.models.enums;

import java.util.Arrays;
import java.util.Objects;

public enum PerfilEnum {
    
    ADMIN(1, "ROLE_ADMIN"),
    USUARIO(2, "ROLE_USUARIO");

    private Integer codigo;
    private String descricao;

    PerfilEnum(final Integer codigo, final String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static PerfilEnum toEnum(final Integer codigo) {
        if (Objects.isNull(codigo)) {
            return null;
        }

        return Arrays
            .stream(values())
            .filter(v -> v.getCodigo().equals(codigo))
            .findFirst()
            .orElse(null);
    }

}
