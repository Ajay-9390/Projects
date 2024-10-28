package com.anr.tiffinshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anr.tiffinshop.model.Item;
import com.anr.tiffinshop.model.MyOrder;
import com.anr.tiffinshop.repository.ItemRepository;
import com.anr.tiffinshop.repository.MyOrderRepository;
import com.anr.tiffinshop.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;

	@Autowired
	private MyOrderRepository myorderRepository;

	@Autowired
	private ItemRepository itemRepository;

	@GetMapping("/items")
	public String getItems(Model model) {
		model.addAttribute("items", itemService.getAllItems());
		return "items";
	}

	@PostMapping("/order")
	public String placeOrder(@RequestParam Long itemId, @RequestParam int quantity, Model model) {
		Item item = itemRepository.findById(itemId).orElse(null);
		if (item != null) {
			double totalPrice = item.getCost() * quantity;
			model.addAttribute("item", item);
			model.addAttribute("quantity", quantity);
			model.addAttribute("totalPrice", totalPrice);
			return "order-confirmation";
		}
		return "redirect:/items";
	}

	@PostMapping("/confirmOrder")
	public String confirmOrder(@RequestParam Long itemId, @RequestParam int quantity) {
		MyOrder order = new MyOrder();
		order.setItemId(itemId);
		order.setQuantity(quantity);
		myorderRepository.save(order);
		return "order-confirmed";
	}

}
