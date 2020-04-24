package br.com.pizzaria.util.Validation;

import br.com.pizzaria.domain.Address;
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
	
	public static boolean phoneIsValid(String phone) {
		if(!(16 > phone.length() && phone.length() > 13 && (phone.matches("(\\(?\\d{2}\\)?\\s)?(\\d{4,5}\\-\\d{4})"))))
			throw new SystemUserInvalidException("Telefone inválido");
		return true;
	}
	
	public static boolean addressIsValid(Address address) {
		if(address == null)
			throw new SystemUserInvalidException("Endereço Vazio");
		if(!(address.getCity() != null && address.getNeighborhood() != null))
			throw new SystemUserInvalidException("Endereço inválido");
		if(!((address.getNumber() != 0 && address.getNumber() < 99999999) && address.getState() != null))
			throw new SystemUserInvalidException("Endereço inválido");
		if(!(address.getStreet() != null))
			throw new SystemUserInvalidException("Endereço inválido");
		return true;
	}
	
	private static boolean emailIsValid(String email) {
		return email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
	}
	
	private static boolean passwordIsValid(String password) {
		return password.length() >= 8;
	}
	
	private static boolean nameIsvALID(String firstName, String lastName) {
		return firstName.length() > 0 && lastName.length() > 0;
	}
}
