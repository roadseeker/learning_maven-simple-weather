/**
 * 
 */
package org.sonatype.mavenbook.ch04.weather;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * <pre>
 * org.sonatype.mavenbook.ch04.weather
 * WeatherFormattrer.java
 * </pre>
 *
 * @author		: roadseeker
 * @Date		: 2018. 4. 29.
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
public class WeatherFormatter {
	
	private static Logger log = Logger.getLogger(WeatherFormatter.class);
	
	public String format(Weather weather) {
		
		log.info("Weather data formatting");
		
		Reader reader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("weather.vm"));
		
		VelocityContext velocityContext = new VelocityContext();
		velocityContext.put("weather", weather);
		StringWriter stringWriter = new StringWriter();
		
		Velocity.evaluate(velocityContext, stringWriter, "", reader);
		
		return stringWriter.toString();
	}

}
