package ren.eggpain.axon.coreapi

import org.axonframework.commandhandling.TargetAggregateIdentifier

class CreateAccountCommand(val accountId: String, val overdraftLimit: Int)
class WithdrawMoneyCommand(@TargetAggregateIdentifier var accountId: String, val amount: Int)
class DepositMoneyCommand(@TargetAggregateIdentifier var accountId: String, val amount: Int)

class AccountCreatedEvent(val accountId: String, val overdraftLimit: Int)
class MoneyWithdrawnEvent(var accountId: String, val amount: Int, val balance: Int)
class MoneyDepositedEvent(val accountId: String, val amount: Int, val balance: Int)

