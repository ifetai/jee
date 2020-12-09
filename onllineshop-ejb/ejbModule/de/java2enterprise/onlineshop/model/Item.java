package de.java2enterprise.onlineshop.model;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the item database table.
 * 
 */
@Entity
@Dependent
//@Table(schema = "webshop", name = "item")
@NamedQuery(name="Item.findAll", query="SELECT i FROM Item i")
public class Item implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotEmpty
	@Size(min = 1, max = 200)
	private String description;

	@Basic(fetch=FetchType.LAZY)
	@Lob
	private byte[] foto;

	@NotNull
	@DecimalMax(value="9999.0")
	private BigDecimal price;

	@NotNull
	@NotEmpty
	@Size(min = 1, max = 50)
	private String title;
	
	@NotNull
	@Max(value=9999)
	private int stock;
	
	@Transient
	private int quantity;

//	//bi-directional many-to-one association to Customer
//	@ManyToOne
//	private Customer buyer;

	//bi-directional many-to-one association to Category
	//@ManyToOne
	//private Category category;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	private Customer seller;

	//bi-directional many-to-many association to Customer
	@ManyToMany(mappedBy="observed")
	private List<Customer> observer;
	
//	 @OneToMany(mappedBy = "item")
//	 private  Set<Buying> buying;

	public Item() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getFoto() {
		return this.foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

//	public Customer getBuyer() {
//		return this.buyer;
//	}
//
//	public void setBuyer(Customer buyer) {
//		this.buyer = buyer;
//	}

//	public Category getCategory() {
//		return this.category;
//	}
//
//	public void setCategory(Category category) {
//		this.category = category;
//	}
//
//	public Cond getCond() {
//		return this.cond;
//	}
//
//	public void setCond(Cond cond) {
//		this.cond = cond;
//	}

	public Customer getSeller() {
		return this.seller;
	}

	public void setSeller(Customer seller) {
		this.seller = seller;
	}

	public List<Customer> getObserver() {
		return this.observer;
	}

	public void setObserver(List<Customer> observer) {
		this.observer = observer;
	}
	
	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void incrementQuantity() {
		this.quantity ++;
	}
	
	public void decrementQuantity() {
		this.quantity --;
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
		if(!(obj instanceof Item)) {
			return false;
		}
		Item other = (Item) obj;
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
	
	public String toString() {
		return id + "-" + title + "-" + seller;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }



}