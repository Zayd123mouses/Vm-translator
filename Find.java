import java.io.*;

public class Find 
{
     String answer;
     String filename;

    public  Find(String fname) {
        filename = fname;

    }

    public   void  findFile(String name,File file)
    {
        File[] list = file.listFiles();
        if(list!=null){
        for (File fil : list)
        {
            if (fil.isDirectory())
            {System.out.println(fil);
                findFile(name,fil);

            }
            else if (name.equalsIgnoreCase(fil.getName()))
            {
                 answer = "" + fil.getParentFile();
                 System.out.println(answer + "//////////////////////////////////");

                 System.out.println(fil.getParentFile());
            }
        }
    }
   
        
    }

    public  String mainLook() 
    {        
        System.out.println(new File("./nand2tetris/").getAbsolutePath());
        findFile(filename,new File("../nand2tetris/"));
        return answer;
    }
}
