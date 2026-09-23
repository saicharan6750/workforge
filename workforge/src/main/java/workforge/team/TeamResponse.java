package workforge.team;

public class TeamResponse {

    private Long id;
    private String name;
    private String slug;

    public TeamResponse(Long id,String name,String slug) {
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