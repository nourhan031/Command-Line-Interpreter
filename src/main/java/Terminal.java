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
    public void pwd() throws IOException {
        System.out.println(path_);//prints the value stored in path_ which represents the cwd

    }

    public void touch() throws IOException {
        String path = parser.getArgs()[0];
        char backslash = '\\';
        String name = "";

        int i, j;
        for (i = path.length() - 1; i >= 0; i--) {
            if(path.charAt(i) == backslash){
                break;
            }
            name = path.charAt(i) + name;
        }
        String newPath="";
        for(j = 0;j < i;j++){
            newPath += path.charAt(j);
        }


        if(path.charAt(1) == ':') {
            System.setProperty("user.dir",newPath);

            File newFile = new File(newPath+"\\"+name);
            newFile.createNewFile();
        }
        else{
            File newFile = new File(path_ +"\\"+newPath+"\\"+name);
            newFile.createNewFile();
        }
    }

    public void cat() throws IOException {

        if(cmd.length >= 2){
            String filePath = path_+"\\"+cmd[1];
            File file = new File(filePath);
            if (file.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("cat: "+cmd[1]+": No such file or directory");
            }
            if(cmd.length >= 3){
                filePath = path_+"\\"+cmd[2];
                file = new File(filePath);
                if (file.exists()) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    System.out.println("cat: "+cmd[2]+": No such file or directory");
                }
            }
            if(cmd.length > 3){
                System.out.println("cat: only can concatenate 2 arguments");
            }
        }
    }



    public boolean rm() {
        String[] args = parser.getArgs();
        if (args.length < 2) {
            System.out.println("Usage: rm [file1] [file2] ...");
            return false;
        }

        for (int i = 1; i < args.length; i++) {
            String filePath = path_ + File.separator + args[i];
            File file = new File(filePath);
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("Removed: " + file.getAbsolutePath());
                } else {
                    System.out.println("Failed to remove: " + file.getAbsolutePath());
                }
            } else {
                System.out.println("File not found: " + file.getAbsolutePath());
            }
        }
        return true;
        }

    public void cp() throws IOException {
        // Verify that parser.getArgs() contains at least two arguments
        String[] args = parser.getArgs();
        if (args.length < 2) {
            System.err.println("Usage: <source_file> <destination_file>");
            return;
        }

        String sourceFileName = args[1];
        String destinationFileName = args[2];
        // Combine the path and file names to create file objects
        File sourceFile = new File(path_, sourceFileName);
        File destinationFile = new File(path_, destinationFileName);

        // Check if the source file exists
        if (!sourceFile.exists()) {
            System.err.println("Source file does not exist: " + sourceFile.getAbsolutePath());
            return;
        }
        // Check if the destination file already exists
        if (destinationFile.exists()) {
            System.err.println("Destination file already exists: " + destinationFile.getAbsolutePath());
            return;
        }

        try (Scanner reader = new Scanner(sourceFile);
             FileWriter writer = new FileWriter(destinationFile)) {

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                writer.write(line);
                writer.write("\n");
            }
            System.out.println("File copied successfully.");
        } catch (IOException e) {
            System.err.println("Error copying the file: " + e.getMessage());
        }
    }

    public void wc(){
        String filePath = path_+"\\"+cmd[1];
        File file = new File(filePath);
        int lineCount = 0;
        int charCount = 0;
        int wordCount = 0;
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    // Count lines
                    lineCount++;

                    // Count characters (including spaces)
                    charCount += line.length();

                    // Count words (split by spaces)
                    String[] words = line.split("\\s+");
                    wordCount += words.length;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(lineCount + " " + wordCount + " " + charCount + " " + cmd[1]);
        }
        else
        {
            System.out.println("cat: "+cmd[1]+": No such file");
        }
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
        File currentDir = new File(path_);
        File[] subDirs = currentDir.listFiles(File::isDirectory);

        if (subDirs != null) {
            for (File subDir : subDirs) {
                if (subDir.list() != null && subDir.list().length == 0) {
                    // Check if it's a directory and it's empty
                    subDir.delete(); // Delete the empty directory
                }
            }
        }
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
            case "ls -r":
                ls();
                break;
            case "cd":
                cd();
                break;
            case "wc":
                wc();
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
