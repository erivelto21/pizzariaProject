package br.com.pizzaria.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.pizzaria.domain.Address;
import br.com.pizzaria.domain.SystemUser;

@Repository
public class SystemUserDaoImpl implements SystemUserDao {

	@PersistenceContext
	private EntityManager entityManager;

	public List<SystemUser> find(long id) {
		return this.entityManager.createQuery("select u from SystemUser u where u.id = :id", SystemUser.class)
				.setParameter("id", id).getResultList();
	}
	
	public List<SystemUser> find(SystemUser user) {
		return this.entityManager
				.createQuery("select u from SystemUser u where u.email = :email and u.password = :password",
						SystemUser.class)
				.setParameter("email", user.getEmail()).setParameter("password", user.getPassword()).getResultList();
	}

	public List<SystemUser> find(String email) {
		return this.entityManager.createQuery("select u from SystemUser u where u.email = :email", SystemUser.class)
				.setParameter("email", email).getResultList();
	}

	public SystemUser persist(SystemUser user) {
		this.entityManager.persist(user);
		return user;
	}

	public void update(SystemUser user) {
		this.entityManager.merge(user);
	}

	public Address findAddress(long id) {
		return this.entityManager.createQuery("select a from Address a where a.id = :id", Address.class)
				.setParameter("id", id).getSingleResult();
	}

	public Address persistAddress(Address address) {
		this.entityManager.persist(address);
		return address;
	}

	public void updateAddress(Address address) {
		this.entityManager.merge(address);
	}

	public String findPhoneByUserId(long id) {
		return this.entityManager.createQuery("select u.phone from SystemUser u where u.id = :id", String.class)
				.setParameter("id", id).getSingleResult();
	}
}
