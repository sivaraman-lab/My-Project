package com.creditcardapplication.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="CUSTOMER_ADDRESS",schema="CRDBO")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AddressEntity {
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String addressId;
	
	@ManyToOne
	@JoinColumn(name="customer_id",referencedColumnName = "customer_id")
	private CreditCardRequesEntity customer;
	
	private String type;
	private String streetdetails;
	private String city;
	private String state;
	private Long pincode;
	
	
	@Override
	public int hashCode() {
		return Objects.hash(addressId, city, pincode, state, streetdetails, type);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddressEntity other = (AddressEntity) obj;
		return Objects.equals(addressId, other.addressId) && Objects.equals(city, other.city)
				&& Objects.equals(pincode, other.pincode) && Objects.equals(state, other.state)
				&& Objects.equals(streetdetails, other.streetdetails) && Objects.equals(type, other.type);
	}
	
}
