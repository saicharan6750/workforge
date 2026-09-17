package workforge.organization;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    public OrganizationService(OrganizationRepository organizationRepository){
        this.organizationRepository = organizationRepository;
    }

    public Organization createOrganization(String name,String slug) {
        name = name.trim();
        slug = slug.trim().toLowerCase();
        if(organizationRepository.existsBySlug(slug)) {
            throw new RuntimeException("Organization slug already exists");
        }
        Organization organization = new Organization(name,slug);
        return organizationRepository.save(organization);
    }

    public Organization getOrganizationById(Long id) {
        return organizationRepository.findById(id).orElse(null);
    }

    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    public Organization updateOrganization(Long id,String name,String slug) {
        Organization organization = organizationRepository.findById(id).orElse(null);
        if(organization == null) {
            return null;
        }
        organization.setName(name.trim());
        organization.setSlug(slug.trim().toLowerCase());
        return organizationRepository.save(organization);
    }

    public void deleteOrganization(Long id) {
        organizationRepository.deleteById(id);
    }
}
