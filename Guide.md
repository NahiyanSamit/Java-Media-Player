# Guide
## ⚠️ Make sure you have Java installed and and JavaFX configured in your Computer
Also set %PATH_TO_FX% and %PATH_TO_FX_JMOD% environment variables   

## Set variable path

#### Set %PATH_TO_FX% 
```bash
set PATH_TO_FX="path\to\javafx-sdk-21.0.2\lib"
``` 

#### Set %PATH_TO_FX_JMOD%
```bash
set PATH_TO_FX_MODS="path\to\javafx-jmods-21.0.2"
``` 

## Compile java code 
First compile the java code using the following command
```bash
javac --module-path %PATH_TO_FX% --add-modules=javafx.controls,javafx.fxml src\main\java\com\player\javamusicplayergradle\*.java -d classes
``` 


## Copy extra files to the classes directory
Copy Ui files to the classes directory
```bash
Xcopy /E src\main\resources classes
``` 


## Create a folder to save the generated jar file
Create a folder where the generated jar file will be saved
```bash
md Application
```


## create jar file
create the jar file for your application
```bash
jar --create --file=Application/MediaPlayer.jar --main-class=com.player.javamusicplayergradle.Application -C classes .
``` 


## Run the jar file
Run the command to run the application
```bash
java --module-path %PATH_TO_FX% --add-modules=javafx.controls,javafx.fxml -jar Application\MediaPlayer.jar
``` 



# Extra

### Create custom jre for the application
Create a custom jre which includes all the modules to run the application 
```bash
jlink --module-path ../jmods;%PATH_TO_FX_JMOD% --add-modules java.base,javafx.base,javafx.controls,javafx.fxml,javafx.media,javafx.graphics --output jreFX
``` 


## Run the Application by custom jre
Use the command to run the application using custom JRE you created
```bash
jreFX\bin\java -jar Application/Mediaplayer.jar
``` 


## Create a batch file to run a Application
Use this command to create a batch file which runs the application

⚠️ Don't forget to press CTRL + z after write the following command line 
```bash
copy con MediaPlayer.bat
jreFX\bin\java -jar Application/Mediaplayer.jar
``` 


### Create a vbs file to run the Application
Use this command to create a vbs file which runs the application without showing the cmd when the application is running

⚠️ Don't forget to press CTRL + z after write the following command line
```bash
copy con MediaPlayer.vbs
Set oShell = CreateObject ("Wscript.shell")
Dim strArgs
strArgs = "cmd /c MediaPlayer.bat"
oShell.Run strArgs, 0, false
``` 




