package com.deividsantos.assembly.repository;

import com.deividsantos.assembly.repository.entity.SessionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends CrudRepository<SessionEntity, Integer> {
    List<SessionEntity> findAllByAgendaId(Integer agendaId);
}
