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
    public void printHistory() {
        for (int i = 0; i < history.size(); i++) {
            System.out.println((i + 1) + "  " + history.elementAt(i));
        }
    }

    public void echo() {
        String[] args = parser.getArgs();
        String output = String.join(" ", args);
        System.out.println(output);
        history.add("echo");
        //parser.getArgs(): retrieves the arr of args entered by the user
        //[0] to access the 1st arg in the array

    }

    //NOTE:
    //it's a good practice to document potential exceptions in method signatures to ensure code maintainability and readability, even if they are not currently being thrown.
    public void pwd() throws IOException {
        System.out.println(path_);//prints the value stored in path_ which represents the cwd
        history.add("pwd");
    }

    public void touch() throws IOException {
        history.add("touch");
    }

    public void cat() throws IOException {
        history.add("cat");
    }

    public void cd() throws IOException {
        this.path_ = "C:\\Users\\DELL.SXTO6";
        history.add("cd");
    }

    public boolean rm() {
        history.add("rm");
        return false;
    }

    public void cp() throws IOException {
        history.add("cp");
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

    public void rmdir(){
        history.add("rmdir");
    }

    public void ls() throws IOException{
        File directory = new File(path_);
        File [] content = directory.listFiles();
        int size = content.length;
        String args[] = new String[size];
        int i=0;
        for (File object : content){
            args[i] = object.getName();
            i++;
        }
        Arrays.sort(args);
        for(int j =0; j<size ; j++){
            System.out.println(args[j]);
        }
        history.add("ls");
    }
    public void ls_r() throws IOException{
        File directory = new File(path_);
        File [] content = directory.listFiles();
        int size = content.length;
        String args[] = new String[size];
        int i=0;
        for (File object : content){
            args[i] = object.getName();
            i++;
        }
        Arrays.sort(args);
        for(int j=size-1 ;j>=0 ; j--){
            System.out.println(args[j]);
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
            case "ls -r":
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
            case "history":
                printHistory();
                break;

        }
    }




}
