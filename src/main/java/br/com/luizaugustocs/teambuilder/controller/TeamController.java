package br.com.luizaugustocs.teambuilder.controller;

import br.com.luizaugustocs.teambuilder.service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.UUID;

@RestController
@RequestMapping("/teams")
@Transactional
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID teamId) {
        this.teamService.delete(teamId);
        return ResponseEntity.noContent().build();
    }
}
