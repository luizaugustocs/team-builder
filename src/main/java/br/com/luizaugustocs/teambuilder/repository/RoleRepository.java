package br.com.luizaugustocs.teambuilder.repository;

import br.com.luizaugustocs.teambuilder.domain.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface RoleRepository extends PagingAndSortingRepository<Role, UUID> {
}
