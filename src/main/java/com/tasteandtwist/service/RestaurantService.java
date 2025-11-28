package com.tasteandtwist.service;

import com.tasteandtwist.model.Restaurant;
import com.tasteandtwist.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RestaurantService {
    @Autowired private RestaurantRepository repo;
    public List<Restaurant> findAll() { return repo.findAll(); }
    public Restaurant save(Restaurant r) { return repo.save(r); }
    public Restaurant findById(Long id) { return repo.findById(id).orElse(null); }
    public void deleteById(Long id) { repo.deleteById(id); }
}
