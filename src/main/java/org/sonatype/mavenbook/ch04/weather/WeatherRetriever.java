/**
 * 
 */
package org.sonatype.mavenbook.ch04.weather;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * <pre>
 * org.sonatype.mavenbook.ch04.weather
 * WeatherRetriever.java
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
public class WeatherRetriever {
	
	private static Logger log = Logger.getLogger(WeatherRetriever.class);
	
	
	public static void main(String[] args) throws MalformedURLException, IOException, InterruptedException, KeyManagementException, NoSuchAlgorithmException {
		
		String weatherString = new WeatherRetriever().retrieve("inchoen,ko");
		System.out.println(weatherString);
		
		
	}
	
	public String retrieve(String code) throws MalformedURLException, IOException, InterruptedException, KeyManagementException, NoSuchAlgorithmException {
		
		log.info("Retrieving Weather Data");

		
		final String appId = "WaQLK46k";
        final String consumerKey = "dj0yJmk9cXRSRExTdkppNkI2JmQ9WVdrOVYyRlJURXMwTm1zbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmc3Y9MCZ4PTk0";
        final String consumerSecret = "edff0d1c40da00508f3d0c7cc1a67078d12ffc7e";
        final String url = "https://weather-ydn-yql.media.yahoo.com/forecastrss";

        long timestamp = new Date().getTime() / 1000;
        byte[] nonce = new byte[32];
        Random rand = new Random();
        rand.nextBytes(nonce);
        String oauthNonce = new String(nonce).replaceAll("\\W", "");

        List<String> parameters = new ArrayList<>();
        parameters.add("oauth_consumer_key=" + consumerKey);
        parameters.add("oauth_nonce=" + oauthNonce);
        parameters.add("oauth_signature_method=HMAC-SHA1");
        parameters.add("oauth_timestamp=" + timestamp);
        parameters.add("oauth_version=1.0");
        // Make sure value is encoded 
        parameters.add("location=" + URLEncoder.encode(code, "UTF-8"));
        parameters.add("format=json");
        Collections.sort(parameters);

        StringBuffer parametersList = new StringBuffer();
        for (int i = 0; i < parameters.size(); i++) {
            parametersList.append(((i > 0) ? "&" : "") + parameters.get(i));
        }

        String signatureString = "GET&" +
            URLEncoder.encode(url, "UTF-8") + "&" +
            URLEncoder.encode(parametersList.toString(), "UTF-8");

        String signature = null;
        try {
            SecretKeySpec signingKey = new SecretKeySpec((consumerSecret + "&").getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHMAC = mac.doFinal(signatureString.getBytes());
            Encoder encoder = Base64.getEncoder();
            signature = encoder.encodeToString(rawHMAC);
        } catch (Exception e) {
            log.fatal("Unable to append signature");
            System.exit(0);
        }

        String authorizationLine = "OAuth " +
            "oauth_consumer_key=\"" + consumerKey + "\", " +
            "oauth_nonce=\"" + oauthNonce + "\", " +
            "oauth_timestamp=\"" + timestamp + "\", " +
            "oauth_signature_method=\"HMAC-SHA1\", " +
            "oauth_signature=\"" + signature + "\", " +
            "oauth_version=\"1.0\"";
        
        // set timeout
        int timeout = 1000; // 1s
        RequestConfig.Builder requestBuilder = RequestConfig.custom();
        requestBuilder = requestBuilder.setConnectTimeout(timeout);
        requestBuilder = requestBuilder.setConnectionRequestTimeout(timeout);        
      
        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setDefaultRequestConfig(requestBuilder.build());
        builder.setSSLSocketFactory(SSLUtil.getInsecureSSLConnectionSocketFactory());
        builder.setRedirectStrategy(new DefaultRedirectStrategy());
        HttpClient httpClient = builder.build();
 
        // Get Yahoo Weather API
        HttpGet httpGet = new HttpGet(url + "?location="+code+"&format=json");
        httpGet.addHeader("Authorization", authorizationLine);
        httpGet.addHeader("X-Yahoo-App-Id", appId);
        httpGet.addHeader("Content-Type", "application/json");
 
        HttpResponse httpResponse = httpClient.execute(httpGet);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        String jsonOutput = "";
        System.out.println("<<< Apache HttpClient REST API Call: Get Yahoo Weather >>>");
        if (statusCode == 200 || statusCode == 201) {
            HttpEntity httpEntity = httpResponse.getEntity();
            jsonOutput = EntityUtils.toString(httpEntity);
        } else {
        	log.fatal("Failure! Status Code: " + httpResponse);
        }
 
//        Java 11 above
//        
//        import java.net.http.HttpClient;
//        import java.net.http.HttpRequest;
//        import java.net.http.HttpResponse;
//        import java.net.http.HttpResponse.BodyHandlers;
//        
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//            .uri(URI.create(url + "?location="+code+"&format=json"))
//            .header("Authorization", authorizationLine)
//            .header("X-Yahoo-App-Id", appId)
//            .header("Content-Type", "application/json")
//            .build();
//
//        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
//        String weatherString = response.body();
   
        return jsonOutput;

	}
	
	private static class SSLUtil {
        protected static SSLConnectionSocketFactory getInsecureSSLConnectionSocketFactory()
                throws KeyManagementException, NoSuchAlgorithmException {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
 
                        @Override
                        public void checkClientTrusted(
                                final java.security.cert.X509Certificate[] arg0, final String arg1)
                                throws CertificateException {
                            // do nothing and blindly accept the certificate
                        }
 
                        @Override
                        public void checkServerTrusted(
                                final java.security.cert.X509Certificate[] arg0, final String arg1)
                                throws CertificateException {
                            // do nothing and blindly accept the server
                        }
                    }
            };
 
            final SSLContext sslcontext = SSLContext.getInstance("SSL");
            sslcontext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
 
            final SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(
                    sslcontext, new String[]{"TLSv1"}, null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            
            return sslSocketFactory;
        }
    }
	
}
