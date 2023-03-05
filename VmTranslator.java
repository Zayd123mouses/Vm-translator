import java.io.IOException;

/**
 * VmTranslator
 */

public class VmTranslator {

    public static void main(String[] args) throws IOException {

        String filePath = new Find("BasicTest.out").mainLook();
       CodeWrite codewiter =  new CodeWrite(filePath.replaceAll("BasicTest.out", "BasicTest.asm"));
        new Parser(filePath,codewiter);
     
    }
}