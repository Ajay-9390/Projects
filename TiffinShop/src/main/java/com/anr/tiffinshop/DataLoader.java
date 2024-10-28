package com.anr.tiffinshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.anr.tiffinshop.model.Item;
import com.anr.tiffinshop.repository.ItemRepository;

@Component
public class DataLoader implements CommandLineRunner {

	@Autowired
	private ItemRepository itemRepository;

	@Override
	public void run(String... args) throws Exception {

		Item masaladosa = new Item();
		masaladosa.setName("MASALA DOSA");
		masaladosa.setCost(40.0);
		itemRepository.save(masaladosa);

		Item oniondosa = new Item();
		oniondosa.setName("ONION DOSA");
		oniondosa.setCost(40.0);
		itemRepository.save(oniondosa);

		Item idly = new Item();
		idly.setName("IDLY");
		idly.setCost(35.0);
		itemRepository.save(idly);

		Item vada = new Item();
		vada.setName("VADA");
		vada.setCost(35.0);
		itemRepository.save(vada);

		Item puri = new Item();
		puri.setName("PURI");
		puri.setCost(40.0);
		itemRepository.save(puri);

		Item bonda = new Item();
		bonda.setName("BONDA");
		bonda.setCost(35.0);
		itemRepository.save(bonda);

	}

}
