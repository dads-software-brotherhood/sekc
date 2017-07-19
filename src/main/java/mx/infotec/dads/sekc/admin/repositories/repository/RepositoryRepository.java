package mx.infotec.dads.sekc.admin.repositories.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Repository entity.
 * 
 * @author Daniel Cortes Pichardo
 */
@Repository
public interface RepositoryRepository
        extends MongoRepository<mx.infotec.dads.sekc.domain.Repository, String> {

}
