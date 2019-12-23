package com.wish.wishlist.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wish.wishlist.config.WebSocketConfig;
import com.wish.wishlist.dto.WishDto;
import com.wish.wishlist.dto.WishMessage;
import com.wish.wishlist.enums.OperationType;
import com.wish.wishlist.mapper.MapperList;
import com.wish.wishlist.model.elasticsearch.WishElastic;
import com.wish.wishlist.model.mysql.WishMysql;
import com.wish.wishlist.repository.elasticsearch.WishElasticRepository;
import com.wish.wishlist.repository.mysql.WishMysqlRepository;
import com.wish.wishlist.service.WishService;

@Service
@Transactional(readOnly = true)
public class WishServiceImpl implements WishService {

	private static final int PAGE_SIZE = 10;

	private static final String SORT_BY = "createdDate";

	@Autowired
	private WishElasticRepository wishElasticRepository;

	@Autowired
	private WishMysqlRepository wishMysqlRepository;

	@Autowired
	private SimpMessagingTemplate template;

	/**
	 * Save to elastic search db and send message
	 * 
	 * @param wishMysql object
	 */
	private void saveToElasticSearch(WishMysql wishMysql) {
		WishElastic wishElastic = wishElasticRepository.save(MapperList.WISH_MAPPER.mySqlToWishElastic(wishMysql));

		Optional.ofNullable(wishElastic).ifPresent(wish -> {

			WishDto wishDto = MapperList.WISH_MAPPER.elasticToWishDto(wish);

			WishMessage message = new WishMessage(OperationType.ADD, wishDto);
			template.convertAndSend(WebSocketConfig.TOPIC, message);

		});
	}

	/**
	 * Delete from elastic search db by id and send message
	 * 
	 * @param id entity id
	 */
	private void deleteFromElasticSearchById(String id) {
		wishElasticRepository.deleteById(id);

		WishDto wishDto = new WishDto();
		wishDto.setId(id);

		WishMessage message = new WishMessage(OperationType.DELETE, wishDto);
		template.convertAndSend(WebSocketConfig.TOPIC, message);
	}

	@Override
	@Transactional(readOnly = false)
	public void addWish(WishDto wishDto) {
		wishDto.setCreatedDate(LocalDateTime.now());
		WishMysql wishMysql = wishMysqlRepository.save(MapperList.WISH_MAPPER.toWishMysql(wishDto));

		Optional.ofNullable(wishMysql).ifPresent(this::saveToElasticSearch);

	}

	@Override
	public List<WishDto> findAllWishByPageMysql(Integer pageNumber) {
		Pageable page = PageRequest.of(pageNumber, PAGE_SIZE, Sort.by(SORT_BY).descending());

		return MapperList.WISH_MAPPER.mysqlToListDto(IterableUtils.toList(wishMysqlRepository.findAll(page)));
	}

	@Override
	public List<WishDto> findAllWishByPageElastic(Integer pageNumber) {
		Pageable page = PageRequest.of(pageNumber, PAGE_SIZE, Sort.Direction.DESC, SORT_BY);

		return MapperList.WISH_MAPPER.elasticToListDto(IterableUtils.toList(wishElasticRepository.findAll(page)));
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteWishById(Long id) {
		wishMysqlRepository.deleteById(id);
		deleteFromElasticSearchById(id.toString());
	}

}
