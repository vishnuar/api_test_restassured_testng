package metaweather.api.test;

import java.io.File;
import java.util.Scanner;
import java.util.Iterator;
import org.testng.Assert;
import metaweather.api.test.Base;
import org.testng.annotations.Test;
import java.io.FileNotFoundException;
import org.testng.annotations.DataProvider;

//Class for validating the APIHarness 
//**Stretch Feature** - To expose the customer issue
public class APIHarness extends Base {

	//csv data, location and date values are present in the file
	final static String CSV_FILE = "src\\test\\resources\\api_feeds.csv";
    final static String DELIMETER = ",";

	// Base class initialization
	static Base base_test = new Base();
    
	//Method to check the Stretch
	//Using testNG dataProvider method, location and date values are parameterized
    @Test(dataProvider = "csv_feed")
    public void test_api_harness(String value){
        
    	//param[1] - location
    	//param[2] - Date
    	String [] param = value.split(";");
        
		//Calling method to get the woeid of the given location
		String location_id = get_woeid(param[0]);
		
		//Location not found scenario
		if(location_id.equals("LOCATION NOT FOUND")) {
			System.out.println("Location: "+param[0]+ " NOT FOUND");
		
		//Location Search API failed scenario
		} else if (location_id.equals("FAILURE")) {
			System.out.println("Location: "+param[0]+ " TEST FAILED");
			Assert.fail("Location: "+param[0]+ " TEST FAILED");
		}
		
		//Location found and finding forecast data next
		else {	
			//Finding tomorrows date and forecast data
			String weather = get_weather_by_day(location_id, param[1]);
			
			//Forecast data not found scenario
			if(weather.equals("FORECAST DATA NOT FOUND")) {
				System.out.println("Location: "+param[0]+ " Date: "+ param[1]+ " FORECAST NOT FOUND");
			
			//Location API failed scenario	
			}else if (weather.equals("FAILURE")) {
				System.out.println("Location: "+param[0]+ " Date: "+ param[1]+ " TEST FAILED");
				Assert.fail("Location: "+param[0]+ " Date: "+ param[1]+ " TEST FAILED");
			}
			//Forecast data retrieved
			else {
				System.out.println(param[0]+"'s weather on "+param[1]+" - "+weather);
			}
		}
        
    }
    
    //Method to fetch data from the csv file
    @DataProvider(name = "csv_feed")
    public Iterator<Object[]> testDP(){
        try {
            Scanner scanner = new Scanner(new File(CSV_FILE)).useDelimiter(DELIMETER);
            return new Iterator<Object[]>() {
                @Override
                public boolean hasNext() {
                    return scanner.hasNext();
                }
                @Override
                public Object[] next() {
                    return new Object[]{scanner.next()};
                }
            };
            } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
