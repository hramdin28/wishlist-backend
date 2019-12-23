package com.wish.wishlist.service;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.wish.wishlist.WishlistApplication;
import com.wish.wishlist.dto.WishDto;
import com.wish.wishlist.model.elasticsearch.WishElastic;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WishlistApplication.class)
public class WishServiceTest {
    @Autowired
    private WishService wishService;

    @Autowired
    private ElasticsearchTemplate esTemplate;

    @Before
    public void before() {
        esTemplate.deleteIndex(WishElastic.class);
        esTemplate.createIndex(WishElastic.class);
        esTemplate.putMapping(WishElastic.class);
        esTemplate.refresh(WishElastic.class);
    }

    @Test
    @Transactional
    public void testAddWish() {

        WishDto wishDto = new WishDto();
        wishDto.setCreatedDate(LocalDateTime.now());
        wishDto.setName("testUnitName");
        wishDto.setDescription("descUnit");

        wishService.addWish(wishDto);

        List<WishDto> wishElastic = wishService.findAllWishByPageElastic(0);
        List<WishDto> wishMysql = wishService.findAllWishByPageMysql(0);

        assertThat(wishElastic.isEmpty(), is(false));
        assertThat(wishMysql.isEmpty(), is(false));

        assertThat(wishElastic, hasItem(Matchers.<WishDto>hasProperty("name", is("testUnitName"))));
        assertThat(wishMysql, hasItem(Matchers.<WishDto>hasProperty("name", is("testUnitName"))));

    }

    @Test
    @Transactional
    public void testDeleteWish() {

        WishDto wishDto = new WishDto();
        wishDto.setCreatedDate(LocalDateTime.now());
        wishDto.setName("testUnitName");
        wishDto.setDescription("descUnit");

        wishService.addWish(wishDto);

        List<WishDto> wishElastic = wishService.findAllWishByPageElastic(0);
        List<WishDto> wishMysql = wishService.findAllWishByPageMysql(0);

        assertThat(wishElastic.isEmpty(), is(false));
        assertThat(wishMysql.isEmpty(), is(false));

        assertThat(wishElastic, hasItem(Matchers.<WishDto>hasProperty("name", is("testUnitName"))));
        assertThat(wishMysql, hasItem(Matchers.<WishDto>hasProperty("name", is("testUnitName"))));

        wishService.deleteWishById(Long.parseLong(wishMysql.get(0).getId()));

        List<WishDto> wishElasticNew = wishService.findAllWishByPageElastic(0);
        List<WishDto> wishMysqlNew = wishService.findAllWishByPageMysql(0);

        assertThat(wishElasticNew, (hasItem(Matchers.<WishDto>hasProperty("name", not(is("testUnitName"))))));
        assertThat(wishMysqlNew, (hasItem(Matchers.<WishDto>hasProperty("name", not(is("testUnitName"))))));

    }
}
