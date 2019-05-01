package org.maven.rest.address_book;

import static spark.Spark.*;

import org.maven.rest.Entity.Contact;
import org.maven.rest.api.AddressBookApisImpl;

import com.google.gson.Gson;

/**
 * @author jyotinikam
 * Main entry point to the application
 */
public class App 
{
    /**
     * @param args
     * 
     * Main entry point to the application which delegates the request to appropriate method in the service class
     */
    public static void main( String[] args )
    {
    	final AddressBookApisImpl serviceImpl = new AddressBookApisImpl();
    	
       
        get("/contact", (request, response) -> {
        	String pageSize = request.queryParams("pageSize");
        	String page = request.queryParams("page");
        	String query = request.queryParams("query");
        	return new Gson().toJson(new Gson().toJsonTree(serviceImpl.getContacts(pageSize, page, query)));
        });
        
        post("/contact", (request, response) -> {
            response.type("application/json");
            serviceImpl.addContact(new Gson().fromJson(request.body(), Contact.class));
             return new Gson().toJson("Contact successfully created");
        });

        
        get("/contact/:name", (request, response) -> {
            response.type("application/json");
            if(serviceImpl.getContact(request.params(":name"))!=null) {
            	  return new Gson().toJson(new Gson().toJsonTree(serviceImpl.getContact(request.params(":name"))));
            }else {
            	return new Gson().toJson("Contact not found");
            }
          
          
        });
        
        put("/contact/:name", (request, response) -> {
            response.type("application/json");
            Contact contact = new Gson().fromJson(request.body(), Contact.class);
            String result = serviceImpl.editContact(contact, request.params(":name"));
            return new Gson().toJson("Successfully updated " + result);
    });
        
        delete("contact/:name",(request,response)->{
        	 response.type("application/json");
              String result = serviceImpl.deleteContact(request.params(":name"));
              return new Gson().toJson("Successfully deleted " + result);
        });
        
        exception(Exception.class, (exception, request, response) -> {
			response.body(new Gson().toJson(exception.getMessage()));
		});

}
}
