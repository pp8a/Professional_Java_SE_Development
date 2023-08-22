# Concurrent Wallet

1. Create a wallet which allows using multiple accounts and process payments using a suitable account from the available ones.
2. You can find the interfaces `Account`, `PaymentLog` and `Wallet`, as well as `ShortageOfMoneyException` and `WalletFactory`.
3. There is no need to implement the `Account` and `PaymentLog` interfaces. They are presented just to provide an API to you. However, you may want to implement them during your local testing `ShortageOfMoneyException` does not require any changes.
4. `WalletFactory` provides only one method - `wallet(List<Account>,PaymentLog)`, that creates a new instance of `Wallet` and passes a list of accounts to it as well as paymenmt log. You need to return your implementation of `Wallet` here.
5. `Wallet` interface has only one method `pay(String, long)`,
that you need to implement.
This method must find an account whose balance exceeds requested amount,
decrease the amount of the account balance by specific amount and log this operation by sending information about the recipient and the amount to payment log. If no account can handle the payment, throw `ShortageOfMoneyException` including information about
recipient and the requested amount. Any other exception will be considered as
invalid. Also be aware that this method must work
correctly in multi-threaded environment, because it will be called from multiple threads simultaneously.

To complete the task, you need to
- Implement `Wallet` interface
- Specify your implementation in `WalletFactory`
- Ensure that your solutions passes the tests

Задача: Реализация Конкурентного Кошелька

1. Создайте кошелек, который позволяет использовать несколько счетов и обрабатывает платежи, используя подходящий счет из доступных.
2. Используйте интерфейсы Account, PaymentLog и Wallet, а также классы ShortageOfMoneyException и WalletFactory.
3. Вам не требуется реализовывать интерфейсы Account и PaymentLog. Они предоставлены вам для создания API. Тем не менее, вам может понадобиться их реализация во время локального тестирования. Класс ShortageOfMoneyException не требует изменений.
4. Класс WalletFactory предоставляет только один метод - wallet(List<Account>, PaymentLog), который создает новый экземпляр кошелька и передает ему список счетов и журнал платежей. Вам нужно вернуть свою реализацию интерфейса Wallet.
5. Интерфейс Wallet имеет только один метод pay(String, long), который вам нужно реализовать. 
Этот метод должен найти счет, баланс которого превышает запрошенную сумму, уменьшить баланс счета на указанную сумму и зарегистрировать эту операцию, отправив информацию о получателе и сумме в журнал платежей. Если ни один счет не может выполнить платеж, выбросьте исключение ShortageOfMoneyException, включив информацию о получателе и запрошенной сумме. Любое другое исключение считается недопустимым. Также имейте в виду, что этот метод должен корректно работать в многопоточной среде, так как он будет вызываться из нескольких потоков одновременно.

Для выполнения задачи выполните следующее:
• Реализуйте интерфейс Wallet.
• Укажите вашу реализацию в классе WalletFactory.
• Убедитесь, что ваше решение проходит тесты.