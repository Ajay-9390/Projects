package com.anr.tiffinshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anr.tiffinshop.model.Item;
import com.anr.tiffinshop.repository.ItemRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

	public List<Item> getAllItems() {
		return itemRepository.findAll();
	}

	public Item saveItem(Item item) {
		return itemRepository.save(item);
	}

}
