package com.wish.wishlist.service;

import java.util.List;

import com.wish.wishlist.dto.WishDto;

public interface WishService {

	/**
	 * Add wish
	 * 
	 * @param wishDto wish dto
	 */
	void addWish(WishDto wishDto);

	/**
	 * Delete wish by id
	 * 
	 * @param id wish id
	 */
	void deleteWishById(Long id);

	/**
	 * Find all wish by Page in Msql db
	 * 
	 * @param pageNumber page number
	 * @return List of WishDto
	 */
	List<WishDto> findAllWishByPageMysql(Integer pageNumber);

	/**
	 * Find all wish by Page in ElasticSearch db
	 * 
	 * @param pageNumber page number
	 * @return List of WishDto
	 */
	List<WishDto> findAllWishByPageElastic(Integer pageNumber);

}
