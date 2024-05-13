package com.patriciasantos.desafio.models.enums;

import java.util.Arrays;
import java.util.Objects;

public enum ProfileEnum {
    
    ADMIN(1, "ROLE_ADMIN"),
    USER(2, "ROLE_USER");

    private Integer code;
    private String description;

    ProfileEnum(final Integer code, final String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ProfileEnum toEnum(final Integer code) {
        if (Objects.isNull(code)) {
            return null;
        }

        return Arrays
            .stream(values())
            .filter(v -> v.getCode().equals(code))
            .findFirst()
            .orElse(null);
    }

}
