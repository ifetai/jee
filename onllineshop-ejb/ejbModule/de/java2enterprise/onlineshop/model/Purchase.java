package de.java2enterprise.onlineshop.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.enterprise.context.Dependent;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

@Entity
@Dependent
@NamedQuery(name="Purchase.findAll", query="SELECT b FROM Purchase b")
public class Purchase implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "buyer_id")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;
	
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyy HH:mm");

	private int quantity;
		
	private LocalDateTime sold;
	
	@Transient
	private BigDecimal sum;

	@Transient
	public String getFormattedDate() {
		return sold.format(formatter);
	}
	
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}


	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int pieces) {
		this.quantity = pieces;
	}

	public LocalDateTime getSold() {
		return sold;
	}

	public void setSold(LocalDateTime sold) {
		this.sold = sold;
	}
	
	public BigDecimal getSum() {
		this.sum = item.getPrice().multiply(new BigDecimal(quantity));
		return sum;
	}

	public void setSum(BigDecimal sum) {
		this.sum = sum;
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
		if(!(obj instanceof Purchase)) {
			return false;
		}
		Purchase other = (Purchase) obj;
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
		return id + "-" + customer + "-" + item;
	}
	
	
	
	

}
