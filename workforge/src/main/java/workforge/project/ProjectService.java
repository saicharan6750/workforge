package workforge.project;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import workforge.exception.DuplicateProjectSlugException;
import workforge.exception.OrganizationNotFoundException;
import workforge.exception.ProjectNotFoundException;
import workforge.exception.TeamNotFoundException;
import workforge.organization.Organization;
import workforge.organization.OrganizationRepository;
import workforge.team.Team;
import workforge.team.TeamRepository;
import workforge.team.TeamResponse;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TeamRepository teamRepository;
    private final OrganizationRepository organizationRepository;

    public ProjectService(
            ProjectRepository projectRepository,
            TeamRepository teamRepository,
            OrganizationRepository organizationRepository) {

        this.projectRepository = projectRepository;
        this.teamRepository = teamRepository;
        this.organizationRepository = organizationRepository;
    }

    public Project createProject(Long organizationId, String name, String slug) {

        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException(
                        "Organization not found with id: " + organizationId));

        name = name.trim();
        slug = slug.trim().toLowerCase();

        if (projectRepository.existsByOrganizationIdAndSlug(organizationId, slug)) {
            throw new DuplicateProjectSlugException(
                    "Project slug already exists in this organization");
        }

        Project project = new Project(name, slug, organization);

        return projectRepository.save(project);
    }

    public ProjectResponse getProjectById(Long organizationId, Long projectId) {

        Project project = getProjectEntity(organizationId, projectId);

        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getSlug());
    }

    public List<ProjectResponse> getProjectsByOrganization(Long organizationId) {

        organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException(
                        "Organization not found with id: " + organizationId));

        return projectRepository.findByOrganizationId(organizationId)
                .stream()
                .map(project -> new ProjectResponse(
                        project.getId(),
                        project.getName(),
                        project.getSlug()))
                .collect(Collectors.toList());
    }

    public Project updateProject(
            Long organizationId,
            Long projectId,
            String name,
            String slug) {

        Project project = getProjectEntity(organizationId, projectId);

        name = name.trim();
        slug = slug.trim().toLowerCase();

        if (projectRepository.existsByOrganizationIdAndSlugAndIdNot(
                organizationId, slug, projectId)) {

            throw new DuplicateProjectSlugException(
                    "Project slug already exists in this organization");
        }

        project.setName(name);
        project.setSlug(slug);

        return projectRepository.save(project);
    }

    public void deleteProject(Long organizationId, Long projectId) {

        Project project = getProjectEntity(organizationId, projectId);

        projectRepository.delete(project);
    }

    @Transactional
    public void addTeamToProject(
            Long organizationId,
            Long projectId,
            Long teamId) {

        Project project = getProjectEntity(organizationId, projectId);

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException(
                        "Team not found with id: " + teamId));

        if (!team.getOrganization().getId().equals(organizationId)) {
            throw new TeamNotFoundException(
                    "Team not found  with id: " + teamId);
        }

        project.getTeams().add(team);

        projectRepository.save(project);
    }

    @Transactional
    public void removeTeamFromProject(
            Long organizationId,
            Long projectId,
            Long teamId) {

        Project project = getProjectEntity(organizationId, projectId);

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException(
                        "Team not found with id: " + teamId));

        if (!team.getOrganization().getId().equals(organizationId)) {
            throw new TeamNotFoundException(
                    "Team not found in organization with id: " + organizationId);
        }

        project.getTeams().remove(team);

        projectRepository.save(project);
    }

    public Set<TeamResponse> getProjectTeams(
            Long organizationId,
            Long projectId) {

        Project project = getProjectEntity(organizationId, projectId);

        return project.getTeams()
                .stream()
                .map(team -> new TeamResponse(
                        team.getId(),
                        team.getName(),
                        team.getSlug()))
                .collect(Collectors.toSet());
    }

    private Project getProjectEntity(Long organizationId, Long projectId) {

        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException(
                        "Organization not found with id: " + organizationId));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(
                        "Project not found with id: " + projectId));

        if (!project.getOrganization().getId().equals(organization.getId())) {
            throw new ProjectNotFoundException(
                    "Project not found in organization with id: " + organizationId);
        }

        return project;
    }
}