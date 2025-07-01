package br.com.victorCatharina.encurtador_url.repository;

import br.com.victorCatharina.encurtador_url.entities.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginRepository extends MongoRepository<UserEntity, String> {
}
