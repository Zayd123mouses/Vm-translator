abstract public class  VmValue {
   public String POP_SP_to_D = "@SP\nM=M-1\nA=M\nD=M\n";
   public String starSP_equal_D_AND_increase_sp = "@SP\nA=M\nM=D\n@SP\nM=M+1\n";

   abstract Pair getArguments();
   abstract  String WriteAssembly(String FileName);
   abstract int arg2();
    
   
   
   public String getFilename(String filename) 
   {
       return filename.split("\\.")[0] + "." + arg2();
   }
       
   String ThisOrThat() {
    if(Integer.valueOf(arg2()) == 0){
        return "THIS";
    }else{
        return "THAT";
    }
   }     

}


class Push extends VmValue{
    private String Arg1;
    private String Arg2;
    private Pair Arguments;

  public static void main(String[] args) {
    new Push("push constant 17").WriteAssembly("Foo.out");
  }
    public Push(String command){
        String[] commandElements = command.split(" ");
        Arg1 = commandElements[1];
        Arg2 = commandElements[2];
        CreateArguments();
    }

//    ---------------------------------------------

    public String WriteAssembly(String FileName){
        System.out.println(Arg1);
    switch (Arg1) {
        case "constant":
            return handleConstant();
            
        case "static":
         return handleStatic(FileName);

        case "pointer":
        return handlePointer();

        case "temp":
        return handleTemp();

        default:
        return handleLocal_Argument_this_that();       
            
    }
   
 
    }
//    ---------------------------------------------
    
// push static 5
// arg1 = static
// arg2 = 5

   private String handlePointer(){
    String rightSegment = ThisOrThat();
    String code = String.format("//write push pointer %s\n@%s\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n",
                                 Arg2, rightSegment);
    System.out.println(code);
    return code;

    
   }

    


   private String handleTemp() {
    String code = String.format("//write push Temp %s\n@%s\nD=A\n@%s\nD=D+A\nA=D\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n",
                                Arg2,Arg2,"5");
    System.out.println(code);
    return code;
    
   }


    private String handleStatic(String filename){

        String code = String.format("// write push static %s\n@%s\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n"
                                    ,Arg2,getFilename(filename));
        System.out.print(code);
        return code;
    }

   

    private String handleConstant() {
        String code = String.format("//write push constant %s\n@%s\nD=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n",Arg2,Arg2); 
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

    public String arg1() {
        return Arg1;
    }

    public int arg2(){
        return Integer.valueOf(Arg2);
    }

    public Pair getArguments() {
        return Arguments;
        
    }

    
    private void CreateArguments() {
        Arguments =   new Pair(arg1(),arg2());
    }
}




class Pop extends VmValue{
    private String Arg1;
    private String Arg2;
    private Pair Arguments;


    public static void main(String[] args) {
        new Pop("pop pointer 0").WriteAssembly("Foo.out");
      }


    public Pop(String command){
        String[] commandElements = command.split(" ");
        Arg1 = commandElements[1];
        Arg2 = commandElements[2];
        CreateArguments(); 
    }


    public String WriteAssembly(String FileName){
        System.out.println(Arg1);
    switch (Arg1) {
     
        case "static":
         return handleStatic(FileName);

        case "pointer":
        return handlePointer();

        case "temp":
        return handleTemp();

        default:
        return handleLocal_Argument_this_that();       
            
    }
}

//-----------------------------------

   private String handlePointer(){
    String code = String.format("//Write pop Pointer %s\n@SP\nM=M-1\nA=M\nD=M\n@%s\nM=D\n",
                                Arg2,ThisOrThat());
    System.out.println(code);
    return code;
   }


   private String handleTemp() {
    String code = String.format("//Write pop temp %s\n@%s\nD=A\n@addr\nM=D\n@%s\nD=A\n@addr\nM=M+D\n@SP\nM=M-1\nA=M\nD=M\n@addr\nA=M\nM=D\n",
                               Arg2,Arg2,"5");
    System.out.println(code);               
    return code;
   }
   
   private String handleStatic(String FileName) {

    String code = String.format("//write pop static %s\n@SP\nM=M-1\nA=M\nD=M\n@%s\nM=D\n"
    ,Arg2,getFilename(FileName));

     System.out.println(code);
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
    String code = String.format("//Write pop %s %s\n@%s\nD=A\n@%s\nD=M+D\n@addr\nM=D\n@SP\nM=M-1\nA=M\nD=M\n@addr\nA=M\nM=D\n",
                                Arg1,Arg2,Arg2,symbol);
    System.out.println(code);                           
    return code;

}

    public String arg1() {
        return Arg1;
    }

    public int arg2(){
        return Integer.valueOf(Arg2);
    }

    public Pair getArguments() {
        return Arguments;
        
    }
    private void CreateArguments() {
        Arguments =  new Pair(arg1(),arg2());
    }
}



class ARITHMETIC extends VmValue{
    private String Arg1;
    private Pair Arguments;
    
    // public static int index = 0;
    public static void main(String[] args) {
        new ARITHMETIC("not").WriteAssembly("");
    }

    public ARITHMETIC(String command){
        Arg1 = command;
        CreateArguments();
    }


    public String WriteAssembly(String FileName){
        System.out.println(Arg1);
        switch (Arg1) {
         
            case "add":
             return handleAdd();
    
            case "sub":
            return handleSub();
    
            case "neg":
            return handleNeg();

            case "and":
            return handleAnd();

            case "or":
            return handleOr();

            case "not":
            return handleNot();

            case "eq":
            return "handleEq()";

            case "gt":
            return "handleGT()";

            case "lt":
            return "handleLt()";
    
            default:
            return "handleLocal_Argument_this_that()"; 
        }      
                
   }

  
//    private String handleEq(){
//     // String code = String.format("//Write EQ\n%s@EQ\nM=D\n%s@EQ\nM=D-M\nD=M\n@IS_EQ_%s\n@D;JEQ\n@SP\nA=M\nM=0\n@CONTINUE_%s\n0;JMP\n(IS_EQ_%s)\n@SP\nA=M\nM=1\n@SP\nAM=M+1\n(CONTINUE_%s)\n",
//     //                        POP_SP_to_D,POP_SP_to_D,String.valueOf(ARITHMETIC.index),String.valueOf(index),String.valueOf(index),String.valueOf(index));

//     // System.out.println(code);                      
//     // return code;
//     return "";
//    }

   private String handleNot(){
    String code = String.format("//Write Not\n%s@NOT\nM=!D\nD=M\n%s",
                           POP_SP_to_D,starSP_equal_D_AND_increase_sp);
     System.out.println(code);                      
    return code;
   }


   private String handleOr(){
    String code = String.format("//Write Or\n%s@AND\nM=D\n%s@AND\nM=D|M\nD=M\n%s",
                           POP_SP_to_D,POP_SP_to_D,starSP_equal_D_AND_increase_sp);
     System.out.println(code);                      
    return code;
   }

   private String handleAnd(){
    String code = String.format("//Write And\n%s@AND\nM=D\n%s@AND\nM=D&M\nD=M\n%s",
                           POP_SP_to_D,POP_SP_to_D,starSP_equal_D_AND_increase_sp);
     System.out.println(code);                      
    return code;
   }

   private String handleNeg() {
    String code = String.format("//Write neg\n%s@NEG\nM=-D\nD=M\n%s",POP_SP_to_D,starSP_equal_D_AND_increase_sp);
    System.out.println(code);
    return code;
    
   }

   private  String handleSub() {
    String code = String.format("//Write sub\n%s@SUB\nM=D\n%s@SUB\nM=D-M\nD=M\n%s",
                               POP_SP_to_D,POP_SP_to_D,starSP_equal_D_AND_increase_sp);
    System.out.println(code);
    return code;
    
   }

   private String handleAdd() {
    String code = String.format("//Write add\n%s@SUM\nM=D\n%s@SUM\nM=M+D\nD=M\n%s",
                POP_SP_to_D,POP_SP_to_D,starSP_equal_D_AND_increase_sp);

    System.out.println(code);
    return code;
   }

    public String arg1() {
        return Arg1;
    }
   
    
     int arg2() {
        return 0;
    }


    public Pair getArguments() {
        return Arguments;
        
    }

    private void CreateArguments() {
        Arguments = new Pair(arg1(),null);
    }
    
}
