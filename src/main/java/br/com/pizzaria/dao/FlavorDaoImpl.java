package br.com.pizzaria.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.pizzaria.domain.Flavor;

@Repository
public class FlavorDaoImpl implements FlavorDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	public List<Flavor> getAllFlavors() {
		return this.entityManager
				.createQuery("select f from Flavor f", Flavor.class)
				.getResultList();
	}
}
