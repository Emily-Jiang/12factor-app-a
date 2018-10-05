package application.rest;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import javax.ws.rs.GET;
@RegisterRestClient
public interface ServiceBClient{

    @GET
    String hello() throws Exception;
    
}
