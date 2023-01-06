package com.scry.analystics.test.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import springfox.documentation.spring.web.plugins.Docket;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ContextConfiguration(classes = {CustomConfiguration.class})
@ExtendWith(SpringExtension.class)
class CustomConfigurationTest {
    @Autowired
    private CustomConfiguration customConfiguration;


    /**
     * Method under test: {@link CustomConfiguration#api()}
     */
    @Test
    void testApi() {
        Docket actualApiResult = (new CustomConfiguration()).api();
        assertTrue(actualApiResult.isEnabled());
        assertEquals("default", actualApiResult.getGroupName());
    }

    @Test
    void testAddResourceHandlers() {
        CustomConfiguration customConfiguration = new CustomConfiguration();
        AnnotationConfigReactiveWebApplicationContext applicationContext = new AnnotationConfigReactiveWebApplicationContext();
        ResourceHandlerRegistry resourceHandlerRegistry = new ResourceHandlerRegistry(applicationContext,
                new MockServletContext());

        customConfiguration.addResourceHandlers(resourceHandlerRegistry);
        assertFalse(resourceHandlerRegistry.hasMappingForPattern("Path Pattern"));
    }

    /**
     * Method under test: {@link CustomConfiguration#addResourceHandlers(ResourceHandlerRegistry)}
     */
    @Test
    void testAddResourceHandlers2() {
        CustomConfiguration customConfiguration = new CustomConfiguration();
        AnnotationConfigApplicationContext applicationContext = mock(AnnotationConfigApplicationContext.class);
        ResourceHandlerRegistry resourceHandlerRegistry = new ResourceHandlerRegistry(applicationContext,
                new MockServletContext());

        customConfiguration.addResourceHandlers(resourceHandlerRegistry);
        assertFalse(resourceHandlerRegistry.hasMappingForPattern("Path Pattern"));
    }
}

