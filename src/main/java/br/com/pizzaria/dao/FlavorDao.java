package br.com.pizzaria.dao;

import java.util.List;

import br.com.pizzaria.domain.Flavor;

public interface FlavorDao {
	
	List<Flavor> findAllFlavors();
	
	List<Flavor> find(long id);
}
