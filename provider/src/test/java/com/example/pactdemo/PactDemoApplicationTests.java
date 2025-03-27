package com.example.pactdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;

@Provider("PactDemoProvider")
@PactFolder("../pacts")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PactDemoApplicationTests {

	@LocalServerPort
	int port;

	@BeforeEach
	void before(PactVerificationContext context) {
		context.setTarget(new HttpTestTarget("localhost", port));
	}

	@TestTemplate
	@ExtendWith(PactVerificationInvocationContextProvider.class)
	void verifyPact(PactVerificationContext context) {
		context.verifyInteraction();
	}

	@State({
		"order with ID 88 exists",
		"order with ID 999 does not exist"
	})
	void testPactWhenOrderWithIdRequestReceived() {
		// This method is used to set up the state of the provider before the interaction is verified.
		// You can use this method to create any necessary data or perform any actions needed to set up the state.
		// For example, you could create a mock order with ID 88 in your database or in-memory store.
		// This will ensure that when the consumer sends a request for order ID 88, it will receive a valid response.
		// Similarly, you can set up the state for order ID 999 to return a 404 response.
	}
}
