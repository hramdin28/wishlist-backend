package com.wish.wishlist.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.wish.wishlist.dto.WishDto;
import com.wish.wishlist.service.WishService;

@RunWith(MockitoJUnitRunner.class)
public class WishPublicControllerTest {

    @Mock
    private WishService wishService;

    @InjectMocks
    private WishPublicController wishPublicController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(wishPublicController).build();
    }

    @Test
    public void fetchAllByPageNumberTest() throws Exception {

        WishDto wishDto = new WishDto();
        wishDto.setCreatedDate(LocalDateTime.now());
        wishDto.setDescription("test desc");
        wishDto.setName("testName");
        wishDto.setId("1");

        when(wishService.findAllWishByPageElastic(0)).thenReturn(Arrays.asList(wishDto));

        mockMvc.perform(get("/public/fetchAllByPageNumber").param("pageNumber", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].name", is("testName")));

        verify(wishService, times(1)).findAllWishByPageElastic(0);
        verifyNoMoreInteractions(wishService);
    }

}
