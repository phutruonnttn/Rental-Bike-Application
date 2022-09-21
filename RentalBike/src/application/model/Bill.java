package application.model;
// Generated Dec 4, 2021, 8:58:47 PM by Hibernate Tools 3.5.0.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import application.calculateFee.NormalCalculateRentBikeFee;
import application.calculateFee.Interface.ICalculateRentBikeFeeModel;

/**
 * Bill generated by hbm2java
 */
@Entity
@Table(name = "bill", schema = "public")
public class Bill implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int billCode;
	private Bike bike;
	private Customer customer;
	private Date date;
	private int minutes;
	private Set<Transaction> transactions = new HashSet<Transaction>(0);
	
	private ICalculateRentBikeFeeModel calculatFeeModel;

	public Bill() {
	}

	public Bill(Bike bike, Customer customer, Date date, int minutes) {
		this.bike = bike;
		this.customer = customer;
		this.date = date;
		this.minutes = minutes;
		this.calculatFeeModel = new NormalCalculateRentBikeFee(this.bike);
	}

	public Bill(Bike bike, Customer customer, Date date, int minutes, Set<Transaction> transactions) {
		this.bike = bike;
		this.customer = customer;
		this.date = date;
		this.minutes = minutes;
		this.transactions = transactions;
		this.calculatFeeModel = new NormalCalculateRentBikeFee(this.bike);
	}

	@Id

	@Column(name = "bill_code", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getBillCode() {
		return this.billCode;
	}

	public void setBillCode(int billCode) {
		this.billCode = billCode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bike_code", nullable = false)
	public Bike getBike() {
		return this.bike;
	}

	public void setBike(Bike bike) {
		this.bike = bike;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_code", nullable = false)
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date", nullable = false, length = 13)
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "minutes", nullable = false)
	public int getMinutes() {
		return this.minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bill")
	public Set<Transaction> getTransactions() {
		return this.transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

	// fee calculated with using time > 30 minutes
	public int calculateExtraFee() {
		// calculate fee
		return 0;
	}

	// total amount = baseFee + extraFee
	public int calculateTotalFee() {
		// calculate fee
		return 0;
	}
	
	public int calculateFirstTransactionAmount() {
		return this.getBike().getFirstTransactionAmount(calculatFeeModel);
	}
	
	public int calculateInitFee() {
		return this.getBike().getInitFee(calculatFeeModel);
	}
}
