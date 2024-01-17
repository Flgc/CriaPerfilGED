package br.com.aspprev;

import br.com.aspprev.liquid.GEDexportMime;
import br.com.aspprev.liquid.GEDexportTika;

public class RequestExample {

	public static void main(String[] args) {
	      String apiUrl = "";
	      String authToken = "";
	      String appKey = "";
	      String profileId = "";
	      String fields = "";
	      String filePath = "C:\\temp\\manual.pdf";
	      
	      GEDexportMime expLiqM = new GEDexportMime();
	      GEDexportTika expLiqT = new GEDexportTika();
	      String retornoApiM = expLiqM.requestPostMime(apiUrl, authToken, appKey, profileId, fields, filePath);	      
	      String retornoApiT = expLiqT.requestPostTika(apiUrl, authToken, appKey, profileId, fields, filePath);
	      System.out.println(retornoApiM);
	      System.out.println("****************** Aqui começa o segundo envio! ****************** ");
	      System.out.println(retornoApiT);
	}
}