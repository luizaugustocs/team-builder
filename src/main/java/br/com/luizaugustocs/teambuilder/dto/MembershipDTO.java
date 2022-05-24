package br.com.luizaugustocs.teambuilder.dto;

import br.com.luizaugustocs.teambuilder.domain.Membership;
import liquibase.pro.packaged.M;
import lombok.Data;

import java.util.UUID;

@Data
public class MembershipDTO {
    private UUID userId;
    private UUID teamId;

    public static MembershipDTO from(Membership membership) {
        MembershipDTO dto = new MembershipDTO();
        dto.setUserId(membership.getId().getUserId());
        dto.setTeamId(membership.getId().getTeamId());
        return dto;
    }
}
