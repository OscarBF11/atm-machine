# ATM Machine
This is a JAVA Spring Boot project simulating ATM machine's logic

### About API
The ATM Machine provides two services.
* Dispense service - To dispense money from the user account 
* Check balance service - To check current amount of each type of notes the ATM Machine currently has

### Technologies
* [Maven](https://maven.apache.org/) - Software project management
* [Spring Boot](https://projects.spring.io/spring-boot/) - Quickly create stand-alone, production-grade Spring based Applications
* [H2](http://www.h2database.com/html/main.html) - A Java in-memory database providing JDBC API
* [Mockito](http://site.mockito.org/) - Write beautiful tests with a clean & readable with simple API

### Prerequisites
* Maven - you can easily install it on MAC OS X by 
```
$ brew install maven
```

### Running project on MAC OS X
* Clone and go to the project by running command
```
$ git clone git@github.com:OscarBF11/atm-machine.git
$ cd atm-machine
```

* Then prepare project dependencies with
```
$ mvn clean install
```

* Run project using command
```
$ mvn spring-boot:run
```

The project should start on port 8080

### How to test the API
You can request dispensing money via [Postman](https://www.getpostman.com)
```
http://localhost:8080/atm-simulator/dispense?amount=<AMOUNT>&accountNumber=<ACCOUNT_NUMBER>&pin=<PIN>
```

while \<AMOUNT\> indicates money you want to dispense from ATM Simulator, \<ACCOUNTNUMBER\> is a unique identifier of your account and  \<PIN\> indicates your secret key to access
<br/>The response should look like
```
{
    "responseCode": "0",
    "responseDesc": "OK - Actual balance: 30€",
    "responseStatus": "SUCCESS",
    "responseBody": [
        {
            "type": "FIFTY",
            "value": 50,
            "amount": 2
        },
        {
            "type": "TWENTY",
            "value": 20,
            "amount": 1
        }
    ]
}
```

```
http://localhost:8080/atm-simulator/checkBalance
```
#### Possible API response
* Check balance success (This option shows the balance of the ATM and NOT of the account)
```
{
    "responseCode": "0",
    "responseDesc": "SUCCESS",
    "responseStatus": "SUCCESS",
    "responseBody": [
        {
            "type": "FIFTY",
            "value": 50,
            "amount": 14
        },
        {
            "type": "TWENTY",
            "value": 20,
            "amount": 20
        },
        {
            "type": "TEN",
            "value": 10,
            "amount": 25
        },
        {
            "type": "FIVE",
            "value": 5,
            "amount": 10
        }
    ]
}
```

* Dispensing success
```
{
    "responseCode": "0",
    "responseDesc": "OK - Actual balance: 80€",
    "responseStatus": "SUCCESS",
    "responseBody": [
        {
            "type": "FIFTY",
            "value": 50,
            "amount": 2
        },
        {
            "type": "TWENTY",
            "value": 20,
            "amount": 1
        }
    ]
}
```
* Insufficient balance - Total balance of user account is less then needed amount
```
{
    "responseCode": "1",
    "responseDesc": "Insufficient balance - Actual balance: 80€",
    "responseStatus": "FAIL",
    "responseBody": []
}
```

* Insufficient balance - Total balance of ATM is less then needed amount
```
{
    "responseCode": "1",
    "responseDesc": "Remaining balance less than dispensed amount",
    "responseStatus": "FAIL",
    "responseBody": []
}
```

* Invalid amount - Needed amount is less than minimum value of the note in ATM (which, in this case, is 5)
```
{
    "responseCode": "1",
    "responseDesc": "Amount less than min amount",
    "responseStatus": "FAIL",
    "responseBody": []
}
```

* Insufficient note - ATM Simulator doesn't have enough notes needed by requested amount or the simulator cannot find appropriate combination of notes to fulfill requested amount
```
{
    "responseCode": "1",
    "responseDesc": "Insufficient note number. Try dispensing a different amount.",
    "responseStatus": "FAIL",
    "responseBody": []
}
```
