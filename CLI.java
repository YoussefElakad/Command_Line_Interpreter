package cli;


import java.io.IOException;
import java.util.Scanner;


public class CLI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        
        
        Terminal terminal = new Terminal();
        System.out.println(terminal.currentFile.getPath());
        while (true) {
            Scanner in = new Scanner(System.in);
            String command = in.nextLine();

            if(terminal.parser.parse(command)){
                terminal.chooseCommandAction();
            }

        }
    }
}
