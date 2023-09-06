package com.vodacom.er.gui.repository;

import com.vodacom.er.gui.entity.Sessions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EsSessionsRepository extends JpaRepository<Sessions, Integer> {

    @Query("SELECT es FROM Sessions es WHERE es.session_id = :sessionId")
    Sessions findBySessionId(String sessionId);
}