package workforge.project;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import workforge.team.TeamResponse;

@RestController
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/api/organizations/{organizationId}/projects")
    public Project createProject(
            @PathVariable Long organizationId,
            @Valid @RequestBody CreateProjectRequest request) {

        return projectService.createProject(
                organizationId,
                request.getName(),
                request.getSlug());
    }

    @GetMapping("/api/organizations/{organizationId}/projects/{projectId}")
    public ResponseEntity<ProjectResponse> getProjectById(
            @PathVariable Long organizationId,
            @PathVariable Long projectId) {

        return ResponseEntity.ok(
                projectService.getProjectById(
                        organizationId,
                        projectId));
    }

    @GetMapping("/api/organizations/{organizationId}/projects")
    public ResponseEntity<List<ProjectResponse>> getProjectsByOrganization(
            @PathVariable Long organizationId) {

        return ResponseEntity.ok(
                projectService.getProjectsByOrganization(organizationId));
    }

    @PutMapping("/api/organizations/{organizationId}/projects/{projectId}")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long organizationId,
            @PathVariable Long projectId,
            @Valid @RequestBody UpdateProjectRequest request) {

        return ResponseEntity.ok(
                projectService.updateProject(
                        organizationId,
                        projectId,
                        request.getName(),
                        request.getSlug()));
    }

    @DeleteMapping("/api/organizations/{organizationId}/projects/{projectId}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable Long organizationId,
            @PathVariable Long projectId) {

        projectService.deleteProject(organizationId, projectId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/organizations/{organizationId}/projects/{projectId}/teams/{teamId}")
    public ResponseEntity<Void> addTeamToProject(
            @PathVariable Long organizationId,
            @PathVariable Long projectId,
            @PathVariable Long teamId) {

        projectService.addTeamToProject(
                organizationId,
                projectId,
                teamId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/api/organizations/{organizationId}/projects/{projectId}/teams/{teamId}")
    public ResponseEntity<Void> removeTeamFromProject(
            @PathVariable Long organizationId,
            @PathVariable Long projectId,
            @PathVariable Long teamId) {

        projectService.removeTeamFromProject(
                organizationId,
                projectId,
                teamId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/organizations/{organizationId}/projects/{projectId}/teams")
    public ResponseEntity<Set<TeamResponse>> getProjectTeams(
            @PathVariable Long organizationId,
            @PathVariable Long projectId) {

        return ResponseEntity.ok(
                projectService.getProjectTeams(
                        organizationId,
                        projectId));
    }
}