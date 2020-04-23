package br.com.pizzaria.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne
	@JoinColumn(name="system_user_id")
	private SystemUser systemUser;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name="favorites",
    joinColumns={@JoinColumn(name="account_id", referencedColumnName="id")},
    inverseJoinColumns={@JoinColumn(name="flavor_id", referencedColumnName="id")})
	private List<Flavor> favorites;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public SystemUser getSystemUser() {
		return systemUser;
	}
	public void setSystemUser(SystemUser systemUser) {
		this.systemUser = systemUser;
	}
	public List<Flavor> getFavorites() {
		return favorites;
	}
	public void setFavorites(List<Flavor> favorites) {
		this.favorites = favorites;
	}
}
