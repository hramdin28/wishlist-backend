package com.wish.wishlist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wish.wishlist.dto.WishDto;
import com.wish.wishlist.service.WishService;

@RestController
@RequestMapping(value = "/admin")
public class WishAdminController {

	@Autowired
	private WishService wishService;

	@PostMapping(value = "/add")
	public String add(@RequestBody WishDto wishDto) {
		wishService.addWish(wishDto);

		return "Records saved in the db.";
	}

	@DeleteMapping(value = "/delete")
	public String delete(Long id) {
		wishService.deleteWishById(id);
		return "Wish has been deleted";
	}

	@GetMapping(value = "/fetchAllByPageNumber")
	public List<WishDto> fetchAllByPageNumber(@RequestParam Integer pageNumber) {
		return wishService.findAllWishByPageMysql(pageNumber);
	}

}
