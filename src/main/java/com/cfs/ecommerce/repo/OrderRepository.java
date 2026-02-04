package com.cfs.ecommerce.repo;

import com.cfs.ecommerce.model.Orders;
import com.cfs.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {

    @Query("SELECT o from Orders o JOIN FETCH o.user")
    List<Orders> findAllOrdersWithUsers();

    List<Orders> findByUser(User user);
}
