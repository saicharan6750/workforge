package workforge.organization;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateOrganizationRequest {

    @NotBlank
    @Size(max=150)
    private String name;

    @NotBlank
    @Size(max=100)
    private String slug;

    public String getName() {
        return name;
    }
    public String getSlug() {
        return slug;
    }
}