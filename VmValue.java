 public interface VmValue {
    Pair getArguments();
    String WriteAssembly();
        
}


class Push implements VmValue{
    private String Arg1;
    private String Arg2;
    private Pair Arguments;

  public static void main(String[] args) {
    new Push("push local 5").WriteAssembly();
  }
    public Push(String command){
        String[] commandElements = command.split(" ");
        Arg1 = commandElements[1];
        Arg2 = commandElements[2];
        CreateArguments();
    }

//    ---------------------------------------------

    public String WriteAssembly(){
        System.out.println(Arg1);
    switch (Arg1) {
        case "constant":
            return handleConstant();
            
        case "static":
         return "";

        case "pointer":
        return "";

        case "temp":
        return "";

        default:
        return handleLocal_Argument_this_that();

        
            
    }
   
 
    }
//    ---------------------------------------------

    private String handleConstant() {
        String code = String.format("//write push constant %s\n@SP\nA=M\nM=%s\n@SP\nM=M+1\n",Arg2,Arg2); 
        System.out.print(code);
        return code;
    }

    private String handleLocal_Argument_this_that(){
        String symbol;
        switch (Arg1) {
            case "local":
                symbol = "LCL";
                break;
            case "argument":
                symbol = "ARG";
                break;
            
            default:
                symbol = Arg1.toUpperCase();
                break;
        }
        String code = String.format("//write push %s %s\n@%s\nD=A\n@%s\nD=D+M\nA=D\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n",Arg1,Arg2,Arg2,symbol);
        System.out.println(code);
        return code;
        
    }

    private String arg1() {
        return Arg1;
    }

    private int arg2(){
        return Integer.valueOf(Arg2);
    }

    public Pair getArguments() {
        return Arguments;
        
    }

    
    private void CreateArguments() {
        Arguments =   new Pair(arg1(),arg2());
    }
}


class Pop implements VmValue{
    private String Arg1;
    private String Arg2;
    private Pair Arguments;

    public Pop(String command){
        String[] commandElements = command.split(" ");
        Arg1 = commandElements[1];
        Arg2 = commandElements[2];
        CreateArguments(); 
    }


    public String WriteAssembly(){
        return "";
   }

    private String arg1() {
        return Arg1;
    }

    private int arg2(){
        return Integer.valueOf(Arg2);
    }

    public Pair getArguments() {
        return Arguments;
        
    }
    private void CreateArguments() {
        Arguments =  new Pair(arg1(),arg2());
    }
}



class ARITHMETIC implements VmValue{
    private String Arg1;
    private Pair Arguments;

    public ARITHMETIC(String command){
        Arg1 = command;
        CreateArguments();
    }


    public String WriteAssembly(){
        return "";
   }

    private String arg1() {
        return Arg1;
    }
   


    public Pair getArguments() {
        return Arguments;
        
    }

    private void CreateArguments() {
        Arguments = new Pair(arg1(),null);
    }
    
}
