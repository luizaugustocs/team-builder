package br.com.luizaugustocs.teambuilder.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
@EqualsAndHashCode(of = "id")
@Entity
@Table(name="team")
public class Team {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private String name;
    @ManyToOne
    @JoinColumn(name="lead_id")
    private User lead;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "team")
    private Set<Membership> members;

}
