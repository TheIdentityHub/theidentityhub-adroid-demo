# theidentityhub-adroid-demo
Demo Android Application for The Identity Hub. The Identity Hub makes it easy to connect your app to all major identity providers like Microsoft, Facebook, Google, Twitter, Linked In and more. For more information see https://www.theidentityhub.com
 
# Getting Started

Download or Clone the repository. 

Find the config.properties file in the src/com/example/apidemo and assets folder and locate the following fragment:
````js
baseUrl = "https://www.theidentityhub.com/[Your URL segment]"; 
````
Change the configuration as follows

1. Replace [Your URL segment] with the url of your tenant on The Identity Hub.

Find the MainActivity.java file in the src/com/example/apidemo and assets folder and locate the following fragment:
````js
String BASE_URL = "https://www.theidentityhub.com/[Your URL segment]";
String CLIENT_ID = "[Your Application Client Id]"; 
````
Change the configuration as follows

1. Replace [Your URL segment] with the url of your tenant on The Identity Hub.
2. Replace [Your Application Client Id] with the client id from your App configured in The Identity Hub.

If you do not have already created an App see https://www.theidentityhub.com/hub/Documentation/#CreateAnApp.

