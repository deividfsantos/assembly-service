package com.deividsantos.assembly.repository;

import com.deividsantos.assembly.repository.entity.AgendaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaRepository extends CrudRepository<AgendaEntity, Integer> {

}
