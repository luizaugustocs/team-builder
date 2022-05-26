package br.com.luizaugustocs.teambuilder.controller;


import br.com.luizaugustocs.teambuilder.domain.Membership;
import br.com.luizaugustocs.teambuilder.domain.Role;
import br.com.luizaugustocs.teambuilder.dto.MembershipDTO;
import br.com.luizaugustocs.teambuilder.dto.NewRoleDTO;
import br.com.luizaugustocs.teambuilder.dto.RoleAssignmentDTO;
import br.com.luizaugustocs.teambuilder.dto.RoleDTO;
import br.com.luizaugustocs.teambuilder.service.MembershipService;
import br.com.luizaugustocs.teambuilder.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;
    private final MembershipService membershipService;

    public RoleController(RoleService roleService, MembershipService membershipService) {
        this.roleService = roleService;
        this.membershipService = membershipService;
    }

    @GetMapping
    public ResponseEntity<Page<RoleDTO>> findAll(Pageable page) {
        Page<Role> result = this.roleService.findAll(page);
        Page<RoleDTO> response = result.map(RoleDTO::from);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@RequestBody @Valid NewRoleDTO newRoleDTO) {
        Role createdRole = this.roleService.createRole(newRoleDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdRole.getId())
                .toUri();

        RoleDTO response = RoleDTO.from(createdRole);
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping(value = "/by-membership")
    public ResponseEntity<RoleDTO> findByMembership(@Valid MembershipDTO membershipDTO) {
        Membership membership = this.membershipService.findByDTO(membershipDTO);
        RoleDTO role = RoleDTO.from(membership.getRole());
        return ResponseEntity.ok(role);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDTO> findById(@PathVariable UUID roleId) {
        Role result = this.roleService.findById(roleId);
        return ResponseEntity.ok(RoleDTO.from(result));
    }

    @PutMapping("/{roleId}/assign")
    public ResponseEntity<RoleAssignmentDTO> assignRole(@PathVariable UUID roleId, @Valid @RequestBody MembershipDTO membership) {
        return ResponseEntity.ok(this.roleService.assignRole(roleId, membership));
    }

    @GetMapping("/{roleId}/memberships")
    public ResponseEntity<Page<MembershipDTO>> getMembershipRoles(@PathVariable UUID roleId, Pageable pageable) {
        Role role = this.roleService.findById(roleId);
        Page<Membership> result = this.membershipService.findByRole(role, pageable);
        Page<MembershipDTO> response = result.map(MembershipDTO::from);
        return ResponseEntity.ok(response);
    }
}
