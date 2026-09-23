package workforge.project;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import workforge.organization.Organization;
import workforge.team.Team;

@Entity
@Table(
        name = "projects",
        uniqueConstraints = @UniqueConstraint(columnNames = {"organization_id", "slug"})
)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 100)
    private String slug;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    protected Project() {
    }

    @ManyToMany
    @JoinTable(
            name = "project_teams",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> teams = new HashSet<>();

    public Project(String name, String slug, Organization organization) {
        this.name = name;
        this.slug = slug;
        this.organization = organization;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public Organization getOrganization() {
        return organization;
    }

    public Set<Team> getTeams() {
        return teams;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}