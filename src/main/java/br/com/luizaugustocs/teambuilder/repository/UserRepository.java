package br.com.luizaugustocs.teambuilder.repository;

import br.com.luizaugustocs.teambuilder.domain.Team;
import br.com.luizaugustocs.teambuilder.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
}
