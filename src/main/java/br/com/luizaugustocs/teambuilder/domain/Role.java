package br.com.luizaugustocs.teambuilder.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(of = "id")
public class Role {

    private UUID id;
    private String name;
    private Boolean isDefault;
}
