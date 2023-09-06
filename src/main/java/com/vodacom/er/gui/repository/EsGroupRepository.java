package com.vodacom.er.gui.repository;

import com.vodacom.er.gui.entity.Groups;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


@Repository
public interface EsGroupRepository extends JpaRepository<Groups, Integer> {

  @Query("SELECT s FROM Groups s WHERE s.name =:n")
  public Groups findByGroupName(@Param("n") String name);
}
