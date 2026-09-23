package workforge.team;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import workforge.organization.Organization;
import workforge.project.Project;

@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String slug;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @ManyToMany(mappedBy = "teams")
    private Set<Project> projects = new HashSet<>();

    protected Team() {
    }

    public Team(String name, String slug, Organization organization) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}