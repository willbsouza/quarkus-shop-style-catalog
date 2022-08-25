package br.com.compass.mscatalog.resources.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SkuFormDto {
    @NotNull
    private Double price;

    @NotNull
    private Integer quantity;

    @NotNull @NotEmpty
    private String color;

    @NotNull @NotEmpty
    private String size;

    @NotNull
    private Integer height;

    @NotNull
    private Integer width;

    @NotNull
    private Long productId;

    @NotNull @NotEmpty
    private List<String> images;
}
