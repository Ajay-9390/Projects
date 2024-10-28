package com.anr.tiffinshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anr.tiffinshop.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
