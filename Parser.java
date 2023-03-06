
import java.io.File;
import java.util.Scanner;


public class Parser {
  private File file;
  private String CurrentCommand;
  private VmValue CommandType;
  private CodeWrite codeWriter;

    public Parser(String filePath, CodeWrite codewriter) {
        file = new File(filePath);
        codeWriter = codewriter;
        advance();
    }


    private boolean hasMoreCommands(Scanner scanner){
        return scanner.hasNextLine();
    }
  
    private void advance() {
        try( Scanner scanner = new Scanner(file)){
            while (hasMoreCommands(scanner)) {
                String temp = scanner.nextLine();
                if(isComment(temp)){ // if the line starts with "//" ignore it
                    continue;
                }
                temp = removeWhitespaceAndComment(temp); // remove eading spaces and comments inline
                CurrentCommand = temp;
                CommandType(); // decide what type of command based on the first word
                
                codeWriter.writeAssembly(CommandType);
                

            }
            codeWriter.Close();

        }catch (Exception e){
            System.out.println("ERROR: "+ e);
        }
        
    }

    private void CommandType() {
        String[] CurrentCommandElements = CurrentCommand.split(" ");
        switch (CurrentCommandElements[0]) {
            case "push":
                CommandType = new Push(CurrentCommand);
                break;

            case "pop":
                CommandType = new Pop(CurrentCommand);
                break;
        
            default:
                CommandType = new ARITHMETIC(CurrentCommand); 
                break;
        }  
        
    }
    
    private boolean isComment(String line) {
        if(line.isEmpty() || line.charAt(0) == '/' ) {
            return true;
        }else{
            return false;
        }
        
    }


    private String removeWhitespaceAndComment(String line) {
        return line.split("//")[0].trim();
        
    }


    
}
