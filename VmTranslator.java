import java.io.IOException;

/**
 * VmTranslator
 */

public class VmTranslator {

    public static void main(String[] args) throws IOException {

        String filePath = new Find(args[0]).mainLook();
        CodeWrite codewiter =  new CodeWrite(filePath+"\\" + args[0].replaceAll("vm", "asm") ,args[0]);
        new Parser(filePath+ "\\"+ args[0],codewiter);
        System.out.println(filePath);

        System.out.println("Translator done successfully");
     
    }
}