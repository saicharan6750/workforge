package workforge.organization;

import org.springframework.stereotype.Service;
import workforge.exception.DuplicateSlugException;
import workforge.exception.OrganizationNotFoundException;

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
            throw new DuplicateSlugException("Organization slug already exists");
        }
        Organization organization = new Organization(name,slug);
        return organizationRepository.save(organization);
    }

    public Organization getOrganizationById(Long id) {

        return organizationRepository.findById(id).orElseThrow(()->new OrganizationNotFoundException("Organization not found with id : "+id));

    }

    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    public Organization updateOrganization(Long id,String name,String slug) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new OrganizationNotFoundException("Organization not found with id: " + id));
        name = name.trim();
        slug = slug.trim().toLowerCase();
        if(organizationRepository.existsBySlugAndIdNot(slug,id)) {
            throw new DuplicateSlugException("Organization slug already exists");
        }
        organization.setName(name);
        organization.setSlug(slug);
        return organizationRepository.save(organization);
    }

    public void deleteOrganization(Long id) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new OrganizationNotFoundException("Organization not found with id: " + id));
        organizationRepository.delete(organization);
    }
}
