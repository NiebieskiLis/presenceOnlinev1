# presenceOnlinev1

This project is a software that helps to manage workers shifts in company. It can helps accountants to. It keeps the data about two kinds of employees - full time worker and a part time one.

## Getting Started

Following insructions are going to help you with running code

### Prerequisites
#### Database
To start working with the software you need to start the derby database
go to the folder  database> derby > lib. Open there cmd and type the following code :  

```
java -jar derbyrun.jar server start
```
If you want to get into the database you need to open a new terminal in the same and then paste :
```
java -cp "derbyclient.jar;derbytools.jar" org.apache.derby.tools.ij
```

and then connect to database:
```
connect 'jdbc:derby://localhost:1527/BazaPracownikow;create=true';
```

## Running the tests

All of the test are in the folder src/test/java/

You have them grouped as follows:

Data Load Tests

Interface Work Tests

## Versioning

 For the versions available, see the [tags on this repository](https://github.com/NiebieskiLis/presenceOnlinev1/tags). 

## Authors

* **Aleksandra Rezetka** - *Initial work* - [NiebieskiLis](https://github.com/NiebieskiLis)
* **Maciej Adamczyk** - *Initial work* - [Jablek98](https://github.com/Jablek98)


