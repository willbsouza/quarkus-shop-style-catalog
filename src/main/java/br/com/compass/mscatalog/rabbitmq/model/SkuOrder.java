package br.com.compass.mscatalog.rabbitmq.model;

import br.com.compass.mscatalog.domain.model.Sku;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SkuOrder  implements Serializable {
    private static final long serialVersionUID = 1L;

    private String orderId;
    private List<Sku> skus;
}

