package br.com.luizaugustocs.teambuilder.dto;

import br.com.luizaugustocs.teambuilder.domain.Membership;
import br.com.luizaugustocs.teambuilder.domain.Role;
import lombok.Data;

import java.util.UUID;

@Data
public class RoleAssignmentDTO {
    private UUID userId;
    private UUID teamId;
    private UUID roleId;

    public static RoleAssignmentDTO from(Role role, Membership membership) {
        RoleAssignmentDTO dto = new RoleAssignmentDTO();
        dto.setRoleId(role.getId());
        dto.setUserId(membership.getId().getUserId());
        dto.setTeamId(membership.getId().getTeamId());
        return dto;
    }
}
