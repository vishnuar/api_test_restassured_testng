package metaweather.api.test;

import org.testng.Assert;
import metaweather.api.test.Base;
import org.testng.annotations.Test;
import org.testng.annotations.Parameters;

// Class file for running the tests
// Extended from base class
public class LocationWeatherTest extends Base {
	
	// Base class initialization
	static Base base_test = new Base();
	
	/*public static void main(String[] args) {
		test_nottinghams_tomorrows_weather();
	}*/
	
	//Validating the Feature to test
	//As a MetaWeather API client, I want to retrieve "tomorrows" weather for "Nottingham"
	//City value added in the testNG(testing.xml) xml as parameter - ## Things to think about - 1
	//Date also parameterized hence different dates can be tested - ## Things to think about - 2
	@Test
	@Parameters({"city"})  
	public static void test_nottinghams_tomorrows_weather(String city) {
		
		//Calling method to get the woeid of the given location
		String location_id = get_woeid(city);
		
		//Location not found scenario
		if(location_id.equals("LOCATION NOT FOUND")) {
			System.out.println("Location: "+city+ " NOT FOUND");
		
		//Location Search API failed scenario
		} else if (location_id.equals("FAILURE")) {
			System.out.println("Location: "+city+ " TEST FAILED");
			Assert.fail("Location: "+city+ " TEST FAILED");
		}
		
		//Location found and finding forecast data next
		else {	
			//Finding tomorrows date and forecast data
			String date = get_tomorrows_date();
			String weather = get_weather_by_day(location_id, date);
			
			//Forecast data not found scenario
			if(weather.equals("FORECAST DATA NOT FOUND")) {
				System.out.println("Location: "+city+ " Date: "+ date+ " FORECAST NOT FOUND");
			
			//Location API failed scenario	
			}else if (weather.equals("FAILURE")) {
				System.out.println("Location: "+city+ " Date: "+ date+ " TEST FAILED");
				Assert.fail("Location: "+city+ " Date: "+ date+ " TEST FAILED");
			}
			//Forecast data retrieved
			else {
				System.out.println(city+"'s weather on "+date+" - "+weather);
			}
		}
	}
}
