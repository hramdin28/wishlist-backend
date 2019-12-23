package com.wish.wishlist.mapper;

public final class MapperList {
	private MapperList() {
	}

	public static final WishDtoMapper WISH_MAPPER = org.mapstruct.factory.Mappers.getMapper(WishDtoMapper.class);
}
