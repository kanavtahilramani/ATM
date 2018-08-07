import java.io.Console;
import java.io.IOException;

public class ATM {
	private Customer activeCustomer; // could be string
	private boolean loggedIn;
	private Console c;

	public ATM() {
		loggedIn = false;
		c = System.console();
	}

	// our running ATM's event loop
	public void start() {
        if (c == null) {
            System.err.println("ERROR: No console found.");
            System.exit(1);
        }

		while (true) {
			while (!loggedIn) {
				char[] pin  = c.readPassword("\nPlease enter your 4-digit customer PIN. (numbers only)\n");
				verifyLogin(pin);
			}

			String option = c.readLine("\nPlease select an option from the menu:\n"
									 + "1) Show balance\n"
									 + "2) Withdraw cash ($"+ activeCustomer.getDailyWithdrawalBalance() + " remaining today)\n"
									 + "3) Make a deposit\n"
									 + "4) Logout\n\n");

			switch(Integer.parseInt(option)) {
				case 1: showBalance();
						break;
				case 2: withdraw();
						break;
				case 3: deposit();
						break;
				case 4: logout();
						break;
				default: System.out.println("\nERROR: Invalid option entered.\n\n=======================================");
						 break;
			}
		}
	}

	// show the active customer's current balance
	private void showBalance() {
		System.out.println("\nYour current account balance is: $" + activeCustomer.getCurrentBalance());
	}

	// subtract an amount from the active customer's current balance
	private void withdraw() {
		try {
			double input = Double.parseDouble(c.readLine("\nHow much would you like to withdraw?\n")); // max number exception to prevent overflow for INTs
			if (input > activeCustomer.getDailyWithdrawalBalance()) {
				System.out.println("\nERROR: Unable to process transaction.\nThat exceeds your remaining daily withdrawal balance of $" + activeCustomer.getDailyWithdrawalBalance() + ".");
			} else if (input > activeCustomer.getCurrentBalance()) {
				System.out.println("\nERROR: Unable to process transaction.\nThat exceeds your current account balance.");
			} else if (input < 0) {
				System.out.println("\nERROR: Unable to process transaction. Please enter a number greater than zero.");
			} else {
				activeCustomer.withdraw(input);
				System.out.println("\nTransaction processed successfully!\nYour account balance is now $" + activeCustomer.getCurrentBalance() + "\nYour remaining daily withdrawal balance is: $" + activeCustomer.getDailyWithdrawalBalance() + ".");
			}
		} catch (NumberFormatException nfe) {
			System.out.println("\nERROR: Unable to process transaction. Invalid input entered!");
		}
	}

	// credit an amount to the active customer's current balance
	private void deposit() {
		try {
			double input = Double.parseDouble(c.readLine("\nHow much would you like to deposit?\n")); // max number exception to prevent overflow for INTs
			if (input < 0) {
				System.out.println("\nERROR: Unable to process transaction. Please enter a number greater than zero.");
			} else {
				activeCustomer.deposit(input);
				System.out.println("\nTransaction processed successfully!\nYour account balance is now $" + activeCustomer.getCurrentBalance() + ".");
			}
		} catch (NumberFormatException nfe) {
			System.out.println("\nInvalid input entered!");
		}
	}

	// exit application gracefully if this option is selected
	private void logout() {
		System.out.println("\nYour final account balance is: $" + activeCustomer.getCurrentBalance() + ". Have a nice day!\n");
		System.exit(1);
	}

	// checks to make sure the user entered a valid PIN
	// 1) four digits (scalability)
	// 2) all numbers (easy user experience)
	private boolean verifyLogin(char[] pin) {
		if (pin.length != 4) return false;

		for (char c : pin) {
			if (!Character.isDigit(c)) return false;
		}

		activeCustomer = new Customer(Integer.parseInt(String.valueOf(pin)));

		return loggedIn = true;
	}
}