package com.wish.wishlist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wish.wishlist.config.WebSocketConfig;
import com.wish.wishlist.dto.WishDto;
import com.wish.wishlist.dto.WishMessage;
import com.wish.wishlist.service.WishService;

@RestController
@RequestMapping(value = "/public")
public class WishPublicController {

	@Autowired
	private WishService wishService;

	@MessageMapping("/")
	@SendTo(WebSocketConfig.TOPIC)
	public WishMessage greeting(WishMessage message) {
		return message;
	}

	@GetMapping(value = "/fetchAllByPageNumber")
	public List<WishDto> fetchAllByPageNumber(@RequestParam Integer pageNumber) {
		return wishService.findAllWishByPageElastic(pageNumber);
	}
}
