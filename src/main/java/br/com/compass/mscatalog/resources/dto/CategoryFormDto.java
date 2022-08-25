package br.com.compass.mscatalog.resources.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class CategoryFormDto {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private Boolean active;

    private Long parentId;
}
