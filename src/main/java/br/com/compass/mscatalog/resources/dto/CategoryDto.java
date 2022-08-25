package br.com.compass.mscatalog.resources.dto;

import br.com.compass.mscatalog.domain.model.Category;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CategoryDto implements Serializable {

    private Long id;
    private String name;
    private Boolean active;
    private List<CategoryDto> children = new ArrayList<>();

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.active = category.getActive();
        this.children = category.getChildren().stream().map(CategoryDto::new).collect(Collectors.toList());
    }
}
