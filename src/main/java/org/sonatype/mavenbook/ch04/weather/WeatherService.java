/**
 * 
 */
package org.sonatype.mavenbook.ch04.weather;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.dom4j.DocumentException;

/**
 * <pre>
 * org.sonatype.mavenbook.ch06
 * WeatherService.java
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
public class WeatherService {
	
	public String retrieveForecast(String code) throws MalformedURLException, IOException, DocumentException, InterruptedException, KeyManagementException, NoSuchAlgorithmException {
		//Retrieve Data
		String dataIn = new WeatherRetriever().retrieve(code);
		
		//Parse Data
		Weather weather = new WeatherParser().parse(dataIn);
		
		//Format (Print) Data
		return new WeatherFormatter().format(weather);
	}

}
