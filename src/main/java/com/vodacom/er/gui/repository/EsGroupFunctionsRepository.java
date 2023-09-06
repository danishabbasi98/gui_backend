package com.vodacom.er.gui.repository;


import com.vodacom.er.gui.entity.Group_Functions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsGroupFunctionsRepository extends JpaRepository<Group_Functions, Integer> {

}

