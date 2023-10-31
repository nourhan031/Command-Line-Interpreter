import java.io.File;
import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Scanner;
import javafx.application.Application;
public class Terminal {
    Parser parser;
    //File currentPath = new File(System.getProperty(""));
    File currentPath = new File("C:\\Users\\nourh\\IdeaProjects\\OS Assignments\\Assignment 1\\Command Line Interpreter");
    //public String pwd(){}
    //public void cd(String[] args){}
// ...

    //This method will choose the suitable command method to be called
    public void chooseCommandAction(String command, String[] args){
        switch(command){
            case "echo" :
                System.out.println(echo(args));
                break;
            case "pwd" :
                System.out.println(pwd());
                break;
            default:
                System.out.println("Command not recognized: " + command);

        }
    }
    //////////////////////////////////////////////////////////////////////////////
    public String echo(String[] args){
    return String.join(" ",args);
    }
    //////////////////////////////////////////////////////////////////////////////
    public String pwd(){
        return currentPath.getAbsolutePath();
    }
    //////////////////////////////////////////////////////////////////////////////


    //////////////////////////////////////////////////////////////////////////////
    public void exit(){System.exit(0);}



}
