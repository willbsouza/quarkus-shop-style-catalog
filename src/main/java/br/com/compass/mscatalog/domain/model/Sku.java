package br.com.compass.mscatalog.domain.model;

import br.com.compass.mscatalog.resources.dto.SkuFormDto;
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
public class Sku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    @OneToMany(mappedBy = "sku", cascade = CascadeType.ALL)
    @NotNull @NotEmpty
    private List<Media> images = new ArrayList<>();

    public Sku(SkuFormDto skuFormDto, Product product) {
        this.price = skuFormDto.getPrice();
        this.quantity = skuFormDto.getQuantity();
        this.color = skuFormDto.getColor();
        this.size = skuFormDto.getSize();
        this.height = skuFormDto.getHeight();
        this.width = skuFormDto.getWidth();
        this.product = product;
    }

    public void addImages(Media media) {
        this.images.add(media);
    }
}
