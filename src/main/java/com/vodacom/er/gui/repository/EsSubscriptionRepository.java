package com.vodacom.er.gui.repository;


import com.vodacom.er.gui.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EsSubscriptionRepository extends JpaRepository<Subscription, Integer> {

    @Query("SELECT s FROM Subscription s")
    List<Subscription> findAllWithLimit(int limit);

    default List<Subscription> findEntityInPage(int page, int pageSize){
        int offSet = page * pageSize;
        return findEntityInRange(offSet, pageSize);
    }
    @Query("SELECT s FROM Subscription s ORDER BY s.seq")
    List<Subscription> findEntityInRange(int offSet, int limit);
}
