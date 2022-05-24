package br.com.luizaugustocs.teambuilder.repository;

import br.com.luizaugustocs.teambuilder.domain.Membership;
import br.com.luizaugustocs.teambuilder.domain.MembershipId;
import br.com.luizaugustocs.teambuilder.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MembershipRepository extends PagingAndSortingRepository<Membership, MembershipId> {
    Page<Membership> findByRole(Role role, Pageable pageable);
}
