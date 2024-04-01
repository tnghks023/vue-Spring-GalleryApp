package org.noobieback.gallery.backend.controller;

import org.noobieback.gallery.backend.entity.Item;
import org.noobieback.gallery.backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class itemController {

    ItemRepository itemRepository;

    @Autowired
    public itemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/api/items")
    public List<Item> getItems() {
        List<Item> items = itemRepository.findAll();

        return items;
    }
}
