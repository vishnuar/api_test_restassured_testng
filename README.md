# API testing with RESTAssured and TestNG
Validating MetaWeather APIs with RESTAssured and TestNG. This is created as part of an assignment.

### Prerequisites
Java SDK 8 and above

### Build and running the test
1. Clone this repository
2. Navigate to the directory and execute the below command in the command prompt
```
gradlew clean build
```

#### Run test only
Execute below command when only test needs to execute
```
gradlew cleanTest test
```

### Test results 
Test results will be generated in the following location _.\build\reports\tests\test\index.html_

### Tests validated in suite
1. _As a MetaWeather API client, I want to retrieve "tomorrows" weather for "Nottingham"_

Location value is parameterized in TestNG xml, which is _testing.xml_. A sample screenshot of the generated result.
![image](https://user-images.githubusercontent.com/37209530/101236405-eb272d00-36f6-11eb-8e84-56101ee9a7c2.png)

2. _Stretch Feature - A Customer has reported that they have already found a bug within the API but are unwilling to share the details.  Build a feature test to expose the issue_

API Harness developed to identify the unknown issue, Location and date are parameterized in _.\src\test\resources\api_feeds.csv_. A sample screenshot of the generated result.
![image](https://user-images.githubusercontent.com/37209530/101236704-5eca3980-36f9-11eb-9f8c-47892d058ba2.png)

### Author
* **Vishnu A R** 

