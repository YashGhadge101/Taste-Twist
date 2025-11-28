package com.tasteandtwist.service;

import com.tasteandtwist.model.MenuItem;
import com.tasteandtwist.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MenuService {
    @Autowired private MenuItemRepository repo;
    public List<MenuItem> findAll() { return repo.findAll(); }
    public List<MenuItem> findByRestaurantId(Long rid) { return repo.findByRestaurantId(rid); }
    public MenuItem save(MenuItem m) { return repo.save(m); }
    public MenuItem findById(Long id) { return repo.findById(id).orElse(null); }
}
