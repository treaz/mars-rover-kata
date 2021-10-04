# mars-rover-kata

## Running the code locally

### Prerequisites

* Java 1.8 or higher
* maven 3

### Running tests

``mvn clean test``

### Running the application

``mvn compile exec:java -Dexec.mainClass="com.horiaconstantin.kata.marsrover.Rover" -Dexec.args="1 2 NORTH FLB"``

NOTE: the application expects that all arguments are passed in properly. There's no exception handling for using the app
via the above command.

Change the -Dexec.args as need:

* first argument is initial x coordinate. In the example above, x=1
* second argument is initial y coordinate. In the example above, y=2
* third argument is initial direction. In the example above, NORTH.
* fourth argument is the movement command sequence. In the example above, FLB.

Version: 1.4, Last updated: 2021-02-02
