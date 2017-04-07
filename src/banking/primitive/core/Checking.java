package banking.primitive.core;

public class Checking extends Account {

	private static final long serialVersionUID = 11L;
	final float OVERDRAWN_LIMIT = -100.0f;
	final int MAX_WITHDRAWALS = 10;
	final float WITHDRAWAL_FEE = 2.0f;
	final float EMPTY = 0.0f;
	private int numWithdraws = 0;
	
	private Checking(String name) {
		super(name);
	}

    public static Checking createChecking(String name) {
        return new Checking(name);
    }

	public Checking(String name, float balance) {
		super(name, balance);
	}

	/**
	 * A deposit may be made unless the Checking account is closed
	 * @param float is the deposit amount
	 */
	public boolean deposit(float amount) {
		if (getState() != State.CLOSED && amount > EMPTY) {
			balance = balance + amount;
			if (balance >= EMPTY) {
				setState(State.OPEN);
			}
			return true;
		}
		return false;
	}

	/**
	 * Withdrawal. After 10 withdrawals a fee of $2 is charged per transaction You may 
	 * continue to withdraw an overdrawn account until the balance is below -$100
	 */
	public boolean withdraw(float amount) {
		if (amount > EMPTY) {		
			// KG: incorrect, last balance check should be >=
			if (getState() == State.OPEN || (getState() == State.OVERDRAWN && balance > OVERDRAWN_LIMIT)) {
				balance = balance - amount;
				numWithdraws++;
				if (numWithdraws > MAX_WITHDRAWALS)
					balance = balance - WITHDRAWAL_FEE;
				if (balance < EMPTY) {
					setState(State.OVERDRAWN);
				}
				return true;
			}
		}
		return false;
	}

	public String getType() { return "Checking"; }
	
	public String toString() {
		return "Checking: " + getName() + ": " + getBalance();
	}
}
