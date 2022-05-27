package br.com.luizaugustocs.teambuilder.service;

import br.com.luizaugustocs.teambuilder.domain.User;
import br.com.luizaugustocs.teambuilder.exception.BadRequestException;
import br.com.luizaugustocs.teambuilder.exception.NotFoundException;
import br.com.luizaugustocs.teambuilder.repository.MembershipRepository;
import br.com.luizaugustocs.teambuilder.repository.TeamRepository;
import br.com.luizaugustocs.teambuilder.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;
    private final TeamRepository teamRepository;

    public UserService(UserRepository userRepository, MembershipRepository membershipRepository, TeamRepository teamRepository) {
        this.userRepository = userRepository;
        this.membershipRepository = membershipRepository;
        this.teamRepository = teamRepository;
    }


    public void delete(UUID userId) {

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user", userId));

        if (this.teamRepository.existsByLead(user)) {
            throw new BadRequestException(String.format("The user [%s] cannot be removed because it is a team lead.", userId));
        }

        this.membershipRepository.deleteByUser(user);
        this.userRepository.delete(user);
    }

}
