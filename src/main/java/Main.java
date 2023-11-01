import java.security.PublicKey;
import java.security.SignatureSpi;
import java.io.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
public class Main {
    public static void main(String[] args) throws IOException{
        Scanner scan = new Scanner(System.in);
        Terminal obj = new Terminal();
        while (true) {
            System.out.print("$");
            String cmd = scan.nextLine();
            Parser object = new Parser();
            if (object.parse(cmd)) {
                obj.setParser(object);
                obj.setCmd();
                obj.setHistory(cmd);
                obj.chooseCommandAction();
                System.out.println("-------------------------");
                //System.out.println("YES!");
            }
        }
    }
}
