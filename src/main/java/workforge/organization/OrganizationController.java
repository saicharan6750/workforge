package workforge.organization;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrganizationController {
    private final OrganizationService organizationService;
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }
    @PostMapping("/api/organizations")
    public Organization createOrganization(@Valid @RequestBody CreateOrganizationRequest request) {
        return organizationService.createOrganization(request.getName(),request.getSlug());
    }

    @GetMapping("/api/organizations/{id}")
    public ResponseEntity<Organization> getOrganizationById(@PathVariable Long id) {

        return ResponseEntity.ok(organizationService.getOrganizationById(id));

    }

    @GetMapping("/api/organizations")
    public List<Organization> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    @PutMapping("/api/organizations/{id}")
    public ResponseEntity<Organization> updateOrganization(@PathVariable Long id, @Valid @RequestBody UpdateOrganizationRequest request) {
        Organization organization = organizationService.updateOrganization(id,request.getName(),request.getSlug());
        if(organization == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(organization);
    }

    @DeleteMapping("/api/organizations/{id}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
        organizationService.deleteOrganization(id);
        return ResponseEntity.noContent().build();
    }
}