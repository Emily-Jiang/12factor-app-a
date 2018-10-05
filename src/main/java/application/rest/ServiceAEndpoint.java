package application.rest;

import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@RequestScoped
@Path("a")
public class ServiceAEndpoint {

    @Inject @ConfigProperty(name="application.rest.ServiceBClient/mp-rest/url") String url;
    static int callCount;
    int tries;

    @Inject @RestClient ServiceBClient client;

    @GET
    @Retry
    @Fallback(fallbackMethod="serviceAFallback")
    @Produces(MediaType.TEXT_PLAIN)
    public String callServiceB() {
      ++callCount;
      ++tries;
      return "Hello from serviceA (" + this + ")\n " + callService();
    }

    public String serviceAFallback() {
        return "Hello from serviceAFallback at " + new Date() + " (ServiceA call count: " + callCount + ")\nCompletely failed to call B after " + tries + " tries";
    }

     private String callService() {

        StringBuilder sb = new StringBuilder();
        sb.append("Calling service at: ")
            .append(url)
            .append(" (ServiceA call count: " + callCount + ", tries: " + tries)
            .append(")");
        System.out.println(sb.toString());

        sb.append("\n");

        String result = null;
        
        try {
            result = client.hello();
                        
          } catch (Exception e) {
          System.out.println("Caught exception");
          e.printStackTrace();
          throw new RuntimeException(e);
        } 
        return sb.append(result).toString();
    } 
}
