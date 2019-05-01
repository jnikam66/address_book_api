package org.maven.rest.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.maven.rest.Entity.Contact;
import org.maven.rest.util.AddressBookConstants;
import org.maven.rest.util.Validations;

import com.google.gson.Gson;

/**
 * @author jyotinikam
 *	 Implementation class that implements all the abstract services
 */
public class AddressBookApisImpl implements AddressBookApis {

	private static Gson gson = new Gson();
	RestHighLevelClient client = new RestHighLevelClient(
			RestClient.builder(new HttpHost(AddressBookConstants.HOST, AddressBookConstants.PORT_ONE, AddressBookConstants.SCHEME)));

	/* (non-Javadoc)
	 * @see org.maven.rest.api.AddressBookApis#getContacts(java.lang.String, java.lang.String, java.lang.String)
	 *  returns all the contacts that match the provided query in a paginated format depending upon the value of pageSize and page
	 * 
	 */
	@Override
	public List<Contact> getContacts(String pageSize, String page, String query) {
		// TODO Auto-generated method stub
		List<Contact> searchedContacts = new ArrayList<>();
		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		if (query != null ) {
			sourceBuilder.query(QueryBuilders.queryStringQuery(query));
		}
		if (page != null && pageSize != null) {
			sourceBuilder.from((Integer.parseInt(page) - 1) * Integer.parseInt(pageSize));
			sourceBuilder.size(Integer.parseInt(pageSize));
		}
		searchRequest.source(sourceBuilder);
		try {
			SearchResponse response = client.search(searchRequest);
			SearchHits hits = response.getHits();
			SearchHit[] searchHits = hits.getHits();
			for (SearchHit hit : searchHits) {
				searchedContacts.add(gson.fromJson(hit.getSourceAsString(), Contact.class));
			}

		} catch (ElasticsearchException e) {
			e.getDetailedMessage();
		} catch (java.io.IOException ex) {
			ex.getLocalizedMessage();
		}
		return searchedContacts;
	}

	/* (non-Javadoc)
	 * @see org.maven.rest.api.AddressBookApis#getContact(java.lang.String)
	 * returns contact that matches the unique name passed as parameter
	 * 
	 */
	@Override
	public Contact getContact(String name) {

		Contact searchedContact = new Contact();
		GetRequest getrequest = new GetRequest(AddressBookConstants.INDEX, AddressBookConstants.TYPE, name);
		GetResponse getResponse = null;

		try {
			getResponse = client.get(getrequest);
			searchedContact = gson.fromJson(getResponse.getSourceAsString(), Contact.class);
		} catch (ElasticsearchException e) {
			e.getDetailedMessage();
		} catch (java.io.IOException ex) {
			ex.getLocalizedMessage();
		}
		return searchedContact;
	}
	

	/* (non-Javadoc)
	 * @see org.maven.rest.api.AddressBookApis#addContact(org.maven.rest.Entity.Contact)
	 * adds the provided contact object to the data source
	 * 
	 */
	@Override
	public void addContact(Contact contact) throws Exception {
		// TODO Auto-generated method stub

		XContentBuilder builder = XContentFactory.jsonBuilder();
		builder.startObject();
		{
			builder.field("name", contact.getName());
			builder.field("address", contact.getAddress());
			builder.field("contactEmail", contact.getContactEmail());
			builder.field("contactNumber", contact.getContactNumber());
		}
		builder.endObject();
		IndexRequest indexRequest = new IndexRequest(AddressBookConstants.INDEX, AddressBookConstants.TYPE)
				.id(contact.getName()).source(builder);
		try {
			IndexResponse response = client.index(indexRequest);
			System.out.println(response);
		} catch (ElasticsearchException e) {
			e.getDetailedMessage();
		} catch (java.io.IOException ex) {
			ex.getLocalizedMessage();
		}

	}

	/* (non-Javadoc)
	 * @see org.maven.rest.api.AddressBookApis#editContact(org.maven.rest.Entity.Contact, java.lang.String)
	 * returns id of the edited contact
	 * 
	 */
	@Override
	public String editContact(Contact contact, String name) throws Exception {
		// TODO Auto-generated method stub
		boolean phoneValid = Validations.isPhoneNumberValid(contact.getContactNumber());
		boolean emailValid = Validations.isPhoneNumberValid(contact.getContactNumber());
		String id = new String();
		if (phoneValid && emailValid) {

			XContentBuilder builder = XContentFactory.jsonBuilder();
			builder.startObject();
			{
				builder.field("address", contact.getAddress());
				builder.field("contactEmail", contact.getContactEmail());
				builder.field("contactNumber", contact.getContactNumber());
			}
			builder.endObject();

			try {
				UpdateRequest request = new UpdateRequest(AddressBookConstants.INDEX, AddressBookConstants.TYPE,
						contact.getName()).doc(builder);
				UpdateResponse updateResponse = client.update(request);
				id = updateResponse.getId();
			} catch (java.io.IOException e) {
				e.getLocalizedMessage();
			}

		}
		return id;
	}

	/* (non-Javadoc)
	 * @see org.maven.rest.api.AddressBookApis#deleteContact(java.lang.String)
	 * deletes the contact which matches the unique name parameter  
	 * 
	 */
	@Override
	public String deleteContact(String name) throws Exception {
		// TODO Auto-generated method stub
		String id = new String();
		Contact contactToDelete = getContact(name);
		if (contactToDelete == null) {
			throw new Exception("Contact does not exist");
		}

		try {
			DeleteRequest request = new DeleteRequest(AddressBookConstants.INDEX, AddressBookConstants.TYPE, name);
			DeleteResponse response = client.delete(request);
			id = response.getId();
		} catch (java.io.IOException e) {
			e.getLocalizedMessage();
		}

		return id;

	}

	

}
