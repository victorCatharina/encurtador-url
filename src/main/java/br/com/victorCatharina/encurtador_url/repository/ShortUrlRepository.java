package br.com.victorCatharina.encurtador_url.repository;

import br.com.victorCatharina.encurtador_url.entities.ShortUrlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShortUrlRepository extends MongoRepository<ShortUrlEntity, String> {
}
