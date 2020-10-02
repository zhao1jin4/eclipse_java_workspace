/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package data_jpa_mapping_class;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

 
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MapingClassTests {
	//官方示例
	private static final String SERVICE_1 = "Service 1";
	private static final String SERVICE_2 = "Service 2";
	@Autowired SubscriptionRepository repository;
	@BeforeEach
	public void setUp() {
		repository.save(new Subscription(SERVICE_1, 1));
		repository.save(new Subscription(SERVICE_1, 2));
		repository.save(new Subscription(SERVICE_1, 3));
		repository.save(new Subscription(SERVICE_2, 3));
		repository.save(new Subscription(SERVICE_2, 4));
	}

	@Test
	public void shouldReturnCorrectSubscriptionSummary() {
		assertThat(repository.findAllSubscriptionSummaries()) //
				.flatExtracting(s -> asList(s.getProduct(), s.getUsageCount())) //
				.contains(SERVICE_1, 3L, SERVICE_2, 2L);
	}
	@Test
	public void shouldReturnCorrectSubscriptionProjection() {
		assertThat(repository.findAllSubscriptionProjections(100)) //
				.flatExtracting(s -> asList(s.getProduct(), s.getUsageCount())) //
				.contains(SERVICE_1, 3L, SERVICE_2, 2L);
	}
	
 
}
