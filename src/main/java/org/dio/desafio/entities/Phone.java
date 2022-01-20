package org.dio.desafio.entities;

import lombok.*;
import org.dio.desafio.enums.PhoneType;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Entity
@Table(name = "Phones")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private PhoneType type;

    @Column(nullable = false)
    private String number;

}
