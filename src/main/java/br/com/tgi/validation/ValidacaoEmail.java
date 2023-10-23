package br.com.tgi.validation;

public class ValidacaoEmail {
	
	public static boolean validar(String email) {
		 String regexEmail = "^[A-Za-z0-9+_.-]+@(.+)$";
		 
		 if(email.matches(regexEmail)) {
			 return true;
		 } else {
			 return false;
		 }
	}

}
