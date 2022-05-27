package br.com.luizaugustocs.teambuilder.service;

import br.com.luizaugustocs.teambuilder.domain.Team;
import br.com.luizaugustocs.teambuilder.exception.NotFoundException;
import br.com.luizaugustocs.teambuilder.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void delete(UUID userId) {

        Team team = this.teamRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("team", userId));

        this.teamRepository.delete(team);
    }

}
