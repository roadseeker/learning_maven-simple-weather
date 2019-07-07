/**
 * 
 */
package org.sonatype.mavenbook.ch04.weather;

/**
 * <pre>
 * org.sonatype.mavenbook.ch04.weather
 * Weather.java
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
public class Weather {
	
	private String City;
	private String region;
	private String country;
	private String condition;
	private String temperature;
	private String chill;
	private String humidity;
	
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region.trim();
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country.trim();
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition.trim();
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature.trim();
	}
	public String getChill() {
		return chill;
	}
	public void setChill(String chill) {
		this.chill = chill.trim();
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity.trim();
	}
	
	

}
