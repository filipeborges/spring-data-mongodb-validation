package br.com.filipeborges.springdatamongodbvalidation.repositories;

import br.com.filipeborges.springdatamongodbvalidation.entities.Data;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DataRepository extends ReactiveCrudRepository<Data, String> {
}
