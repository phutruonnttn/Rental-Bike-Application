package unittest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import application.model.BankAccount;
import application.utils.BankAccountValidate;
import application.utils.DateUtil;

public class ValidateNewBankAccountTest {
	BankAccount bankAccount = new BankAccount("123456789999", null, "Manh Tien", "123456789", DateUtil.parseDate("2022-02-14"), "ViettinBank");
	BankAccountValidate bankAccountValidate = new BankAccountValidate(bankAccount);
	
	@Test
	public void testValidNewBankAccount() {
		String result = this.bankAccount.validate();
		assertEquals("oke", result);
	}
	
	@Test
	public void testInvalidCardName() {
		this.bankAccount.setCardholderName(null);
		this.bankAccountValidate.setBankAccount(bankAccount);
		String result = this.bankAccount.validate();
		assertEquals("CardHolder name cant null or empty", result);
	}
	
	@Test
	public void testCardNumberTooLong() {
		bankAccount = new BankAccount("123456789999", null, "Manh Tien", "123456789", DateUtil.parseDate("2022-02-14"), "ViettinBank");
		this.bankAccount.setCardNumber("123456789123456789123");
		this.bankAccountValidate.setBankAccount(bankAccount);
		String result = this.bankAccount.validate();
		assertEquals("length cardholder number must <= 20", result);
	}
	
	@Test
	public void testNotFullDigitsCardNumber() {
		bankAccount = new BankAccount("123456789999", null, "Manh Tien", "123456789", DateUtil.parseDate("2022-02-14"), "ViettinBank");
		this.bankAccount.setCardNumber("123456sas456");
		this.bankAccountValidate.setBankAccount(bankAccount);
		String result = this.bankAccount.validate();
		assertEquals("cardholder number must be full digits", result);
	}
	
	@Test
	public void testInvalidBankIssue() {
		bankAccount = new BankAccount("123456789999", null, "Manh Tien", "123456789", DateUtil.parseDate("2022-02-14"), "ViettinBank");
		this.bankAccount.setIssueBank("");
		this.bankAccountValidate.setBankAccount(bankAccount);
		String result = this.bankAccount.validate();
		assertEquals("IssueBank cant null or empty", result);
	}
	
	@Test
	public void testInvalidExpirationDate() {
		bankAccount = new BankAccount("123456789999", null, "Manh Tien", "123456789", DateUtil.parseDate("2022-02-14"), "ViettinBank");
		this.bankAccount.setExpirationDate(DateUtil.parseDate("2020-02-02"));
		this.bankAccountValidate.setBankAccount(bankAccount);
		String result = this.bankAccount.validate();
		assertEquals("expiration date invalid", result);
	}
	
	@Test
	public void testInvalidSecurity() {
		bankAccount = new BankAccount("123456789999", null, "Manh Tien", "123456789", DateUtil.parseDate("2022-02-14"), "ViettinBank");
		this.bankAccount.setSecurityCode(null);
		this.bankAccountValidate.setBankAccount(bankAccount);
		String result = this.bankAccount.validate();
		assertEquals("Security code cant null or empty", result);
	}
	
	@Test
	public void testIsRegister() {
		bankAccount = new BankAccount("20002000", null, "Manh Tien", "123456789", DateUtil.parseDate("2022-02-14"), "ViettinBank");
		this.bankAccountValidate.setBankAccount(bankAccount);
		String result = this.bankAccount.validate();
		assertEquals("bankAccount already register", result);
	}
}
