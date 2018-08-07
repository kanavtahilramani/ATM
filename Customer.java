public class Customer {
	private int pin;
	private double currentBalance;
	private double dailyWithdrawalBalance;
	
	public Customer(int pin) {
		this.pin = pin;
		currentBalance = 0;
		dailyWithdrawalBalance = 1000;
	}

	public double getCurrentBalance() {
		return currentBalance;
	}

	public double getDailyWithdrawalBalance() {
		return dailyWithdrawalBalance;
	}

	public void withdraw(double amount) {
		dailyWithdrawalBalance -= amount;
		currentBalance -= amount;
	}

	public void deposit(double amount) {
		currentBalance += amount;
	}
}