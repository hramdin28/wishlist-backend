package com.wish.wishlist.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WishDto implements Serializable {

	private static final long serialVersionUID = -9007922353125294463L;

	private String id;
	private String name;
	private String description;
	private LocalDateTime createdDate;
}
