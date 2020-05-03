package br.com.pizzaria.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.pizzaria.domain.RankItem;

@Repository
public class PizzaDaoImpl implements PizzaDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	
	public List<RankItem> findPizzaRank() {
		return castList(RankItem.class, this.entityManager.createNamedQuery("RankItem").getResultList());
	}
	
	private static <T> List<T> castList(Class<? extends T> classValue, Collection<?> c) {
	    List<T> l = new ArrayList<T>(c.size());
	    
	    for(Object o: c)
	      l.add(classValue.cast(o));
	    
	    return l;
	}
}
