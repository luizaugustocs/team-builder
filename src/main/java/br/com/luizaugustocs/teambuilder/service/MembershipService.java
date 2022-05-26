package br.com.luizaugustocs.teambuilder.service;

import br.com.luizaugustocs.teambuilder.domain.Membership;
import br.com.luizaugustocs.teambuilder.domain.MembershipId;
import br.com.luizaugustocs.teambuilder.domain.Role;
import br.com.luizaugustocs.teambuilder.dto.MembershipDTO;
import br.com.luizaugustocs.teambuilder.exception.NotFoundException;
import br.com.luizaugustocs.teambuilder.repository.MembershipRepository;
import br.com.luizaugustocs.teambuilder.repository.TeamRepository;
import br.com.luizaugustocs.teambuilder.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MembershipService {

    private final MembershipRepository membershipRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public MembershipService(MembershipRepository membershipRepository, TeamRepository teamRepository, UserRepository userRepository) {
        this.membershipRepository = membershipRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    public Membership assignRole(Role role, MembershipDTO membershipDTO) {

        Membership membership = this.findByDTO(membershipDTO);
        membership.setRole(role);
        return this.membershipRepository.save(membership);

    }

    public Membership findByDTO(MembershipDTO membershipDTO) {

        if (!this.teamRepository.existsById(membershipDTO.getTeamId())) {
            throw new NotFoundException("team", membershipDTO.getTeamId());
        }
        if (!this.userRepository.existsById(membershipDTO.getUserId())) {
            throw new NotFoundException("user", membershipDTO.getUserId());
        }

        MembershipId id = new MembershipId();
        id.setTeamId(membershipDTO.getTeamId());
        id.setUserId(membershipDTO.getUserId());

        return this.membershipRepository.findById(id)
                .orElseThrow(() -> {
                    var formattedMessage = String.format("The user [%s] is not a member of the team [%s].", membershipDTO.getUserId(), membershipDTO.getTeamId());
                    return new NotFoundException(formattedMessage);
                });
    }

    public Page<Membership> findByRole(Role role, Pageable pageable) {
        return this.membershipRepository.findByRole(role, pageable);
    }
}
