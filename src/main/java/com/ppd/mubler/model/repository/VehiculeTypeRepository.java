package com.ppd.mubler.model.repository;

import com.ppd.mubler.model.entity.VehiculeType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface VehiculeTypeRepository extends CrudRepository<VehiculeType, Integer> {
}
