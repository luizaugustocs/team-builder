package br.com.luizaugustocs.teambuilder.domain;

import br.com.luizaugustocs.teambuilder.dto.MembershipDTO;
import liquibase.pro.packaged.M;
import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Data
@Embeddable
public class MembershipId implements Serializable {

    private UUID teamId;
    private UUID userId;

}
