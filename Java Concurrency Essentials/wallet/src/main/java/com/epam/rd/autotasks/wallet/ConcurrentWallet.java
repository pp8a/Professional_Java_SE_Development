package com.epam.rd.autotasks.wallet;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import java.util.List;

public class ConcurrentWallet implements Wallet {
	private final List<Account> accounts;
    private final PaymentLog paymentLog;
    static Lock lock = new ReentrantLock();

	public ConcurrentWallet(List<Account> accounts, PaymentLog paymentLog) {
		 this.accounts = accounts;
	     this.paymentLog = paymentLog;
	}

	@Override
	public void pay(String recipient, long amount) throws ShortageOfMoneyException {

		lock.lock();		
		try {
			Account selectedAccount = accounts.stream()
					.filter(account -> account.balance() >= amount)
					.findFirst()
					.orElseThrow(() -> new ShortageOfMoneyException(recipient, amount));
			
			selectedAccount.lock().lock();
			try {
				if(selectedAccount.balance() >= amount) {
					selectedAccount.pay(amount);
					paymentLog.add(selectedAccount, recipient, amount);
				}else {
					throw new ShortageOfMoneyException(recipient, amount);
				}
				
			} finally {
				selectedAccount.lock().unlock();
			}
			
		} finally {
			lock.unlock();
		}
	}

}
