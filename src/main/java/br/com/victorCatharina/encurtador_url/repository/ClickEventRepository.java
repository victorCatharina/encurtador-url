package br.com.victorCatharina.encurtador_url.repository;

import br.com.victorCatharina.encurtador_url.entities.ClickEventEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ClickEventRepository extends MongoRepository<ClickEventEntity, String> {
    List<ClickEventEntity> findByShortUrlId(String shortUrlId);

    long countByShortUrlId(String shortUrlId);
}
