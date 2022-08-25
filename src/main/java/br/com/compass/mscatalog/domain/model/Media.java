package br.com.compass.mscatalog.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String imagemUrl;

    @ManyToOne
    @JsonIgnore
    private Sku sku;

    public Media(@NotNull @NotEmpty String imagemUrl, Sku sku) {
        this.imagemUrl = imagemUrl;
        this.sku = sku;
    }
}
