package com.vodacom.er.gui.repository;

import com.vodacom.er.gui.entity.Session_Actions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsSessionActionsRepository extends JpaRepository<Session_Actions, Integer> {

}
