package org.maven.rest.api;

import java.util.List;

import org.maven.rest.Entity.Contact;

/**
 * @author jyotinikam
 * Interface declaring abstract methods to be implemented by  the Implementation Service
 */
public interface AddressBookApis {
	

    public List<Contact> getContacts(String pageSize, String page, String query );
    
    public Contact getContact(String name);
    
    public void addContact(Contact user) throws Exception;

    public String editContact(Contact contact, String name) throws Exception;

    public String deleteContact(String id) throws Exception;

  
}
