package com.ste.mzo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.ste.mzo.entities.Provider;
import com.ste.mzo.repositories.ProviderRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class TestProviderRepository {
	@Autowired
	private ProviderRepository providerRepositoryTest;
	@Test
	@Rollback(false)
	@Order(1)
	public void testCreateProvider() {
		Provider provider = providerRepositoryTest.save(new Provider("NOKIA", "Finland", "nokia@gmail.com"));
		assertThat(provider.getId()).isGreaterThan(11);
	}
	@Test
	@Rollback(false)
	@Order(2)
	public void testFindProviderByAddress() {
		List<Provider> provider = providerRepositoryTest.findProvidersByAdress("USA");
		for(Provider p:provider)
		assertThat(p.getAdress()).isEqualTo("USA");
	}
	@Test
	@Rollback(false)
	@Order(3)
	public void testDeleteProvider() {
		List<Provider> provider = providerRepositoryTest.findProvidersByAdress("USA");
		for(Provider p:provider) {
		assertThat(p.getAdress()).isEqualTo("USA");
		providerRepositoryTest.delete(p);
		}
	}
}
