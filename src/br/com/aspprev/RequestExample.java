package br.com.aspprev;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.aspprev.liquid.GEDexportMime;
import br.com.aspprev.liquid.GEDexportTika;

public class RequestExample {

	public static void main(String[] args) {
	      String apiUrl = "";
	      String authToken = "";
	      String appKey = "";
	      String profileId = "";
	      String fields = "";
	      
	      List<File> filePath = new ArrayList<>();

	      filePath.add(new File("C:\\temp\\Manual.docx"));
	      filePath.add(new File("C:\\temp\\Manual.JPG"));
	      filePath.add(new File("C:\\temp\\Manual.pdf"));
	      filePath.add(new File("C:\\temp\\Manual.PNG"));
	      
	      GEDexportMime expLiqM = new GEDexportMime();
	      GEDexportTika expLiqT = new GEDexportTika();
	      String retornoApiM = expLiqM.requestPostMime(apiUrl, authToken, appKey, profileId, fields, filePath);	      
	      String retornoApiT = expLiqT.requestPostTika(apiUrl, authToken, appKey, profileId, fields, filePath);
	      
	      System.out.println("****************** Aqui começa o primeiro envio - mimeType = Files.probeContentType! ****************** ");
	      System.out.println(retornoApiM);
	      System.out.println("****************** Aqui começa o segundo envio - otika.detect! ****************** ");
	      System.out.println(retornoApiT);
	}
}