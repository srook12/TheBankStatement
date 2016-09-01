package ssa;

public class Checking extends CashAccount {
	private int checkNumber;
	
	public static final int STARTING_CHECK_NUM = 100;
	private static int nextCheckNumber = STARTING_CHECK_NUM;
	
	public Checking() {
		// Call to super() is implicit
	}
		
	public Checking(String description) {
		super(description);
	}
		
	public Checking(int id, String description) {
		super(id, description);
	}
		
	public int getCheckNumber() {
		return checkNumber;
	}
	
	public void setCheckNumber(int checkNumber) {
		this.checkNumber = checkNumber;
		nextCheckNumber = this.checkNumber;
	}
	
	@Override
	// Only thing different is incrementing the check number - the
	// rest is handled by the withdraw method in Account
	public double withdraw(double amount) {
		return withdraw(amount, nextCheckNumber++);
	}
	
	// Overloaded to handle the case where the user submits a check
	// number
	public double withdraw(double amount, int checkNumber) {
		setCheckNumber(checkNumber);
		nextCheckNumber = ++checkNumber;
		return super.withdraw(amount);
	}
	
	// Nothing needed for deposit - exactly the same as in Account
	
	@Override
	public String print() {
		StringBuffer sb = new StringBuffer();
		
		String balanceString = CashAccount.balanceFormatter.format(getBalance());
		
		sb.append(String.format("%-1s   ", "C"));
		sb.append(String.format("%-3d   ", getId()));
		sb.append(String.format("%-20s  ", getDescription()));
		sb.append(String.format("%6s   ", balanceString));
		sb.append(String.format("%-3d", getCheckNumber()));
		
		return sb.toString();
	}
}
