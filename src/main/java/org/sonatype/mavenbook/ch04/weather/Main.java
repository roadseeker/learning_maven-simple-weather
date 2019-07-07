/**
 * 
 */
package org.sonatype.mavenbook.ch04.weather;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

/**
 * <pre>
 * org.sonatype.mavenbook.ch04.weather
 * Main.java
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
public class Main {
	
	private static Logger log = Logger.getLogger(Main.class);
	private String code;
	
	public Main() {}
	
	
	public Main(String code) {
		this.code = code;
	}
	
	
	public static void main(String[] args) throws MalformedURLException, IOException, DocumentException, InterruptedException, KeyManagementException, NoSuchAlgorithmException {
		
		String regionCode = "Incheon,ko";
		
		try{
			regionCode = args[0];
		}catch (Exception e) {
			
		}
		
		new Main(regionCode).start();
		
		
	}


	/**
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws DocumentException 
	 * @throws InterruptedException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 *
	 */
	private void start() throws MalformedURLException, IOException, DocumentException, InterruptedException, KeyManagementException, NoSuchAlgorithmException {
		
		//InputStream inputStream = new WeatherRetriever().retrieve(code);
		
		String reponseString = new WeatherRetriever().retrieve(code);
		
		log.info(reponseString);
				
		  Weather weather = new WeatherParser().parse(reponseString);
		  
		  log.info("City ==> " + weather.getCity());
		  log.info("Region ==> " + weather.getRegion().trim());
		  log.info("Country ==> " + weather.getCountry());
		  log.info("Temperature ==> " + weather.getTemperature());
		  log.info("Chill ==> " + weather.getChill());
		  log.info("Humidity ==> " + weather.getHumidity());
		  		  
		  log.info("\r\n" + new WeatherFormatter().format(weather));

	}

}
