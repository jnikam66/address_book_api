package org.maven.rest.address_book;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.maven.rest.Entity.Contact;
import org.maven.rest.api.AddressBookApisImpl;
import org.mockito.runners.MockitoJUnitRunner;

import junit.framework.TestCase;

/**
 * @author jyotinikam
 * Unit test for App.
 */
@RunWith(MockitoJUnitRunner.class)
public class AppTest extends TestCase
{	
	
	private static AddressBookApisImpl serviceImpl;
	private static Contact contact1;
	private static Contact contact2;


    @BeforeClass
    public static void SetUpFakeELasticSearchServer() throws Exception{
      
      serviceImpl = mock(AddressBookApisImpl.class);

      //Create few instances of Contact class.
      contact1 = new Contact("Jyoti","(657)-232-1212","jyoti@email.com","California");
      contact2 = new Contact("Thomas","(627)-211-1212","thomas@email.com","California");

      //Mocking getContact Method
      when(serviceImpl.getContact(contact1.getName())).thenReturn(contact1);
      when(serviceImpl.getContact(contact2.getName())).thenReturn(contact2);
      
      
      when(serviceImpl.getContacts("2", "1", "address:California"))
				.thenReturn((List<Contact>) Arrays.asList(contact1,contact2));
      
      //Mocking editContact Method
      when(serviceImpl.editContact(contact1, contact1.getName())).thenReturn(contact1.getName());
      when(serviceImpl.editContact(contact2, contact2.getName())).thenReturn(contact2.getName());
      
      //Mocking deleteContact Method
      when(serviceImpl.deleteContact(contact1.getName())).thenReturn(contact1.getName());
      when(serviceImpl.deleteContact(contact2.getName())).thenReturn(contact2.getName());
         }

    @org.junit.Test
	public void testGetContacts() throws Exception {

		List<Contact> contacts = serviceImpl.getContacts("2", "1", "address:California");
		assertEquals(2, contacts.size());
		assertEquals("California", contacts.get(0).getAddress());
		assertEquals("California", contacts.get(1).getAddress());

	}

    @Test
	public void testGetContact() throws Exception {
		Contact contact = serviceImpl.getContact("Jyoti");
		assertEquals(contact, contact1);
	}

    @Test
	public void testaddContact() throws Exception {
		serviceImpl.addContact(contact1);
	}
	
    @Test
	public void testdeleteContact() throws Exception {
		serviceImpl.deleteContact("Jyoti");
	}
    @Test
	public void testeditContact() throws Exception {
		serviceImpl.editContact(contact1, "Jyoti");
	}
}