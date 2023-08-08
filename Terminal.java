package cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Scanner;


public class Terminal {
    
    Parser parser;
   
    String current_path = System.getProperty("user.dir");
    String home_path = System.getProperty("user.home");
    
    File currentFile , homeFile;
   
    public Terminal(){
        parser = new Parser();
        currentFile = new File(current_path);
        homeFile = new File(home_path);
   
    }
    
   
    
    public void clear(){
        for (int i = 0; i < 300*80; ++i) System.out.println();
    }
    
    public String pwd() { /// 2
        
        if (parser.args.length != 0) {
            return "Invalid parameters, pwd takes no parameters";
        }
        else
            return currentFile.getPath();
    }
    
   
    public void cd(String[] args) { /// 3
        File pre_file;
        switch (args.length) {
            case 0:
                currentFile = homeFile;
                break;
            case 1:
                if(args[0].contains("..")){
                    String[] paths = currentFile.getPath().split("\\\\");
                    String pre_path = "";
                    for(int i=0; i<paths.length-1; i++){
                        if(i == paths.length-2)
                            pre_path += paths[i];
                        else
                            pre_path += paths[i] + "\\";
                    }
                    pre_file = new File(pre_path);
                    currentFile = pre_file;
                    
                }
                else{
                    
                    pre_file = new File(args[0]);
                    if(pre_file.isDirectory()){
                        currentFile = pre_file;
                    }
                    else
                        System.out.println("Invalid Path");
                    
                }   break;
            default:
                System.out.println("Error... Invalid Arguments");
                break;
        }
        
    }
    
    public void ls(){ /// 4
        String[] l_items = currentFile.list();
        for (String item : l_items)
            System.out.println(item);
    }
   
    
    public boolean mkdir(String path) { /// 6
        String[] test = path.split("\\\\");
        File file = null;
        if(test.length == 1){
            file = new File(currentFile.getPath()+"\\" + path);
        }
        
        if(test.length >1){
            file = new File(path);
            
        }
        return file.mkdir();
    }
    
    public Boolean rmdir(String path){
        String[] test = path.split("\\\\");
        File file = null;
        if(test.length == 1){
            file = new File(currentFile.getPath()+"\\" + path);
        }
        
        if(test.length >1){
            file = new File(path);
            
        }
        if(file.length() == 0)
            return file.delete();      
        else
            return false;
    }
    
    public void CP(String name1 , String name2) throws IOException{
                InputStream inStream = null;
		OutputStream outStream = null;
	    	try{
	    	    File afile =new File(name1);
	    	    File bfile =new File(name2);
	    	    inStream = new FileInputStream(afile);
	    	    outStream = new FileOutputStream(bfile);
	    	    byte[] buffer = new byte[1024];
	    	    int length;
	    	    while ((length = inStream.read(buffer)) > 0){
	    	    	outStream.write(buffer, 0, length);
	    	    }
	    	    inStream.close();
	    	    outStream.close();
	    	    System.out.println("File is copied successfully!");
	    	}catch(IOException e){
	    		e.printStackTrace();
	    	}
    }

    public void rm(String name) throws IOException {

        Path path = Paths.get(name);
        Files.delete(path);
    }

    public void cat(String name) throws IOException {
        Path path = Paths.get(name);
        byte[] output1 = Files.readAllBytes(path);
        System.out.println(new String(output1));
    }

    public void cat(String fname, String sname) throws IOException {
        Path path = Paths.get(fname);
        byte[] output1 = Files.readAllBytes(path);
        System.out.println(new String(output1));
        Path path2 = Paths.get(sname);
        byte[] output2 = Files.readAllBytes(path2);
        System.out.println(new String(output2));
    }
    
   public String date() {
        Date date=java.util.Calendar.getInstance().getTime();
        System.out.println(date);

        return date.toString();
    }
   
   public File help() {
        File file = null;
        try {
            file = new File("help.txt");
            Scanner read = new Scanner(file);

            while (read.hasNextLine()) {
                String data = read.nextLine();
                System.out.println(data);
            }

            read.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
        return file;
    }
   
    public void cpf(String sourcePath, String destinationPath) throws IOException {
        String source = sourcePath;
        String dest = destinationPath;

        int lengthOfPath = source.split("\\\\").length;
        String lastFileName = source.split("\\\\")[lengthOfPath-1];
        String outputPath = dest + "\\" + lastFileName;

        File isSrc = new File(source);
        File isDest = new File(dest);

        if(isSrc.isDirectory()){
            Path path = Paths.get(outputPath);
            Files.createDirectories(path);
        }
        else if(isSrc.exists() && isDest.exists()){
            File file = new File(source);
            FileInputStream fin =  new FileInputStream(file);

            byte fileContent[] = new byte[(int)file.length()];

            fin.read(fileContent);

            FileOutputStream fos = new FileOutputStream(outputPath);
            fos.write(fileContent);

            fin.close();
            fos.close();
        }
        else{
            System.out.println("File not found");
        }
    }
   public void mv(String sourcePath, String destinationPath) throws IOException {
        
        File dest = new File(destinationPath);
        if(dest.isDirectory()){
            cpf(sourcePath, destinationPath);
            rm(sourcePath);
        }
        else{
            File src = new File(sourcePath);
            File newName = new File(destinationPath);
            src.renameTo(newName);
        }
    }
    
   public void more(String path) throws IOException {
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(path));
        String line = reader.readLine();
        Scanner scan = new Scanner(System.in);

        for (int i = 0; i < 18; i++) {
            System.out.println(line);
            line = reader.readLine();
        }

        String input = scan.nextLine();
        while (line != null && input.isEmpty()) {
            System.out.println(line);
            line = reader.readLine();
            input = scan.nextLine();
        }
    }

   //This method will choose the suitable command method to be called
    public void chooseCommandAction() throws IOException{
        
        switch(parser.commandName){ 
            case "date":
                date();
                break;
            
            case "help":
                help();
                break;
                
            case "cd": 
                this.cd(parser.args);
                System.out.println(currentFile.getPath());
                break;
                
            case "pwd":
                System.out.println(this.pwd());
                break;
                
            case "ls":
                ls();
                break;
                
            case "mkdir":
                for (String arg : parser.args) {
                    if (!mkdir(arg)) {
                        System.out.println("Error");
                    }
                }
                System.out.println("Folder Created");
                break;
                
            case "rmdir":
                for (String arg : parser.args) {
                    if (!rmdir(arg)) {
                        System.out.println("Error");
                    }
                }
                System.out.println("Folder Removed");
                break;
            
            case "cp":
                if (parser.args.length != 0) {
                    
                    CP(parser.args[0],parser.args[1]);
                    
                }
                
                break;
                
            case "rm":
                 if (parser.args.length != 1) 
                {
                    System.out.println("Invalid parameters, rm takes 1 parameter");
                }
                else
                 {
                rm(parser.args[0]);
                System.out.println("File Succesfully Deleted");
                 }
                break;
            
            case "more":
                 if (parser.args.length != 1) 
                {
                    System.out.println("Invalid parameters, more takes 1 parameter");
                }
                else
                 {
                more(parser.args[0]);
                 }
                break;
                
            case "cat":
                if (parser.args.length == 1) {
                    cat(parser.args[0]);

                } else if (parser.args.length == 2) {
                    cat(parser.args[0], parser.args[1]);
                } else {
                    System.out.println("Invalid parameters, cat takes 1 or 2 parameters");
                }
                break;
                
            case "clear":
                clear();
                break;
                
            case "mv":
                if (parser.args.length != 2) 
                {
                    System.out.println("Invalid parameters, mv takes 2 parameters");
                }
                else
                {
                mv(parser.args[0],parser.args[1]);
                System.out.println("File Succesfully Moved");
                }
                break;
                
            case "exit":
                System.exit(0);
                break;
            default:
                System.out.println("Invalid cmd");
        }
    }
    
    
    
    
}
