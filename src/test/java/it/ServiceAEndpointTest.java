package it;

import org.junit.Test;

public class ServiceAEndpointTest extends EndpointTest {

    @Test
    public void testDeployment() {
      testEndpoint("/demo/a", 200, "serviceAFallback");
    }
}
