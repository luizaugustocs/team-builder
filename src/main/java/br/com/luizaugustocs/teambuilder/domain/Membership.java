package br.com.luizaugustocs.teambuilder.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.EmbeddedId;
import javax.persistence.MapsId;

@Data
@EqualsAndHashCode(of="id")
public class Membership {

    @EmbeddedId
    private MembershipId id;

    @MapsId("teamId")
    private Team team;
    @MapsId("userId")
    private User user;
    private Role role;
}
