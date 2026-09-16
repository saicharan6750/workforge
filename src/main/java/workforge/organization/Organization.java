package workforge.organization;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "organizations")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false , length = 150)
    private String name;

    @Column(nullable = false,unique = true , length = 100)
    private String slug;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected Organization() {
        this.name = name;
        this.slug = slug;
        this.createdAt = LocalDateTime.now();
    }

    public Organization(String name,String slug) {
        this.name = name;
        this.slug = slug;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}