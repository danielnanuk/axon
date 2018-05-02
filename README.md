# Axon 

*Axon Framework Webinars*

## Our Case
### Bank Account
 + create
     + AccountCreated
 + WithdrawMoney
    + MoneyWithdrawn
    + AccountOverdrawn (Exception)
 + DepositMoney
    + MoneyDeposited 
   
## Aggregate

+ @CommandHanlder
+ @EventSourcingHandler
+ apply()

### Test Fixtures

+ Given... when... then...
+ Check for published events
+ Check for exceptions

### Configuration API

+ Pure Java: Configurer
+ Spring: @EnableAxon
+ JPA Event Store configuration
+ CommandBus Customization
 
