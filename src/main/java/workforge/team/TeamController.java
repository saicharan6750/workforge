package workforge.team;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("/api/organizations/{organizationId}/teams")
    public Team createTeam(
            @PathVariable Long organizationId,
            @Valid @RequestBody CreateTeamRequest request) {

        return teamService.createTeam(
                organizationId,
                request.getName(),
                request.getSlug());
    }

    @GetMapping("/api/organizations/{organizationId}/teams/{teamId}")
    public ResponseEntity<TeamResponse> getTeamById(
            @PathVariable Long organizationId,
            @PathVariable Long teamId) {

        return ResponseEntity.ok(
                teamService.getTeamById(
                        organizationId,
                        teamId));
    }

    @GetMapping("/api/organizations/{organizationId}/teams")
    public ResponseEntity<List<TeamResponse>> getTeamsByOrganization(
            @PathVariable Long organizationId) {

        return ResponseEntity.ok(
                teamService.getTeamsByOrganization(organizationId));
    }

    @PutMapping("/api/organizations/{organizationId}/teams/{teamId}")
    public ResponseEntity<Team> updateTeam(
            @PathVariable Long organizationId,
            @PathVariable Long teamId,
            @Valid @RequestBody UpdateTeamRequest request) {

        return ResponseEntity.ok(
                teamService.updateTeam(
                        organizationId,
                        teamId,
                        request.getName(),
                        request.getSlug()));
    }

    @DeleteMapping("/api/organizations/{organizationId}/teams/{teamId}")
    public ResponseEntity<Void> deleteTeam(
            @PathVariable Long organizationId,
            @PathVariable Long teamId) {

        teamService.deleteTeam(organizationId, teamId);

        return ResponseEntity.noContent().build();
    }
}