package com.vodacom.er.gui.repository;

import com.vodacom.er.gui.entity.User_Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsUserGroupRepository extends JpaRepository<User_Groups, Integer> {

}
