/**
 * 
 */
package org.sonatype.mavenbook.ch04.weather;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * <pre>
 * org.sonatype.mavenbook.ch04.weather
 * WeatherParser.java
 * </pre>
 *
 * @author		: roadseeker
 * @Date		: 2018. 4. 28.
 * @Version		: 
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------       --------    ---------------------------
 *   
 *
 * </pre>
 */
public class WeatherParser {
	
	private static Logger log = Logger.getLogger(WeatherParser.class);
	
	public Weather parse(String weatherJson) throws DocumentException {
		
		Weather weather = new Weather();
		
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonWeather = (JsonObject) jsonParser.parse(weatherJson);
		
		log.info("JsonParser parsing");
		
		JsonObject location = (JsonObject) jsonWeather.get("location");
		
		weather.setCity(location.get("city").getAsString());
		weather.setRegion(location.get("region").getAsString());
	    weather.setCountry(location.get("country").getAsString());
	    
	    JsonObject observation = (JsonObject) jsonWeather.get("current_observation");
	    
	    JsonObject condition = (JsonObject) observation.get("condition");
	    weather.setTemperature(condition.get("temperature").getAsString());
	    weather.setCondition(condition.get("text").getAsString());
	    
	    JsonObject wind = (JsonObject) observation.get("wind");
	    weather.setChill(wind.get("chill").getAsString());
	    
	    JsonObject atmosphere = (JsonObject) observation.get("atmosphere");
	    weather.setHumidity(atmosphere.get("humidity").getAsString());
	    
		return weather;
		
	}

}
