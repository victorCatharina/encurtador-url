package br.com.victorCatharina.encurtador_url.repository;

import br.com.victorCatharina.encurtador_url.entities.ClickEventEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClickEventRepository extends MongoRepository<ClickEventEntity, String> {
    List<ClickEventEntity> findByShortUrlId(String shortUrlId);

    long countByShortUrlId(String shortUrlId);
}
