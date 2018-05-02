package ren.eggpain.axon.account;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.Before;
import org.junit.Test;

import ren.eggpain.axon.coreapi.AccountCreatedEvent;
import ren.eggpain.axon.coreapi.CreateAccountCommand;
import ren.eggpain.axon.coreapi.MoneyWithdrawnEvent;
import ren.eggpain.axon.coreapi.WithdrawMoneyCommand;

public class AccountTest {
  private AggregateTestFixture fixture;

  @Before
  public void setUp() throws Exception {
    fixture = new AggregateTestFixture(Account.class);
  }

  @Test
  public void testCreateAccount() throws Exception {
    fixture.givenNoPriorActivity()
        .when(new CreateAccountCommand("1234", 1000))
        .expectEvents(new AccountCreatedEvent("1234", 1000));
  }


  @Test
  public void testWithdrawReasonableAmount() throws Exception {
    fixture.given(new AccountCreatedEvent("1234", 1000))
        .when(new WithdrawMoneyCommand("1234", 500))
        .expectEvents(new MoneyWithdrawnEvent("1234", 500, -500));
  }

  @Test
  public void testWithdrawAbsurdAmount() throws Exception {
    fixture.given(new AccountCreatedEvent("1234", 1000))
        .when(new WithdrawMoneyCommand("1234", 1001))
        .expectNoEvents()
        .expectException(OverdraftLimitException.class);
  }

  @Test
  public void testWithdrawTwice() {
    fixture.given(new AccountCreatedEvent("1234", 1000),
        new MoneyWithdrawnEvent("1234", 999, -999))
        .when(new WithdrawMoneyCommand("1234", 2))
        .expectNoEvents()
        .expectException(OverdraftLimitException.class);
  }
}