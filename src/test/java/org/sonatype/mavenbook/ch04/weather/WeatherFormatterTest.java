package org.sonatype.mavenbook.ch04.weather;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

import junit.framework.TestCase;

public class WeatherFormatterTest extends TestCase {
	
	public WeatherFormatterTest(String name) {
		super(name);
	}
	
	public void testWeatherFormatter() throws Exception {
		InputStream jsonData = getClass().getClassLoader().getResourceAsStream("weather.json");
		
		String weatherJson = IOUtils.toString(jsonData, StandardCharsets.UTF_8.name());
		
		Weather weather = new WeatherParser().parse(weatherJson);
		String formattedResult = new WeatherFormatter().format(weather);
		
		InputStream expectedInputStream = getClass().getClassLoader().getResourceAsStream("format-expected.dat");
		String expectedString = IOUtils.toString(expectedInputStream, StandardCharsets.UTF_8.name());
		
		assertEquals(expectedString.trim(), formattedResult.trim() );
	}

}
