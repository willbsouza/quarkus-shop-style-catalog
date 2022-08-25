package br.com.compass.mscatalog.domain.model;

import br.com.compass.mscatalog.resources.dto.CategoryFormDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> children = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<Product> products;

    public Category(CategoryFormDto categoryDto, Category parentCategory) {
        this.name = categoryDto.getName();
        this.active = categoryDto.getActive();
        this.parent = parentCategory;
    }

    public Category(CategoryFormDto categoryDto) {
        this.name = categoryDto.getName();
        this.active = categoryDto.getActive();
    }

    public void addChildren(Category category) {
        this.children.add(category);
    }
}
