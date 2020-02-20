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

	public List<SystemUser> getSystemUser(SystemUser user) {
		return this.entityManager
				.createQuery("select u from SystemUser u where u.email = :email and u.password = :password",
						SystemUser.class)
				.setParameter("email", user.getEmail()).setParameter("password", user.getPassword()).getResultList();
	}

	public List<SystemUser> getSystemUser(String email) {
		return this.entityManager.createQuery("select u from SystemUser u where u.email = :email", SystemUser.class)
				.setParameter("email", email).getResultList();
	}

	public SystemUser save(SystemUser user) {
		this.entityManager.persist(user);
		return user;
	}

	public void updateSystemUser(SystemUser user) {
		this.entityManager.merge(user);
	}

	public Address get(long id) {
		return this.entityManager.createQuery("select a from Address a where a.id = :id", Address.class)
				.setParameter("id", id).getSingleResult();
	}

	public long saveAddress(Address address) {
		this.entityManager.persist(address);
		return address.getId();
	}

	public void updateAddress(Address address) {
		this.entityManager.merge(address);
	}

	public String getPhone(long id) {
		return this.entityManager.createQuery("select u.phone from SystemUser u where u.id = :id", String.class)
				.setParameter("id", id).getSingleResult();
	}
}
