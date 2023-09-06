package com.vodacom.er.gui.repository;


import com.vodacom.er.gui.entity.Functions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EsFunctionsRepository extends JpaRepository<Functions, Integer> {
  @Query("SELECT s FROM Functions s WHERE s.name =:n")
  public Functions findByFunctionName(@Param("n") String name);
}
