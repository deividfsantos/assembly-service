package com.deividsantos.assembly.repository;

import com.deividsantos.assembly.repository.entity.VoteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends CrudRepository<VoteEntity, Integer> {

}
