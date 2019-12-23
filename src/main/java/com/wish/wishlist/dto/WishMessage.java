package com.wish.wishlist.dto;

import java.io.Serializable;

import com.wish.wishlist.enums.OperationType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
@Setter
public class WishMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	OperationType type;
	WishDto wishDto;

}
