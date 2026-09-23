package workforge.team;

import org.springframework.stereotype.Service;
import workforge.exception.DuplicateTeamSlugException;
import workforge.exception.OrganizationNotFoundException;
import workforge.exception.TeamNotFoundException;
import workforge.organization.Organization;
import workforge.organization.OrganizationRepository;
import java.util.stream.Collectors;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final OrganizationRepository organizationRepository;

    public TeamService(TeamRepository teamRepository, OrganizationRepository organizationRepository) {
        this.teamRepository = teamRepository;
        this.organizationRepository = organizationRepository;
    }

    public Team createTeam(Long organizationId, String name, String slug) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException("Organization not found with id: " + organizationId));
        name = name.trim();
        slug = slug.trim().toLowerCase();
        if(teamRepository.existsByOrganizationIdAndSlug(organizationId,slug)) {
            throw new DuplicateTeamSlugException("Team slug already exists in this organization");
        }
        Team team = new Team(name, slug, organization);
        return teamRepository.save(team);
    }

        public TeamResponse getTeamById(Long organizationId, Long teamId) {

            Organization organization = organizationRepository.findById(organizationId)
                    .orElseThrow(() -> new OrganizationNotFoundException(
                            "Organization not found with id: " + organizationId));

            Team team = teamRepository.findById(teamId)
                    .orElseThrow(() -> new TeamNotFoundException(
                            "Team not found with id: " + teamId));

            if (!team.getOrganization().getId().equals(organization.getId())) {
                throw new TeamNotFoundException(
                        "Team not found in organization with id: " + organizationId);
            }

            return new TeamResponse(
                    team.getId(),
                    team.getName(),
                    team.getSlug());
        }


    public List<TeamResponse> getTeamsByOrganization(Long organizationId) {

        organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException(
                        "Organization not found with id: " + organizationId));

        return teamRepository.findByOrganizationId(organizationId)
                .stream()
                .map(team -> new TeamResponse(
                        team.getId(),
                        team.getName(),
                        team.getSlug()))
                .collect(Collectors.toList());
    }

    public Team updateTeam(Long organizationId, Long teamId, String name, String slug) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException("Organization not found with id: " + organizationId));
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("Team not found with id: " + teamId));
        if (!team.getOrganization().getId().equals(organization.getId())) {
            throw new TeamNotFoundException("Team not found in organization with id: " + organizationId);
        }
        name = name.trim();
        slug = slug.trim().toLowerCase();
        if(teamRepository.existsByOrganizationIdAndSlugAndIdNot(organizationId,slug,teamId)) {
            throw new DuplicateTeamSlugException("Team slug already exists in this organization");
        }
        team.setName(name);
        team.setSlug(slug);
        return teamRepository.save(team);
    }

    public void deleteTeam(Long organizationId,Long teamId) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException("Organization not found with id: " + organizationId));
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("Team not found with id: " + teamId));
        if(!team.getOrganization().getId().equals(organization.getId())) {
            throw new TeamNotFoundException("Team not found in organization with id: " + organizationId);
        }
        teamRepository.delete(team);
    }


}