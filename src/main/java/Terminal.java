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
    public void mkdir(){
        String[] args = parser.getArgs();//args contains the args provided by mkdir command, it could be a path or a dir name
        for (String arg : args) {//iterate through each arg that was written after the mkdir command
            File newDir;
            //checking if arg cont path separator (backslash)
            if (arg.contains(File.separator)) {
                // If the argument contains a path separator, treat it as a full path.
                newDir = new File(arg);
            } else {
                // Otherwise, it's just a directory name, create it in the current directory.
                newDir = new File(path_ + File.separator + arg);
            }

            if (!newDir.exists()) {//check if the dir already exists
                newDir.mkdirs();//if it doesnt, create a one
                System.out.println("Directory created: " + newDir.getAbsolutePath());
                //getAbsolutePath(): returns the full path to the file, including the directory hierarchy leading to that file.
            } else {
                System.out.println("Directory already exists: " + newDir.getAbsolutePath());
            }
        }
    }


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
