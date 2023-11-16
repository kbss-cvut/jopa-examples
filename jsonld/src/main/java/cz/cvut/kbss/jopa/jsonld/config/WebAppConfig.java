/*
 * JOPA Examples
 * Copyright (C) 2023 Czech Technical University in Prague
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
package cz.cvut.kbss.jopa.jsonld.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.kbss.jsonld.ConfigParam;
import cz.cvut.kbss.jsonld.JsonLd;
import cz.cvut.kbss.jsonld.jackson.JsonLdModule;
import cz.cvut.kbss.jsonld.jackson.serialization.SerializationConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Collections;

@Configuration
public class WebAppConfig {

    @Bean(name = "jsonLdMapper")
    public ObjectMapper jsonLdObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // Here we register the JSON-LD serialization/deserialization module
        final JsonLdModule module = new JsonLdModule();
        // Package scan is important for polymorphic deserialization
        module.configure(ConfigParam.SCAN_PACKAGE, "cz.cvut.kbss.jopa.example08");
        module.configure(SerializationConstants.FORM, SerializationConstants.FORM_COMPACT_WITH_CONTEXT);
        objectMapper.registerModule(module);
        return objectMapper;
    }

    @Primary
    @Bean(name = "objectMapper")
    public ObjectMapper objectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    @Bean
    public HttpMessageConverter<?> createJsonLdMessageConverter() {
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(
                jsonLdObjectMapper());
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.valueOf(JsonLd.MEDIA_TYPE)));
        return converter;
    }
}
