package cash_access;

public abstract class TransactionImplBase {
	public abstract void deposit(String accountID, double amount)
			throws InvalidParamException;

	public abstract void withdraw(String accountID, double amount)
			throws InvalidParamException, OverdraftException;

	public abstract double getBalance(String accountID)
			throws InvalidParamException;

	public static TransactionImplBase narrowCast(Object rawObjectRef) {

	TransactionImplBase resu = (TransactionImplBase) rawObjectRef;
	return resu;
	}
}
