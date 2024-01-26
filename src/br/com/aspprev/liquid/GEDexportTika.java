package br.com.aspprev.liquid;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.tika.Tika;

import br.com.aspprev.json.ObjectForJson;
import br.com.aspprev.uteis.FilesFound;
import br.com.aspprev.uteis.StringToJSON;

public class GEDexportTika {
	String msgRetorno = null;

	public String requestPostTika(String apiUrl, String authToken, String appKey, String profileId, String fields,
			List<File> filePath) {

		try {
			if (FilesFound.isTheFile(filePath)) {
				HttpClient httpClient = HttpClients.createDefault();
				String url = apiUrl;
				HttpPost httpPost = new HttpPost(url);
				MultipartEntityBuilder builder = MultipartEntityBuilder.create();

				String strProfile = profileId;
				String strFields = fields;		

				builder.addTextBody("profileId", strProfile, ContentType.TEXT_PLAIN);
				builder.addTextBody("fields", strFields, ContentType.TEXT_PLAIN);
				
				String oContentType = null;			
				Tika otika = new Tika();				
				
				int nfile = 0;
				String addNfile;
				for (File file : filePath) {
					try {
						addNfile = "file" + String.valueOf(nfile);
						Path path = file.toPath();
						oContentType = otika.detect(path);
						if (nfile == 0) {
							builder.addBinaryBody("file", Files.readAllBytes(path),
									ContentType.create(oContentType, StandardCharsets.UTF_8), file.getName());
						} else {
							builder.addBinaryBody(addNfile, Files.readAllBytes(path),
									ContentType.create(oContentType, StandardCharsets.UTF_8), file.getName());
						}
						nfile++;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}				

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

				ObjectForJson objFJson = new ObjectForJson(1099, "Invalid and/or file not found!");
				msgRetorno = StringToJSON.isTheStringToJSON(objFJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msgRetorno;
	}
}