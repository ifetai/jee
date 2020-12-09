package de.java2enterprise.onlineshop.model;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;

import java.util.HashSet;
import java.util.List;
import java.util.Set;



/**
 * The persistent class for the customer database table.
 * 
 */
@Entity
@Dependent
//@Table(schema = "webshop", name = "customer")
@NamedQuery(name="Customer.findAll", query="SELECT c FROM Customer c")
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotEmpty
	@Email
	private String email;

	@NotNull
	@NotEmpty
	@Size(min = 1, max = 50)
	private String firstname;

	@NotNull
	@NotEmpty
	@Size(min = 1, max = 50)
	private String name;

	@NotNull
	@NotEmpty
	@Size(min = 6, max = 15)
	private String password;
	
	@Column(nullable = false, columnDefinition = "TINYINT(1)")
	private boolean active;

	//bi-directional many-to-one association to Item
	//@OneToMany(mappedBy="buyer")
	//private Set<Item> purchases;

	public boolean isActive() {
		return active;
	}
	
	public void setActive() {
		this.active = true;
	}

	public void deactivate() {
		this.active = false;
	}

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="seller")
	private Set<Item> offers;

	//bi-directional many-to-many association to Item
	@ManyToMany
	@JoinTable(
		name="observing"
		, joinColumns={
			@JoinColumn(name="observer_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="item_id")
			}
		)
	private List<Item> observed;
	
	 @OneToMany(mappedBy = "customer")
	 private  Set<Purchase> buying;

	 @Transient
	 private String nameAndFirstname;
	 
	public Customer() {
	}	

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

//	public Set<Item> getPurchase() {
//		return this.purchases;
//	}
//
//	public void setPurchase(Set<Item> purchase) {
//		this.purchases = purchase;
//	}

//	public Item addPurchase(Item purchase) {
//		Set<Item> purchases = getPurchase();
//		if(purchases == null) {
//			purchases = new HashSet<Item>();
//		}
//		
//		getPurchase().add(purchase);
//		purchase.setBuyer(this);
//
//		return purchase;
//	}
//
//	public Item removePurchase(Item purchase) {
//		getPurchase().remove(purchase);
//		purchase.setBuyer(null);
//
//		return purchase;
//	}

	public String getNameAndFirstname() {
		this.nameAndFirstname = name + " " + firstname;
		return nameAndFirstname;
	}

	public void setNameAndFirstname(String nameAndFirstname) {
		this.nameAndFirstname = nameAndFirstname;
	}

	public Set<Item> getOffer() {
		return this.offers;
	}

	public void setOffer(Set<Item> offer) {
		this.offers = offer;
	}

	public Item addOffer(Item offer) {
		Set<Item> offers = getOffer();
		if(offers == null) {
			offers = new HashSet<Item>();
		}
		getOffer().add(offer);
		offer.setSeller(this);

		return offer;
	}

	public Item removeOffer(Item offer) {
		getOffer().remove(offer);
		offer.setSeller(null);

		return offer;
	}

	public List<Item> getObserved() {
		return this.observed;
	}

	public void setObserved(List<Item> observed) {
		this.observed = observed;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if(!(obj instanceof Customer)) {
			return false;
		}
		Customer other = (Customer) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else if(!id.equals(other.id)) {
			return false;
		}
		return true;
		
		}
	
	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", vorname=" + firstname
				+ ", email=" + email + ", password=" + password
				+ ", offers=" + offers  + "]";
	}
	
	
	

}