
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CodeWrite {
    private File file;
    private FileWriter fw;
    private PrintWriter pw;

    public CodeWrite(String filePath) throws IOException {
        file = new File(filePath);
         fw = new FileWriter(file);
         pw = new PrintWriter(fw);
    }

    public void writeAssembly(VmValue object){
      String code =  object.WriteAssembly();
      pw.println(code);
    }


}
