package com.tasteandtwist.repository;

import com.tasteandtwist.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> { }
