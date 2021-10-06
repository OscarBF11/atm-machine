# ATM Machine
Es tracta d’un projecte JAVA Spring Boot que simula la lògica d'un caixer automàtic o ATM.

### Sobre l'API
El caixer automàtic proporciona dos serveis.
* Servei de dispensació: per distribuir diners del compte d'usuari.
* Servei de consulta: per comprovar l'import actual de cada tipus de bitllets que té actualment el caixer automàtic.

### Tecnologíes
* [Maven] (https://maven.apache.org/) - Gestió de projectes de programari
* [Spring Boot] (https://projects.spring.io/spring-boot/): creeu ràpidament aplicacions basades en Spring, autònomes i de producció
* [H2] (http://www.h2database.com/html/main.html): Una base de dades Java en memòria que proporciona l'API JDBC
* [Mockito] (http://site.mockito.org/) - Escriviu proves fàcilment amb un API net de manera llegible. (L'apartat de testos es troba actualment es desenvolupament i pot haver-hi errors)

### Requisits
* Maven: podeu instal·lar-lo fàcilment a MAC OS X mitjançant 
```
$ brew install maven
```

### Executar el prjecte en MAC OS X
* Cloneu i aneu al projecte executant l'ordre:
```
$ git clone git@github.com:OscarBF11/atm-machine.git
$ cd atm-machine
```

* A continuació, prepareu les dependències del projecte amb:
```
$ mvn clean install
```

* Executeu el projecte mitjançant l'ordre:
```
$ mvn spring-boot:run
```

El projecte hauria d’iniciar-se al port 8080

### Com provar l'API
Podeu sol·licitar la distribució de diners mitjançant [Postman](https://www.getpostman.com)
```
http://localhost:8080/atm-simulator/dispense?amount=<AMOUNT>&accountNumber=<ACCOUNT_NUMBER>&pin=<PIN>
```

mentre \ <AMOUNT \> indica els diners que voleu dispensar del simulador de caixers automàtics, \ <ACCOUNTNUMBER \> és un identificador únic del vostre compte i \ <PIN \> indica la vostra clau secreta per accedir
<br/> La resposta hauria de ser semblant
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
#### Possible resposta de l'API
* Comproveu l'èxit del saldo (Aquesta opció mostra el saldo del caixer automàtic i NO del compte)
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

* Èxit al dispensar
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
* Saldo insuficient: el saldo total del compte d'usuari és inferior a l'import necessari
```
{
    "responseCode": "1",
    "responseDesc": "Insufficient balance - Actual balance: 80€",
    "responseStatus": "FAIL",
    "responseBody": []
}
```

* Saldo insuficient: el saldo total de l’ATM és inferior a la quantitat necessària
```
{
    "responseCode": "1",
    "responseDesc": "Remaining balance less than dispensed amount",
    "responseStatus": "FAIL",
    "responseBody": []
}
```

* Import no vàlid: l'import necessari és inferior al valor mínim de la nota a l'ATM (que, en aquest cas, és 5)
```
{
    "responseCode": "1",
    "responseDesc": "Amount less than min amount",
    "responseStatus": "FAIL",
    "responseBody": []
}
```

* Nota insuficient: el simulador ATM no té prou notes necessàries per l'import sol·licitat o el simulador no pot trobar la combinació adequada de notes per complir l'import sol·licitat
```
{
    "responseCode": "1",
    "responseDesc": "Insufficient note number. Try dispensing a different amount.",
    "responseStatus": "FAIL",
    "responseBody": []
}
```
