package cash_access;

public class TransactionStub extends TransactionImplBase {

	@Override
	public void deposit(String accountID, double amount)
			throws InvalidParamException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void withdraw(String accountID, double amount)
			throws InvalidParamException, OverdraftException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getBalance(String accountID) throws InvalidParamException {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
