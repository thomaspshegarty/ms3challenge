# CSV HANDLER CODING PROJECT

Coded by Tommy Hegarty

### Running the program

To run the program, execute the command:  
`java -cp csv_handler.jar ms3challenge.MainHandler`  
in the directory that csv_handler.jar is stored.

### Approaching the Solution

Immediately after reading through the problem I saw that it had 2 primary parts: parsing information from the CSV into manageable data structures, and then transfering that information into the database. That meant I needed three primary classes: one to handle database interactions, one to handle CSV parsing, and one to hold the main method and orchestrate interactions between the two. I tend to take this approach with most problems, trying to break them down into as many smaller parts as possible -- it's easier to manage if you can assemble each working part separately instead of getting lost in a massive mountain of code.

The description mentioned that the data sets can be extremely large, so efficiency was important. I decided that it was a better idea for me to process the entire CSV first, then send the processed information as a lump data structure to the database handler. I'm not certain of the exact efficiency tradeoffs as SQLite in Java is fairly new to me, but it seemed more efficient than the alternative method of parsing an entry, adding it to the database, then going back to parse another entry.

Finally, as I wrapped up the CSV and DB handling and integrated them, I realized I had left the csv loading process hardcoded in. Because this was something requested by a theoretical customer it seemed important to make it as accessible as I could in the time I was given. Thus I added a third handler, InterfaceHandler, to take care of displaying a very rudimentary file selection window and passing the user's selection to the CSV to process.