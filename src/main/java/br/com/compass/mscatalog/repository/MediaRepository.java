package br.com.compass.mscatalog.repository;

import br.com.compass.mscatalog.domain.model.Media;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MediaRepository implements PanacheRepository<Media> {
}
