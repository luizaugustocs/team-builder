package br.com.luizaugustocs.teambuilder.service;

import br.com.luizaugustocs.teambuilder.domain.Membership;
import br.com.luizaugustocs.teambuilder.domain.Role;
import br.com.luizaugustocs.teambuilder.dto.MembershipDTO;
import br.com.luizaugustocs.teambuilder.dto.NewRoleDTO;
import br.com.luizaugustocs.teambuilder.dto.RoleAssignmentDTO;
import br.com.luizaugustocs.teambuilder.exception.NotFoundException;
import br.com.luizaugustocs.teambuilder.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final MembershipService membershipService;

    public RoleService(RoleRepository roleRepository, MembershipService membershipService) {
        this.roleRepository = roleRepository;
        this.membershipService = membershipService;
    }

    public Page<Role> findAll(Pageable pageable) {
        return this.roleRepository.findAll(pageable);
    }

    public Role createRole(NewRoleDTO newRole) {

        Role role = new Role();
        role.setName(newRole.getName());
        role.setIsDefault(Boolean.FALSE);

        return this.roleRepository.save(role);
    }

    public Role findById(UUID roleId) {
        return this.roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("role", roleId));
    }

    public RoleAssignmentDTO assignRole(UUID roleId, MembershipDTO membershipDTO) {

        Role role = this.findById(roleId);

        Membership membership = this.membershipService.assignRole(role, membershipDTO);

        return RoleAssignmentDTO.from(role, membership);
    }
}
