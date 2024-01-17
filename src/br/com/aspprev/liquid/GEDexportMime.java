package br.com.aspprev.liquid;

import java.io.File;
import java.nio.file.Files;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.aspprev.json.ObjectForJson;


public class GEDexportMime {
	String msgRetorno;

	public String requestPostMime(String apiUrl, String authToken, String appKey, String profileId, String fields, String filePath) {
		
		try {
	        if (fileFound(filePath)) {	
				File file = new File(filePath);
				String mimeType = Files.probeContentType(file.toPath());	
				HttpClient httpClient = HttpClients.createDefault();
				HttpPost httpPost = new HttpPost(apiUrl);	
				MultipartEntityBuilder builder = MultipartEntityBuilder.create();				
				builder.addTextBody("profileId", profileId, ContentType.TEXT_PLAIN);
				builder.addTextBody("fields", fields, ContentType.TEXT_PLAIN);
				builder.addBinaryBody("file", file, ContentType.create(mimeType, java.nio.charset.StandardCharsets.UTF_8), file.getName());
	
				org.apache.http.HttpEntity multipart = builder.build();
				httpPost.setEntity(multipart);
				httpPost.setHeader("Authorization", "Bearer " + authToken);
				httpPost.setHeader("x-app-key", appKey);
				httpPost.setHeader(multipart.getContentType());
				
				HttpResponse response = httpClient.execute(httpPost);
	
				if (response.getStatusLine().getStatusCode() == 201) {
					HttpEntity entityResponse = response.getEntity();
					String content = EntityUtils.toString(entityResponse);
					msgRetorno = content;
				} else {
					org.apache.http.HttpEntity entityResponse = response.getEntity();
					String content = EntityUtils.toString(entityResponse);
					msgRetorno = content;
				}
	        } else {	        	
	        	ObjectForJson objFJson 
	        	= new ObjectForJson(1099, "Invalid and/or file not found!");    		        	
	        	msgRetorno = stringToJSON(objFJson);	        	 	        	
	        }		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msgRetorno;
	}
	
    public static boolean fileFound(String filePath) {
    	File oneFound = new File(filePath);
        return oneFound.exists();
    }

    public static String stringToJSON(Object myObject) {
        try {
        	ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(myObject);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }    
}