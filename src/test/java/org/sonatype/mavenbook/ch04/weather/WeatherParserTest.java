package org.sonatype.mavenbook.ch04.weather;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

import junit.framework.TestCase;

public class WeatherParserTest extends TestCase {
	
	public WeatherParserTest(String code) {
		super(code);
	}
	
	public void testParser() throws Exception {
		
		InputStream jsonInputStream = getClass().getClassLoader().getResourceAsStream("weather.json");
		
		String jsonString = IOUtils.toString(jsonInputStream, StandardCharsets.UTF_8.name()); 
		
		Weather weather = new WeatherParser().parse(jsonString);
		
		assertEquals("Incheon", weather.getCity());
		assertEquals("Incheon", weather.getRegion());
		assertEquals("South Korea", weather.getCountry());
		assertEquals("81", weather.getChill());
		assertEquals("Mostly Cloudy", weather.getCondition());
		assertEquals("65", weather.getHumidity());
		assertEquals("81", weather.getTemperature());
		
	}
}
