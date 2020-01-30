package br.com.pizzaria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pizzaria.dao.FlavorDao;
import br.com.pizzaria.domain.Flavor;

@Service
@Transactional
public class FlavorServiceImpl implements FlavorService{

	@Autowired
	private FlavorDao dao;
	
	@Transactional(readOnly = true)
	public List<Flavor> getAllFlavorsList() {
		return dao.getAllFlavors();
	}
}
