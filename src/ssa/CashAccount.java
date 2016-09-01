package ssa;

import java.text.DecimalFormat;

public class CashAccount extends Account {
	private double balance;
	
	public static final double STARTING_BALANCE = 0.0;
	
	public static final String BALANCE_FORMAT = "$#,##0.00";
	
	public static final String TRANSFER_FAILED = "Error - the transfer failed! Please see the specific " +
            "account message.";
	public static final String TRANSFER_SAME_ACCOUNT = "Error - you cannot transfer between the same account!";

	public static final String AMOUNT_BELOW_ZERO = "Error - amount must be above 0!";

	public static final String INSUFFICENT_FUNDS = "Insufficient funds! You cannot withdraw $%.2f " +
			"when your account contains only $%.2f.\n";
	
	public static DecimalFormat balanceFormatter = new DecimalFormat(BALANCE_FORMAT);
	
	public CashAccount() {
		super();
	}
	
	public CashAccount(String description) {
		super(description);
	}
	
	public CashAccount(int id, String description) {
		super(id, description);
		setBalance(0.0);
	}
		
	public CashAccount(double startingBalance, String description) {
		this(description);
		setBalance(STARTING_BALANCE); 
	}
	
	// For our purposes, all accounts provide the depositing functionality by adding the money to the
	// account - so include it in the general Account class
	public double deposit(double amount) {		
		if(amount <= 0) {
			System.out.println(AMOUNT_BELOW_ZERO);
		} else {		
			setBalance(getBalance() + amount);
		}
			
		return getBalance();
	}
			
	// Allows the user to withdraw from the checking account if sufficient funds are available.
	// If insufficient funds are available, no money is withdrawn and an error message is printed.
	// All specific types of accounts must implement this method.
	public double withdraw(double amount) {				
		// Is it a legal amount AND do we have the funds to do the withdrawal?
		if(amount <= 0) {
			System.out.println(AMOUNT_BELOW_ZERO);
		} else if(amount <= getBalance()) {
			setBalance(getBalance() - amount);			
		} else {
			System.out.printf(INSUFFICENT_FUNDS, amount, getBalance());
		}
					
		// Return the updated balance for the account
		return getBalance();
	}
			
	public double getBalance() {
		return balance;
	}
		
	// In a professional implementation, I would have Account and all account types in one package
	// and main in another package. Then only the methods defined in the specific account types can
	// modify the balance. By following the assignment structure, it IS possible for a user to directly
	// manipulate the balance of the account directly
	private void setBalance(double balance) {
		this.balance = balance;
	}
			
	// This method will transfer the amount from the calling account to accountTo. If the withdrawal violates
	// the rules of the calling account, the transfer will fail.
	public void transferFrom(CashAccount fromAccount, double amount) {
		// Make sure these are two different accounts...
		if(this.getId() != fromAccount.getId()) {
						
			// this represents the calling account
			double amountBeforeTransfer = fromAccount.getBalance();
			
			double amountAfterTransfer = fromAccount.withdraw(amount);
			
			// If the withdrawal succeeds, then the balance changed
			if(amountAfterTransfer < amountBeforeTransfer) {	
				// Deposit the amount
				this.deposit(amount);
			}		
			// If here, the transfer failed
			else {
				System.out.println(TRANSFER_FAILED);
			}
		} else {
			System.out.println(TRANSFER_SAME_ACCOUNT);
		}
	}
	
	// Required print method
	public String print() {
		StringBuffer sb = new StringBuffer();
		DecimalFormat balanceFormatter = new DecimalFormat(BALANCE_FORMAT);
			
		sb.append("Account ").append(getId()).append(" balance is ");
		sb.append(balanceFormatter.format(balance));
			
		return sb.toString();
	}
}
