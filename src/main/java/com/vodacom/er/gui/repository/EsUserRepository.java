package com.vodacom.er.gui.repository;


import com.vodacom.er.gui.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EsUserRepository extends JpaRepository<Users, Integer> {
    @Query("SELECT s FROM Users s WHERE s.user_name =:n")
    public Users findByUserName(@Param("n") String user_name);

    @Query("SELECT s FROM Users s")
    List<Users> findAllWithLimit(int limit);


}
