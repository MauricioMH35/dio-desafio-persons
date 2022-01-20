package org.dio.desafio.dto.requests;

import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class PersonDTO {

    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    private String firstname;

    @NotNull
    @Size(min = 2, max = 100)
    private String lastname;

    @NotNull
    @CPF
    private String cpf;

    private String birthDate;

    @Valid
    @NotNull
    private List<PhoneDTO> phones;

    @NotNull
    private Boolean isActive;

}
