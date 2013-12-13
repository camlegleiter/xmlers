xmlers
======

Senior Design 491 - Task Manager Project
http://seniord.ece.iastate.edu/dec1315/index.html

TaskManager is a web app for creating HTML forms and submitting them for responses to others. 
This provides a much more versatile option than simply emailing users for data and managing the results within email threads.

Setup
=====
Required software:

* Eclipse EE (preferably. Regular Eclipse with the Web Tool plugins works as well, but the EE version has them already)
* Apache Tomcat 7
* MongoDB, if you plan on using the JSON database

Install
=======
1. Download the above software, at least Eclipse EE and Apache Tomcat
2. Clone the repository to a local directory of your choice
3. Open Eclipse EE, and choose the workbench location to be the same directory as in Step 2
4. In the Java EE perspective, create a new Dynamic Web Project, and name it "xmlers" (no quotes), the same as the folder Git puts this source code into
5. Create a local Tomcat server for the project, referencing the Tomcat binaries you downloaded in Step 1. Make sure to choose the same version of Tomcat for the server as the one you downloaded
    * If you used the vanila Eclipse with Web Tool plugins, you may not see all of the Tomcat server options. If this is the case, make sure you have downloaded all JSP/Java web tool and server plugins for Eclipse.
6. If you created the project with the same name as the folder, it should automatically recognize the directory structure and place all files/folders correctly.
7. To run Tomcat, open the "Servers" view and run the Tomcat server associated with the xmlers project.
    * Eclipse will typically open the login page in a browser tab. Once Tomcat is running, you can open a regular browser and navigate to the same URI (e.g., localhost/xmlers/login.jsp or localhost:8080/xmlers/login.jsp)

Environment Variables
=====================
The `src.dbconnect.DBManager` singleton is responsible for creating an instance of a database connection object. Within `DBManager`, there are a list of different Environment Variables:

```
        public static final String XML_STRATEGY = "XML";  
        public static final String SQL_STRATEGY = "SQL";  
        public static final String JSON_STRATEGY = "JSON";  
        public static final String DEBUG_STRATEGY = "DEBUG";  
```

These define which type of database the web app will use; selecting the `XML_STRATEGY` will use the web app's XML database. The Environment Variable can be set in the Eclipse Run Configurations for the Tomcat Server with the key "XMLERS_STRATEGY" and the value of one of the given strategies.  
**NOTE:** currently, the web app supports JSON and some XML functionality.  
By default, if no Environment Variable is found or `XMLERS_STRATEGY` is not set, the `DEBUG_STRATEGY` is used, which will initialize `src.dbconnect.stub.StubController` with some basic data.  
`
JSON_STRATEGY
==============
TaskManager currently uses MongoDB as its JSON document database. If you wish to use the `JSON_STRATEGY`, you will need to download [MongoDB](http://www.mongodb.org/downloads), install it and have it running. Otherwise, all sorts of errors will rain from the sky. No other setup is required with MongoDB--if the collections do not exist in the database, they will be created by the `JsonController` class.

If you wish to connect to your MongoDB instance using a username and password, initialize the `JsonController` with a `MongoClient` object in the `DBManager`.

Adding Your Own Strategy
========================
To add your own strategy option, you must:

1. Implement the `IDBController` interface
2. Create a respective STRATEGY value within `DBManager`
