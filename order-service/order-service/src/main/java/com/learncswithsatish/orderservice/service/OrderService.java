package com.learncswithsatish.orderservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import com.learncswithsatish.orderservice.dto.InventoryResponse;
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

	// Add WebCleint
	private final WebClient client;

	public void placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsList().stream().map(this::mapToOrder)
				.toList();
		order.setOrderLineItems(orderLineItems);
		
		// Get the list of SKU code for oder items to pass in inventory request
		List<String> SkuCodes = order.getOrderLineItems().stream().map(OrderLineItems::getCode).toList();

		// Call inventory API synchronously service to check items are available and place order
		InventoryResponse[] inventoryResponseArray = client.get().
						uri("http://localhost:8082/api/inventory",uriBuilder->uriBuilder.queryParam("skuCode", SkuCodes).build()).
						retrieve().bodyToMono(InventoryResponse[].class).block();
		
		boolean allProductsInStock=Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);
		if (allProductsInStock) {
			orderRepository.save(order);
		} else {
			throw new IllegalArgumentException("Product is not in stock, please try later");
		}

	}

	private OrderLineItems mapToOrder(OrderLineItemsDTO o) {
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setPrice(o.getPrice());
		orderLineItems.setQuantity(o.getQuantity());
		orderLineItems.setCode(o.getSkuCode());
		return orderLineItems;
	}

}
