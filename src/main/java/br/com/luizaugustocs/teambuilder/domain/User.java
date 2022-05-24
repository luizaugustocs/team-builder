package br.com.luizaugustocs.teambuilder.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class User {

    private UUID id;
    private String firstName;
    private String lastName;
    private String displayName;
    private String avatarUrl;
    private String location;
}
