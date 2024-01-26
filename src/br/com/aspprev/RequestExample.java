package br.com.aspprev;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.aspprev.liquid.GEDexportMime;

public class RequestExample {

	public static void main(String[] args) {
	      String apiUrl = "";
	      String authToken = "";
	      String appKey = "";
	      String profileId = "";
	      String fields = "";
	      
	      List<File> filePath = new ArrayList<>();

	      filePath.add(new File("C:\\GitProject\\Manual.docx"));
	      filePath.add(new File("C:\\GitProject\\Manual.JPG"));
	      filePath.add(new File("C:\\GitProject\\Manual.pdf"));
	      filePath.add(new File("C:\\GitProject\\Manual.PNG"));
	      
	      GEDexportMime expLiqM = new GEDexportMime();
//	      GEDexportTika expLiqT = new GEDexportTika();
	      String retornoApiM = expLiqM.requestPostMime(apiUrl, authToken, appKey, profileId, fields, filePath);	      
//	      String retornoApiT = expLiqT.requestPostTika(apiUrl, authToken, appKey, profileId, fields, filePath);
	      
	      System.out.println(retornoApiM);
//	      System.out.println("****************** Aqui começa o segundo envio! ****************** ");
//	      System.out.println(retornoApiT);
	}
}