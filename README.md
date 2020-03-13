Game application
======================

Players are configured in application.yml files for demonstration purposes.

-----------

## Supported API
| Method  | Path             | Description |
| :------:| :---------------:| :----------:| 
| GET     | /game            | Get state of the game |                                               
| PUT     | /game            | Updates the state of the game (plays next round) 
| PUT     | /game/reset      | Resets the game to the original state

## Running

* In order to run the backend part of the application you should have JRE 8+ installed on your machine and maven.

$ cd bol-assignment

$ mvn clean package

$ java -jar ./target/bol-assignment-1.0.jar

* Install Angular CLI to be able to run frontend part of the application and run the following commands:

$ cd bol-assignment/src/main/js/gameclient

$ ng server --open