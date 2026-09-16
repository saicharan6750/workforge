package workforge.organization;

import org.springframework.stereotype.Service;

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
}
