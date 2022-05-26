package br.com.luizaugustocs.teambuilder.dto;

import br.com.luizaugustocs.teambuilder.domain.Membership;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class MembershipDTO {

    @NotNull(message = "User id is required")
    private UUID userId;
    @NotNull(message ="Team id is required")
    private UUID teamId;

    public static MembershipDTO from(Membership membership) {
        MembershipDTO dto = new MembershipDTO();
        dto.setUserId(membership.getId().getUserId());
        dto.setTeamId(membership.getId().getTeamId());
        return dto;
    }
}
