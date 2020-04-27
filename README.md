# covid19-api
This is an implementation of some basic rest API to get covid-19 details. The project is built in the Spring Boot Framework, and 
connects to the John Hopkins free dataset available on GCP. You can find more information on it [here](#https://console.cloud.google.com/marketplace/details/johnshopkins/covid19_jhu_global_cases).

The application uses docker to build and run it in a containerized fashion. Moreover, there is a GitHub actions workflow set up which builds and deploys the docker 
image to my private repo in DockerHub, as well as my Heroku application. 

## Using the API
The APIs are available at this URL: https://covid19-rest-api.herokuapp.com/swagger-ui.html

Due to the [free dyno](#https://www.heroku.com/pricing) restrictions on Heroku, there is a possibility that the app may be switched off due to idling. In that case, please wait for a
minute or two for the page to load up.

The swagger screen shows the APIs that the application provides. Feel free to use the 'Try It Out' option that is available to view the APIs and response structures.


