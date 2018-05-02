package ren.eggpain.axon.account;

import lombok.NoArgsConstructor;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

import ren.eggpain.axon.coreapi.AccountCreatedEvent;
import ren.eggpain.axon.coreapi.CreateAccountCommand;
import ren.eggpain.axon.coreapi.MoneyWithdrawnEvent;
import ren.eggpain.axon.coreapi.WithdrawMoneyCommand;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate(repository = "jpaAccountRepository")
@NoArgsConstructor
@Entity
public class Account {

  @AggregateIdentifier
  @Id
  private String accountId;
  @Basic
  private int balance;
  @Basic
  private int overdraftLimit;

  @CommandHandler
  public Account(CreateAccountCommand command) {
    apply(new AccountCreatedEvent(command.getAccountId(), command.getOverdraftLimit()));
  }

  @CommandHandler
  public void handle(WithdrawMoneyCommand command) {
    if (balance + overdraftLimit >= command.getAmount()) {
      apply(new MoneyWithdrawnEvent(accountId, command.getAmount(), balance - command.getAmount()));
    } else {
      throw new OverdraftLimitException();
    }
  }

  @EventSourcingHandler
  public void on(AccountCreatedEvent event) {
    this.accountId = event.getAccountId();
    this.overdraftLimit = event.getOverdraftLimit();
  }

  @EventSourcingHandler
  public void on(MoneyWithdrawnEvent event) {
    this.balance = event.getBalance();
  }
}
