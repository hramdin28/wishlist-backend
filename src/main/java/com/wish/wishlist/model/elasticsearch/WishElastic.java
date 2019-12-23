package com.wish.wishlist.model.elasticsearch;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "wish")
@Document(indexName = "mywishlist", type = "wish")
public class WishElastic {

	@Id
	private String id;
	private String name;
	private String description;
	private Date createdDate;

}
