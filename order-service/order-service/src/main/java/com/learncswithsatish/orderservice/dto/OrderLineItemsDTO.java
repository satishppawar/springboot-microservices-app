package com.learncswithsatish.orderservice.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemsDTO {
	private Long id;
	private String code;
	private BigDecimal price;
	private Integer quantity;
}
