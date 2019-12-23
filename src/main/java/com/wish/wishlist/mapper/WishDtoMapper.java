package com.wish.wishlist.mapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.wish.wishlist.dto.WishDto;
import com.wish.wishlist.model.elasticsearch.WishElastic;
import com.wish.wishlist.model.mysql.WishMysql;

@Mapper
public interface WishDtoMapper {

	@Mapping(target = "id", source = "source.id", qualifiedByName = "stringToLong")
	WishMysql toWishMysql(WishDto source);

	@Mapping(target = "id", source = "source.id", qualifiedByName = "longToString")
	WishDto mysqltoWishDto(WishMysql source);

	@Mapping(target = "id", source = "source.id", qualifiedByName = "longToString")
	@Mapping(target = "createdDate", source = "source.createdDate", qualifiedByName = "localDateTimeToDate")
	WishElastic mySqlToWishElastic(WishMysql source);

	@Mapping(target = "createdDate", source = "source.createdDate", qualifiedByName = "dateTolocalDateTime")
	WishDto elasticToWishDto(WishElastic source);

	default List<WishDto> mysqlToListDto(List<WishMysql> wishList) {
		return wishList.stream().map(MapperList.WISH_MAPPER::mysqltoWishDto).collect(Collectors.toList());
	}

	default List<WishDto> elasticToListDto(List<WishElastic> wishList) {
		return wishList.stream().map(MapperList.WISH_MAPPER::elasticToWishDto).collect(Collectors.toList());
	}

	@Named("longToString")
	public static String longToString(Long id) {
		if (id != null) {
			return id.toString();
		}
		return null;
	}

	@Named("stringToLong")
	public static Long stringToLong(String id) {
		if (id != null) {
			return Long.valueOf(id);
		}
		return null;
	}

	@Named("@localDateTimeToDate")
	public static Date localDateTimeToDate(LocalDateTime date) {
		if (date != null) {
			return java.util.Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
		}
		return null;
	}

	@Named("@dateTolocalDateTime")
	public static LocalDateTime dateTolocalDateTime(Date date) {
		if (date != null) {
			return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		}
		return null;
	}

}
