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

import br.com.aspprev.uteis.FilesFound;
import br.com.aspprev.uteis.ObjectForJson;
import br.com.aspprev.uteis.StringToJSON;

public class GEDexportMime {
	String msgRetorno;

	public String requestPostMime(String apiUrl, String authToken, String appKey, String profileId, String fields,
			List<File> filePath) {

		try {
			if (FilesFound.isTheFile(filePath)) {
				HttpClient httpClient = HttpClients.createDefault();
				HttpPost httpPost = new HttpPost(apiUrl);
				MultipartEntityBuilder builder = MultipartEntityBuilder.create();
				builder.addTextBody("profileId", profileId, ContentType.TEXT_PLAIN);
				builder.addTextBody("fields", fields, ContentType.TEXT_PLAIN);

				int nfile = 0;
				String addNfile;
				for (File file : filePath) {
					try {
						addNfile = "file" + String.valueOf(nfile);
						Path path = file.toPath();
						String mimeType = Files.probeContentType(path);
						if (nfile == 0) {
							builder.addBinaryBody("file", Files.readAllBytes(path),
									ContentType.create(mimeType, StandardCharsets.UTF_8), file.getName());
						} else {
							builder.addBinaryBody(addNfile, Files.readAllBytes(path),
									ContentType.create(mimeType, StandardCharsets.UTF_8), file.getName());
						}
						nfile++;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				HttpEntity multipart = builder.build();
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
				ObjectForJson objFJson = new ObjectForJson(1099, "Invalid and/or file not found!");
				msgRetorno = StringToJSON.isTheStringToJSON(objFJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msgRetorno;
	}
}