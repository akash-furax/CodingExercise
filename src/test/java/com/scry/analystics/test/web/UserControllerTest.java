package com.scry.analystics.test.web;

import com.scry.analystics.test.entity.Shop;
import com.scry.analystics.test.service.UserService;
import com.scry.analystics.test.utils.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link UserController#getNearestShop(Double, Double)}
     */
    @Test
    void testGetNearestShop() throws Exception {
        Shop shop1 = TestUtil.createShop("10");
        when(userService.findNearestLocationForUser((Double) any(), (Double) any())).thenReturn(shop1);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/user");
        MockHttpServletRequestBuilder paramResult = getResult.param("lat", String.valueOf(10.0d));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("lon", String.valueOf(10.0d));
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"shopName\":\"10\",\"shopAddress\":{\"id\":10,\"location\":{\"latitude\":10.0,\"longitude\":10.0},\"number"
                                        + "\":10,\"postCode\":\"10\"}}"));
    }
}

