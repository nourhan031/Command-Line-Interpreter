import java.io.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
public class Terminal {
    Parser parser;
    String path_;//store the cwd path
    public void setParser(Parser obj){
        parser = obj;
        path_=System.getProperty("user.dir");//setting the path_ var to the cwd
    }
    //////////////////////////////////////////////////////////////////////////////
    public void echo(){
        String[] args = parser.getArgs();
        String output = String.join(" ",args);
        System.out.println(output);
        //parser.getArgs(): retrieves the arr of args entered by the user
        //[0] to access the 1st arg in the array
    }
    //NOTE:
    //it's a good practice to document potential exceptions in method signatures to ensure code maintainability and readability, even if they are not currently being thrown.
    public void pwd() throws IOException {
        System.out.println(path_);//prints the value stored in path_ which represents the cwd
    }
    public void touch() throws IOException {}
    public void cat() throws IOException{}
    public void cd() throws IOException {}
    public boolean rm(){
        return false;
    }
    public void cp() throws IOException {}
    public void mkdir(){}
    public void rmdir(){}
    public void ls() throws IOException{}
    public void ls_r() throws IOException{}
    public void exit(){
        System.exit(0);
    }
    //////////////////////////////////////////////////////////////////////////////
    //This method will choose the suitable command method to be called
    public void chooseCommandAction() throws IOException {
        switch(parser.getCommandName()){
            case "echo":
                echo();
                break;
            case "exit":
                //System.out.println("You left!");
                exit();
                break;
            case "pwd":
                pwd();
                break;
            case "ls":
                ls();
                break;
            case "ls-r":
                ls_r();
                break;
            case "cd":
                cd();
                break;
            case "cat":
                cat();
                break;
            case "touch":
                touch();
                break;
            case "rm":
                rm();
                break;
            case "cp":
                cp();
                break;
            case "rmdir":
                rmdir();
                break;
            case "mkdir":
                mkdir();
                break;
        }
    }




}
