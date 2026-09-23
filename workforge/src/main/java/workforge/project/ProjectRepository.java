package workforge.project;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByOrganizationId(Long organizationId);

    boolean existsByOrganizationIdAndSlug(Long organizationId, String slug);

    boolean existsByOrganizationIdAndSlugAndIdNot(Long organizationId, String slug, Long id);
}