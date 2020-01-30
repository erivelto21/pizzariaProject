package br.com.pizzaria.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.pizzaria.domain.SystemUser;

@Repository
public class SystemUserDaoImpl implements SystemUserDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	public SystemUser getSystemUser(SystemUser user) {
		return this.entityManager
				.createQuery("select u from SystemUser u where u.email = :email and u.password = :password", SystemUser.class)
				.setParameter("email", user.getEmail())
				.setParameter("password", user.getPassword())
				.getSingleResult();
	}
	
	public SystemUser getSystemUser(String email) {
			return this.entityManager
					.createQuery("select u from SystemUser u where u.email = :email", SystemUser.class)
					.setParameter("email", email)
					.getSingleResult();
	}

	public SystemUser save(SystemUser user) {
		this.entityManager.persist(user);
		return user;
	}
}
