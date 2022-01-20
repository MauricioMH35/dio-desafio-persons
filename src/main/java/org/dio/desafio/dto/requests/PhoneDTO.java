package org.dio.desafio.dto.requests;

import lombok.*;
import org.dio.desafio.enums.PhoneType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class PhoneDTO {

    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PhoneType type;

    @NotNull
    @Size(min = 13, max = 14)
    private String number;
}