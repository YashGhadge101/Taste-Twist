package com.tasteandtwist.repository;

import com.tasteandtwist.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    // You can add custom queries later if needed
    // Example:
    // List<Menu> findByRestaurantId(Long restaurantId);
}
