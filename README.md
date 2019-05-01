# address_book_api
Address Book API

RESTful API for an address book with an Elasticsearch data store built using JavaSpark and Junit for unit testing

To use this project follow the steps below :

Step 1: 

Download and install elasticSearch. Once elasticsearch server is installed , get the server up and running by executing the exe file. 

Step 2: 

Clone this git repository / download and extract the .zip file. Import into eclise using the Import Existing Maven project option. 

Step 3:

Get the application up and running. You can now use the REST API's in this project as shown below :  

GET /contact?pageSize={}&page={}&query={}

This endpoint will be providing a listing of all contacts, you will need to allow for a defined pageSize (number of results allowed back), and the ability to offset by page number to get multiple pages. Query also should be a query for queryStringQuery as defined by Elasticsearch that you can pass directly in the Elasticsearch call.

Usage :

http://localhost:4567/contact?pageSize=2&page=1&query=address:California

Response :

[
{"name":"Jyoti","contactNumber":"1234567890","contactEmail":"abc@gmail.com","address":"California"},

{"name":"asdf","contactNumber":"1234567890","contactEmail":"abc@gmail.com","address":"California"}

]

POST /contact

This endpoint should create the contact.  Given that name should be unique.  

http://localhost:4567/contact

Body : {
	"name" :"Jyoti",
	"contactEmail" : "abc@gmail.com",
	"contactNumber" : "1234567890",
	"address" : "California"
}

Response:

"Contact successfully created"


GET /contact/{name}

This endpoint should return the contact by a unique name. This name should be specified by the person entering the data.  

http://localhost:4567/contact/Jyoti

Response :

{
    "name": "Jyoti",
    "contactNumber": "1234567890",
    "contactEmail": "abc@gmail.com",
    "address": "California"
}

PUT /contact/{name}

This endpoint should update the contact by a unique name (and should error if not found)

http://localhost:4567/contact/Jyoti

Body:

Body : {
	"name" :"Jyoti",
	"contactEmail" : "jyoti@gmail.com",
	"contactNumber" : "1234567890",
	"address" : "California"
}

Response:

"Successfully updated Jyoti"


DELETE /contact/{name}

This endpoint should delete the contact by a unique name (and should error if not found)

http://localhost:4567/contact/Jyoti

Response :

"Successfully deleted Jyoti"



