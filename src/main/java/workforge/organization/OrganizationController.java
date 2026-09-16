package workforge.organization;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrganizationController {
    private final OrganizationService organizationService;
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }
    @PostMapping("/api/organizations")
    public Organization createOrganization(@RequestBody CreateOrganizationRequest request) {
        return organizationService.createOrganization(request.getName(),request.getSlug());
    }
}