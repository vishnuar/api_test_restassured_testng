package metaweather.api.test;
import java.util.Date;
import java.util.List;
import io.restassured.RestAssured;
import java.text.SimpleDateFormat;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.time.DateUtils;
import io.restassured.specification.RequestSpecification;


public class Base {
	
	//initializing the variables
	private static String ROOT_URI = "https://www.metaweather.com/";
	private static RequestSpecification request;
	
	//initializing the class
	//Test Acceptance
	//Ensured that full anatomy of RESTFul API endpoint is covered, Header("Content-Type)
	public Base() {
        RestAssured.baseURI = ROOT_URI;
        request = RestAssured.given();
        request.header("Content-Type", "application/json");
	}
	
	//Method to get the Where On Earth ID from Location Search API
	//Location name is parameterized and expands for other locations as well
	//Method returns Where On Earth ID on successful execution
	public static String get_woeid(String location) {
	try {
			Response response = request.get(ROOT_URI + "api/location/search/?query="+location);
			if(response.getStatusCode() == 200) {
				JsonPath json_response = response.jsonPath();
				String woeid = json_response.get("woeid").toString();
				woeid = woeid.substring(1, woeid.length() - 1);
				if(!(woeid.equals(""))) {
					return woeid;
				}
				else {
					return "LOCATION NOT FOUND";
				}
			}
			else{
				return "FAILURE";
			}
		}
		catch(Exception e) {
			 System.out.print("Exception caught:" + e);
			 return "FAILURE";
			}
		}
	
	//Method to fetch weather details of a particular location on a specific day
	//Used Location API to fetch the weather details
	//Method parameters, 'woeid' - Location ID, 'weather_date' - Fetch weather details of this particular day
	//Method returns weather state, temperature and humidity
	public static String get_weather_by_day(String woeid, String weather_date) {
		try {
		Response response = request.get(ROOT_URI + "api/location/"+woeid);
		if(response.getStatusCode() == 200) {
			JsonPath json_response = response.jsonPath();
			List<String> all_dates = json_response.getList("consolidated_weather.applicable_date");
			for (int i = 0; i < all_dates.size(); i++) {
				  if(all_dates.get(i).equals(weather_date)) {
					  String [] weather = new String[3];
					  weather[0] = json_response.get("consolidated_weather.weather_state_name["+i+"]").toString();
					  weather[1] = json_response.get("consolidated_weather.the_temp["+i+"]").toString();
					  weather[2] = json_response.get("consolidated_weather.humidity["+i+"]").toString();
					  return "Weather state:"+weather[0]+ ", Current temperature:"+ weather[1]+ ", Humidity:"+weather[2];
				  }
				}
			return "FORECAST DATA NOT FOUND";
		}
		else {
				return "FAILURE";  
			}
		}
		catch(Exception e) {
			 System.out.print("Exception caught:" + e);
			 return "FAILURE";
			}
	}
	
	
	//Method to get tomorrows date
	//Returns tomorrows date in yyyy-MM-dd format
	public static String get_tomorrows_date() {
		Date date = new Date();
		Date incremented_date = DateUtils.addDays(date, 1);
		String tmrrw_date = new SimpleDateFormat("yyyy-MM-dd").format(incremented_date);
		return tmrrw_date;
	}
}
