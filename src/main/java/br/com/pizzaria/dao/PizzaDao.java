package br.com.pizzaria.dao;

import java.util.List;

import br.com.pizzaria.domain.RankItem;

public interface PizzaDao {

	public List<RankItem> getPizzaRank();
}
