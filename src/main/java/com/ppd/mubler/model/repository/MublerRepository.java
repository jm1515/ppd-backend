package com.ppd.mubler.model.repository;

import com.ppd.mubler.model.entity.Mubler;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MublerRepository extends CrudRepository<Mubler, Integer>{
    List<Mubler> findByEmail(String email);
    Mubler findById(long id);
}
