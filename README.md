# JEE Management System

A JEE management system where students can register and will be assigned a roll number. An admin will be responsible to fill out the marks of the students.

- This project is created in java and uses JavaFX for the GUI

![alt text](https://github.com/Pushpakant/JEE-Management-System/blob/master/preview.png?raw=true)

[![Watch the video](https://i.imgur.com/vKb2F1B.png)](https://github.com/Pushpakant/JEE-Management-System/blob/master/demo.gif?raw=true)

# How to get this project running

Follow the following steps to run this project :

1.  Run the following commands in terminal:

  - `$ git clone https://github.com/Pushpakant/JEE-Management-System`

  - `$ cd JEE-Management-System`

2.  If you are using IntelliJ IDEA, just open this project in IntelliJ IDEA and skip to step 5.

3.  If you are using some another IDE, then open the project in your IDE. Make sure your VM options are setup properly. In my case it was at:
  - `$ --module-path "D:\dev\java\sdkAndIde\javafx-sdk-11.0.2\lib" --add-modules javafx.controls,javafx.fxml`

4.  The libraries used in the project are under ./JEE-Management-System/.idea/libraries. Include all the libraries in your IDE project.

5.  Change the JDBC_URL to **"jdbc:sqlite:{YOUR_PATH_TO_PROJECT}/JEE-Management-System/src/res/db"** in the JEE-Management-System/src/sample/databaseConnection.java file.

6.  Run the project.

# Libraries and Plugins Used

1.  JavaFx (libralry)

2.  sqlite-jdbc-3.32.3.2.jar

3.  rs2xml.jar

4.  jfoenix-9.0.10.jar

5.  Scene Builder
