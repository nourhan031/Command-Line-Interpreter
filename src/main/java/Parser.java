import java.io.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
public class Parser {
    String commandName;//parsed command name
    String[] args;//parsed args
    String[] commands = {"echo", "pwd", "ls", "ls-r","cd","mkdir","cat","exit","touch","rm","cp","cp-r","mkdir","rmdir","history"};
    public boolean parse(String input){//method to parse user input
        input = input.trim();
        commandName = input.split("\\s+")[0];//set to the 1st word in the input split by space
        int i;
        for (i = 0;i < commands.length;i++){
            //iterate through commands arr to check if the commandName matches any of the predefined ones
            if(commands[i].equals(commandName)){
                //if a match is found, it splits the input into words and stores them in the args array
                args = input.split("\\s+");
                break;
            }
        }

        try {
            if (i == commands.length) {
                throw new IOException();
                //return true;
            }
        }
        //if the commandName isnt found in the commansd array, it throws an IOException and returns false indicating that the input is invalid
        catch (IOException e){
            System.out.println("invalid input");
            return false;
        }
        return true;
    }

    public String getCommandName(){//method to retrieve the parsed commandName after calling the parse method
        return commandName;
    }
    public String[] getArgs(){//method that retrieves the parsed args after calling the parse method
        return args;
    }



}
