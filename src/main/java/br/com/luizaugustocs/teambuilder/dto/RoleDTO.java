package br.com.luizaugustocs.teambuilder.dto;

import br.com.luizaugustocs.teambuilder.domain.Role;
import lombok.Data;

import java.util.UUID;

@Data
public class RoleDTO {

    private UUID id;
    private String name;

    public static RoleDTO from(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());
        return dto;
    }


}
