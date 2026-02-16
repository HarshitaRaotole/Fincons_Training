package com.example.parking_management;

import com.example.parking_management.service.ParkingEventProducer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ParkingManagementApplicationTests {
	@MockBean
	private ParkingEventProducer eventProducer;

	@Test
	void contextLoads() {
	}

}
