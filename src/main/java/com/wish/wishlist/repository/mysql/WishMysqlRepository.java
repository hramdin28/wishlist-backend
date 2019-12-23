package com.wish.wishlist.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wish.wishlist.model.mysql.WishMysql;

@Repository
public interface WishMysqlRepository extends JpaRepository<WishMysql, Long> {

}
