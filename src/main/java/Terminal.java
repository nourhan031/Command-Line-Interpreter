import java.io.*;
import java.util.*;
import java.io.File;
import java.io.IOException;



public class Terminal {
    Parser parser;
    String path_;//store the cwd path
    Vector<String> history;
    String[] cmd;

    Terminal() {
        this.history = new Vector<>();
        this.path_ = System.getProperty("user.dir");//setting the path_ var to the cwd
        this.parser = new Parser();
    }

    Terminal(Parser obj) {
        this.parser = obj;
        this.path_ = System.getProperty("user.dir");//setting the path_ var to the cwd
        this.parser = new Parser();
    }
    public void setHistory(String line){
        history.add(line);
    }

    public void setCmd() {
        cmd = Arrays.copyOf(parser.getArgs(), parser.getArgs().length);
    }

    public void printCmd() {
        for (int i = 0; i < cmd.length; i++) {
            System.out.println(cmd[i]);
        }
    }

    public void setParser(Parser obj) {
        parser = obj;
    }

    //////////////////////////////////////////////////////////////////////////////
    public void history() {
        for (int i = 0; i < history.size(); i++) {
            System.out.println((i + 1) + "  " + history.elementAt(i));
        }
    }

    public void echo() {
        String[] args = parser.getArgs();
        String output = String.join(" ", args);
        System.out.println(output);
        //parser.getArgs(): retrieves the arr of args entered by the user
        //[0] to access the 1st arg in the array

    }

    //NOTE:
    //it's a good practice to document potential exceptions in method signatures to ensure code maintainability and readability, even if they are not currently being thrown.
    public void pwd() throws IOException {
        System.out.println(path_);//prints the value stored in path_ which represents the cwd

    }

    public void touch() throws IOException {

    }

    public void cat() throws IOException {

    }



    public boolean rm() {
        return false;
    }

    public void cp() throws IOException {

    }

    public void mkdir() {
        history.add("mkdir");
        String[] args = parser.getArgs();//args contains the args provided by mkdir command, it could be a path or a dir name
        for (String arg : args) {
            if (arg.equals("mkdir")) {
                //System.out.println("Error: 'mkdir' is a reserved command and cannot be used as a directory name.");
                continue;
            } else {
                //iterate through each arg that was written after the mkdir command
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
    }
    public void cd() throws IOException {
        String[] args = parser.getArgs();

        if (args.length == 1) {
            // Case 1: No arguments, change to the home directory
            path_ = System.getProperty("user.home");
        }
        else if(args.length > 2 ){
            System.out.println("bash: cd: too many arguments");
        }
        else if (args.length == 2 && args[1].equals("..")) {
            // Case 2: Argument is "..", move to the previous directory
            File currentDirectory = new File(path_);
            String parentPath = currentDirectory.getParent();
            if (parentPath != null) {
                path_ = parentPath;
            } else {
                System.out.println("Already in the root directory.");
            }
        }
        else if (args.length == 2) {
            // Case 3: Argument is a new directory path
            File newDirectory = new File(args[1]);
            if (newDirectory.isAbsolute()) {
                // If it's an absolute path, set it directly
                path_ = newDirectory.getAbsolutePath();
            } else {
                // If it's a relative path, resolve it relative to the current directory
                path_ = new File(path_, args[1]).getAbsolutePath();
            }
            if (!newDirectory.exists() || !newDirectory.isDirectory()) {
                System.out.println("Directory does not exist: " + newDirectory.getAbsolutePath());
            }
        } else {
            System.out.println("Invalid 'cd' command. Usage: cd [directory]");
        }
        //this.path_ = "C:\\Users\\DELL.SXTO6";

    }

    public void rmdir(){

    }
    public void ls() throws IOException{
        File directory=new File(path_);
        String[] content = directory.list();
        if(cmd.length == 1){
            for (int i = 0; i < content.length; i++) {
                System.out.println(content[i]);
            }
        }
        else {
            if(cmd[1].equals("-r")){
                for (int i = content.length-1 ; i >= 0 ; i--) {
                    System.out.println(content[i]);
                }
            }
            else{
                System.out.println("ls: cannot access '" + cmd[1] + "' No such file or directory");
            }
        }

    }

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
                ls();
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
            case "history":
                history();
                break;

        }
    }
}
