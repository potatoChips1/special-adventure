# Calculator Assignment

This is my solution to the calculator assignment. 
1. The string input is parsed into recognizable tokens. 
2. The parsed tokens are then converted into an abstract syntax tree. 
3. The tree is evaluating starting from the left most leaf node. 

ie. add(2, 3) would result in:
```
      add
    /     \
  2        3
```
 
ie. let(a, 1, add(a, a)) would result in:
```
      let
    /  |   \
  a    1     add
  		    /    \
  	       a	  a
```


## Getting Started

### Installing
1. Navigate to the directory where the pom.xml file is.

2. Run the following command: 
```
java -jar target/calculatorassignment-1.0-SNAPSHOT-jar-with-dependencies.jar
```

3. In case step 2 does not work, run the following and repeat step 2:
```
mvn clean compile assembly:single
```

End with an example of getting some data out of the system or using it for a little demo

## Running the tests

```
mvn test
```
## Built With
* [Maven](https://maven.apache.org/) - Dependency Management