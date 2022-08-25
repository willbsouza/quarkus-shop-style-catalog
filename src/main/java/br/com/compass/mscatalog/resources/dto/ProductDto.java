package br.com.compass.mscatalog.resources.dto;

import br.com.compass.mscatalog.domain.model.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private String brand;
    private String material;
    private Boolean active;
    private List<SkuDto> skus;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.brand = product.getBrand();
        this.material = product.getMaterial();
        this.active = product.getActive();
        this.skus = product.getSkus().stream().map(SkuDto::new).collect(Collectors.toList());
    }
}
