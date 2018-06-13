# My-Vending-Machine

Design and code a vending machine that satisfies the following diagram. But also take considerations for additional features that may come in the future. We are looking for software developers that not only satisfy the requirements at hand but also plan for changes in the future. 


## Achitecture

### MVP pattern. 
#### Model layer
All business logics were wrapped in bellow pojo Java models classes:
```
VendingMachine - provides all APIs to access VandingMachine from View layer.
1. resetMachine - resets vending machine to default
2. insertCoin - inserts coin into slot and returns running total amount 
3. KeyInId - keys in the itemId want to buy
4. confirmSelection - confirms the purchasing
5. abortPurchasing - Aborts purchasing and returns coind in slot
6. getInventory - returns all inventory number and get ready to render the view layer
7. getPurchaseHistory - returns all transactions. It can be used for summary reporting

Here is the interface which defines all methods:

public interface IVendingMachine {
    void setInventoryListener(VendingMachine.InventorListener listener);
    void resetMachine();
    int insertCoin(AcceptedCoins coin);
    boolean keyInId(String itemId);
    int confirmSelection() throws NoSufficientFundsException, NoEnoughItemsException;
    int abortPurchasing();
    List<ItemInventory> getInventory();
    boolean isItemSelected();
    List<PurchaseHistory> getPurchaseHistory();
    }

```
### Prerequisites

What things you need to install the software and how to install them

```
Give examples
```

### Installing

A step by step series of examples that tell you how to get a development env running

Say what the step will be

```
Give the example
```

And repeat

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo

## Running the tests

Explain how to run the automated tests for this system

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Billie Thompson** - *Initial work* - [PurpleBooth](https://github.com/PurpleBooth)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc
