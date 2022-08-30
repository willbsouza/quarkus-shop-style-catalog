package br.com.compass.mscatalog.rabbitmq;

import br.com.compass.mscatalog.domain.model.Sku;
import br.com.compass.mscatalog.rabbitmq.model.SkuOrder;
import br.com.compass.mscatalog.repository.SkuRepository;
import io.vertx.core.json.JsonObject;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class RabbitMQConsumer {

    private SkuRepository skuRepository;

    @Inject
    public RabbitMQConsumer(SkuRepository skuRepository){
        this.skuRepository = skuRepository;
    }

    @Incoming("order-sku")
    @Transactional
    public void processMessage(JsonObject msg){
        SkuOrder skuOrder = msg.mapTo(SkuOrder.class);
        for(Sku sku : skuOrder.getSkus()) {
            updateOrderSku(sku.getId(), sku.getQuantity());
        }
    }

    private void updateOrderSku(Long id, Integer quantity) {
        Sku sku = skuRepository.findById(id);
        sku.setQuantity(sku.getQuantity() - quantity);
    }
}
