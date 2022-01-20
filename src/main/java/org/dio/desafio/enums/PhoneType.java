package org.dio.desafio.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PhoneType {
    HOME("Casa"),
    MOBILE("Móvel"),
    COMMERCIAL("Comercial");

    private String description;
}
