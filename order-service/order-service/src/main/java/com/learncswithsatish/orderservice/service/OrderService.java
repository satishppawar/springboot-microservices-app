package com.learncswithsatish.orderservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learncswithsatish.orderservice.dto.OrderLineItemsDTO;
import com.learncswithsatish.orderservice.dto.OrderRequest;
import com.learncswithsatish.orderservice.model.Order;
import com.learncswithsatish.orderservice.model.OrderLineItems;
import com.learncswithsatish.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
	
	private final OrderRepository orderRepository;
	
	public void placeOrder(OrderRequest orderRequest) {
		Order order=new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		List<OrderLineItems> orderLineItems=orderRequest.getOrderLineItemsList().stream().map(this::mapToOrder).toList();
		order.setOrderLineItems(orderLineItems);
		orderRepository.save(order);
		
	}

	private OrderLineItems mapToOrder(OrderLineItemsDTO o) {
		OrderLineItems orderLineItems=new OrderLineItems();
		orderLineItems.setPrice(o.getPrice());
		orderLineItems.setQuantity(o.getQuantity());
		orderLineItems.setCode(o.getCode());
		return orderLineItems;
	}

}
