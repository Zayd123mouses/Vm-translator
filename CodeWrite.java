
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CodeWrite {
    private File file;
    private FileWriter fw;
    private PrintWriter pw;
    private String FileName;

    public CodeWrite(String filePath,String filename) throws IOException {
        file = new File(filePath);
         fw = new FileWriter(file);
         pw = new PrintWriter(fw);
         FileName = filename;
    }
    public void writeAssembly(VmValue other) {
      String code = other.WriteAssembly(FileName);
      pw.println(code);
      
    }


    public void Close(){
      pw.close();
    }


}
