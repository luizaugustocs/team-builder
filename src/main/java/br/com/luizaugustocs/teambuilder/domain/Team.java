package br.com.luizaugustocs.teambuilder.domain;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class Team {

    private UUID id;
    private String name;
    private User lead;
    private Set<Membership> members;

}
