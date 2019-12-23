package com.wish.wishlist.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.wish.wishlist.model.elasticsearch.WishElastic;

@Repository
public interface WishElasticRepository extends ElasticsearchRepository<WishElastic, String> {

}
