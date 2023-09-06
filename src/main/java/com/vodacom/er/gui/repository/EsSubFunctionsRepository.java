package com.vodacom.er.gui.repository;

import com.vodacom.er.gui.entity.Sub_Functions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsSubFunctionsRepository extends JpaRepository<Sub_Functions, Integer> {

}
