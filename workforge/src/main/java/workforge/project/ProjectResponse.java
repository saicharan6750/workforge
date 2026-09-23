package workforge.project;

public class ProjectResponse {

    private Long id;
    private String name;
    private String slug;

    public ProjectResponse(Long id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
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
}