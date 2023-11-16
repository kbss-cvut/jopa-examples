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
package cz.cvut.kbss.jopa.example08.config;

import com.github.ledsoft.jopa.spring.transaction.DelegatingEntityManager;
import com.github.ledsoft.jopa.spring.transaction.JopaTransactionManager;
import cz.cvut.kbss.jopa.example08.persistence.PersistenceFactory;
import cz.cvut.kbss.jopa.model.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@Import(PersistenceFactory.class)
public class PersistenceConfig {

    @Bean
    public DelegatingEntityManager entityManager() {
        return new DelegatingEntityManager();
    }

    @Bean(name = "txManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf,
                                                         DelegatingEntityManager emProxy) {
        return new JopaTransactionManager(emf, emProxy);
    }
}
