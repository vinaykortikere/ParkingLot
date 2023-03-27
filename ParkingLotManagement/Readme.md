Project Contains the Implementation for a Simple Park Lot Management System

Java Sources are divided into 3 Packages:

com.plm.app: This package contains the Main ParkingLot application class
com.plm.data: This package contains the Data Model classes represting the Vehciles
com.plm.constants: This package contains the Constants used in the application in the enums format

Client Code to test the Parking Lot Application is present in Main.java in the package com.pl.clients

The test data is test/data folder and contains json files for inputing the the 4 use cases mentioned in the problem statement

The config files for the ParkingLot application are present in the test/config folder and has the configs to test the 4 use cases mentioned in the problem statement

The client class can be tested by running the Main.java class in the package com.plm.clients

`java com.plm.clients.Main <config file path> <data file path>`

for example java com.plm.clients.Main test/configs/AirportParking.json test/data/AirportParkingtest.json

The output of the client code is printed on the console

If you want to specify live data in the console, you can use the following command

`java com.plm.clients.Main <config file path>`

for example java com.plm.clients.Main test/configs/AirportParking.json

and the data can entered via console.

