package org.noobieback.gallery.backend.controller;

import org.noobieback.gallery.backend.dto.OrderDTO;
import org.noobieback.gallery.backend.entity.Cart;
import org.noobieback.gallery.backend.entity.Item;
import org.noobieback.gallery.backend.entity.Order;
import org.noobieback.gallery.backend.repository.CartRepository;
import org.noobieback.gallery.backend.repository.ItemRepository;
import org.noobieback.gallery.backend.repository.OrderRepository;
import org.noobieback.gallery.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    JwtService jwtService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CartRepository cartRepository;

    @GetMapping("/api/orders")
    public ResponseEntity getOrder(
            @CookieValue(value="token", required = false) String token
    ) {
        if(!jwtService.isValid(token)) {
            throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);
        List<Order> orders = orderRepository.findByMemberIdOrderByIdDesc(memberId);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/api/orders")
    public ResponseEntity pushOrder(
            @RequestBody OrderDTO dto,
            @CookieValue(value="token", required = false) String token
    ) {
        if(!jwtService.isValid(token)) {
            throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Order newOrder= new Order();
        int memberId = jwtService.getId(token);
        newOrder.setMemberId(memberId);
        newOrder.setName(dto.getName());
        newOrder.setAddress(dto.getAddress());
        newOrder.setPayment(dto.getPayment());
        newOrder.setCardNumber(dto.getCardNumber());
        newOrder.setItems(dto.getItems());

        orderRepository.save(newOrder);

        cartRepository.deleteByMemberId(memberId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
