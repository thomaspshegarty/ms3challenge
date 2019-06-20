# CSV HANDLER CODING PROJECT

## Written by Tommy Hegarty for MS3

### Running the program

To run the program, execute the command:
> java -cp csv_handler.jar ms3challenge.MainHandler
in the directory that csv_handler.jar is stored.

### Approaching the Solution

Immediately after reading through the problem I saw that it had 2 primary parts: parsing information from the CSV into manageable data structures, and then transfering that information into the database. That meant I needed three primary classes: one to handle database interactions, one to handle CSV parsing, and one to hold the main method and orchestrate interactions between the two.