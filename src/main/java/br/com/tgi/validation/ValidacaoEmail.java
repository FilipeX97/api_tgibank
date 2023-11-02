package br.com.tgi.validation;

public class ValidacaoEmail {
	
	public static boolean validar(String email) {
		String regexEmail = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})$";
		 
		 if(email.matches(regexEmail)) {
			 return true;
		 } else {
			 return false;
		 }
	}

}
