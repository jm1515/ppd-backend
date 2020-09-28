package com.ppd.mubler.model.repository;

import com.ppd.mubler.model.entity.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ReviewRepository extends CrudRepository<Review, Integer> {
    List<Review> findByIdWriter(long idWriter);
    List<Review> findByIdConcerned(long idConcerned);
    Review findById(long id);
}
