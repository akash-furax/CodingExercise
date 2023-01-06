package com.scry.analystics.test.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scry.analystics.test.model.AddressDTO;
import com.scry.analystics.test.model.LocationDTO;
import com.scry.analystics.test.model.ShopDTO;
import com.scry.analystics.test.service.ShopService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {ShopController.class})
@ExtendWith(SpringExtension.class)
class ShopControllerTest {
    @Autowired
    private ShopController shopController;

    @MockBean
    private ShopService shopService;

    /**
     * Method under test: {@link ShopController#getAllShops()}
     */
    @Test
    void testGetAllShops() throws Exception {
        when(shopService.getAllShops()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/shop");
        MockMvcBuilders.standaloneSetup(shopController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link ShopController#getAllShops()}
     */
    @Test
    void testGetAllShops2() throws Exception {
        when(shopService.getAllShops()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/shop");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(shopController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link ShopController#updateShop(ShopDTO)}
     */
    @Test
    void testUpdateShop() throws Exception {

        when(shopService.getAllShops()).thenReturn(new ArrayList<>());

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(123L);
        addressDTO.setLocation(new LocationDTO());
        addressDTO.setNumber(10);
        addressDTO.setPostCode("OX1 1PT");

        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setShopAddress(addressDTO);
        shopDTO.setShopName("Shop Name");
        String content = (new ObjectMapper()).writeValueAsString(shopDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/shop")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(shopController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

