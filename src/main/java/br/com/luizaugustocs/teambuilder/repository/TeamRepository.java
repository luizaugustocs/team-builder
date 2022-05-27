package br.com.luizaugustocs.teambuilder.repository;

import br.com.luizaugustocs.teambuilder.domain.Team;
import br.com.luizaugustocs.teambuilder.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TeamRepository extends CrudRepository<Team, UUID> {
    boolean existsByLead(User user);
}
