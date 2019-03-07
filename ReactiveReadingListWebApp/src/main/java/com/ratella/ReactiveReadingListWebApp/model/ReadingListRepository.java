package com.ratella.ReactiveReadingListWebApp.model;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ReadingListRepository extends ReactiveMongoRepository<Book, String> {

    public Flux<Book> findByReader(String reader);

}
