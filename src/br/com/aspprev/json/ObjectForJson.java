package br.com.aspprev.json;

public class ObjectForJson {
	public  int errorCode;
	public  String  message;

    public ObjectForJson(int errorCode, String message) {
        this.errorCode= errorCode;
        this.message = message;
    }
}
