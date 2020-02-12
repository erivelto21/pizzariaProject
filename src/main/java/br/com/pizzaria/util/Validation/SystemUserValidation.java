package br.com.pizzaria.util.Validation;

import br.com.pizzaria.domain.SystemUser;
import br.com.pizzaria.exception.SystemUserInvalidException;

public class SystemUserValidation {
	
	public static boolean systemUserIsValid(SystemUser user) {
		if(!emailIsValid(user.getEmail())) {
			throw new SystemUserInvalidException("Email inválido");
		} 
		
		if(!passwordIsValid(user.getPassword())) {
			throw new SystemUserInvalidException("Senha inválida");
		}
		
		if(!nameIsvALID(user.getFirstName(), user.getLastName())) {
			throw new SystemUserInvalidException("Primeiro ou último nome inválidos");
		}
		
		return true;
	}
	
	private static boolean emailIsValid(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}
	
	private static boolean passwordIsValid(String password) {
		return password.length() >= 8;
	}
	
	private static boolean nameIsvALID(String firstName, String lastName) {
		return firstName.length() > 0 && lastName.length() > 0;
	}
}
