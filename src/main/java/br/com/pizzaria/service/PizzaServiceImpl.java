package br.com.pizzaria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pizzaria.dao.PizzaDao;
import br.com.pizzaria.domain.RankItem;

@Service
@Transactional
public class PizzaServiceImpl implements PizzaService{
	
	@Autowired
	private PizzaDao dao;
	
	@Transactional(readOnly = true)
	public List<RankItem> getPizzaRank(){
		return dao.getPizzaRank();
	}
}
