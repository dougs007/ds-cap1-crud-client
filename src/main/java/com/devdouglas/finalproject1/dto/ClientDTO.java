package com.devdouglas.finalproject1.dto;

import com.devdouglas.finalproject1.entities.Client;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDTO implements Serializable {

    private Long id;
    private String name;
    private String cpf;
    private Double income;
    private Instant birthDate;
    private Integer children;

    public static ClientDTO build(Client entity) {
        return ClientDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .cpf(entity.getCpf())
                .income(entity.getIncome())
                .birthDate(entity.getBirthDate())
                .children(entity.getChildren())
                .build();
    }
}
