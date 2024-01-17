package br.com.aspprev.liquid;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.tika.Tika;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.aspprev.json.ObjectForJson;

public class GEDexportTika {
	String msgRetorno = null;

	public String requestPostTika(String apiUrl, String authToken, String appKey, String profileId, String fields, String ofilePath) {
		
		try {
	        if (fileFound(ofilePath)) {
				String oContentType = null;
				File ofile = new File(ofilePath);
				Tika otika = new Tika();
				oContentType = otika.detect(ofile);

				HttpClient httpClient = HttpClients.createDefault();
				String url = apiUrl;
				HttpPost httpPost = new HttpPost(url);
				MultipartEntityBuilder builder = MultipartEntityBuilder.create();

				String strProfile = profileId;
				String strFields = fields;

				builder.addTextBody("profileId", strProfile, ContentType.TEXT_PLAIN);
				builder.addTextBody("fields", strFields, ContentType.TEXT_PLAIN);
				builder.addBinaryBody("file", ofile, ContentType.create(oContentType, StandardCharsets.UTF_8), ofile.getName());

				HttpEntity multipart = builder.build();

				String strAuthToken = authToken;
				String strAppKey = appKey;

				httpPost.setEntity(multipart);
				httpPost.setHeader("Authorization", "Bearer " + strAuthToken);
				httpPost.setHeader("x-app-key", strAppKey);
				httpPost.setHeader(multipart.getContentType());

				HttpResponse response = httpClient.execute(httpPost);

				if (response.getStatusLine().getStatusCode() == 201) {
					HttpEntity entityResponse = response.getEntity();
					String content = EntityUtils.toString(entityResponse);
					msgRetorno = content;
				} else {
					HttpEntity entityResponse = response.getEntity();
					String content = EntityUtils.toString(entityResponse);
					msgRetorno = content;
				}        	                 
	        	
	        } else {
	        	
	        	ObjectForJson objFJson	= new ObjectForJson(1099, "Invalid and/or file not found!");    		        	
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