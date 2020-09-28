package com.ppd.mubler.model.repository;

import com.ppd.mubler.model.entity.MublerRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MublerRequestRepository extends CrudRepository<MublerRequest, Integer> {

    MublerRequest findById(long id);
    List<MublerRequest> findByIdRequester(long idRequester);
    List<MublerRequest> findByIdAccepter (long idAccepter);
}
