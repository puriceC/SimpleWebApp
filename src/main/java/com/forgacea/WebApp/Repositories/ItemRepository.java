package com.forgacea.WebApp.Repositories;

import com.forgacea.WebApp.Models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
