package cli;

public class Parser {
    String commandName;
    String[] args ;
    
    public boolean parse(String input){
        if (input == null) {
            return false;
        }
        
        String[] words = input.split(" ");
        commandName = words[0];
        args = new String[words.length-1];
        for (int i = 1, j=0 ; i < words.length; i++,j++) {
            words[i] = words[i].trim();
            
            if (words[i].length() > 1) {
                args[j] = words[i]; 
            }
            
        }
        return true;
    }
    
    public String getCommandName() {
        return commandName;
    }
    
    public String[] getArgs() {
        return args;
    }
}
