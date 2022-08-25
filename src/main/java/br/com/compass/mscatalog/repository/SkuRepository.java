package br.com.compass.mscatalog.repository;

import br.com.compass.mscatalog.domain.model.Sku;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SkuRepository implements PanacheRepository<Sku> {
}
