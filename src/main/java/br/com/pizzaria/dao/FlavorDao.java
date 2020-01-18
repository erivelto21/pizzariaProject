package br.com.pizzaria.dao;

import java.util.List;

import br.com.pizzaria.domain.Flavor;

public interface FlavorDao {
	
	List<Flavor> getAllFlavors();
}
