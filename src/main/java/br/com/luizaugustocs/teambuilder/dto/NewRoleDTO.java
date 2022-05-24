package br.com.luizaugustocs.teambuilder.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NewRoleDTO {

    @NotBlank(message = "New roles must have a name.")
    private String name;
}
