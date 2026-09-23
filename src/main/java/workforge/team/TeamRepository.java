package workforge.team;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team,Long> {
    List<Team> findByOrganizationId(Long organizationId);
    boolean existsByOrganizationIdAndSlug(Long organizationId,String slug);
    boolean existsByOrganizationIdAndSlugAndIdNot(Long organizationId,String slug,Long id);
}