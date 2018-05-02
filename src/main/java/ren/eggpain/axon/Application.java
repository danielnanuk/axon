package ren.eggpain.axon;

import org.axonframework.commandhandling.AsynchronousCommandBus;
import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;

import ren.eggpain.axon.account.Account;
import ren.eggpain.axon.coreapi.CreateAccountCommand;
import ren.eggpain.axon.coreapi.WithdrawMoneyCommand;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

public class Application {
  public static void main(String[] args) {
    Configuration config = DefaultConfigurer.defaultConfiguration()
        .configureAggregate(Account.class)
        .configureEmbeddedEventStore(c -> new InMemoryEventStorageEngine())
        .configureCommandBus(c -> new AsynchronousCommandBus())
        .buildConfiguration();
    config.start();

    config.commandBus()
        .dispatch(asCommandMessage(new CreateAccountCommand("4321", 500)));

    config.commandBus()
        .dispatch(asCommandMessage(new WithdrawMoneyCommand("4321", 250)));

    config.commandBus()
        .dispatch(asCommandMessage(new WithdrawMoneyCommand("4321", 251)));
  }
}
