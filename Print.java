public class Print extends SyougiServer{
  // static String masu[][] = new String [9][9];
  static void map(String masu[][]){
    int i, j, k;
    System.out.println();
    System.out.println("                     横");
    System.out.println();
    System.out.print("            ");
    for(k=0;k<9;k++){
      System.out.print(" "+(k+1));
    }
    System.out.println();
    System.out.println("             ーーーーーーーーー");
    for(i=0;i<9;i++){
      if(i==4){
        System.out.print("    縦    "+(i+1)+" |");
      }else{
        System.out.print("          "+(i+1)+" |");
      }
      for(j=0;j<9;j++){
        System.out.print(masu[i][j]);
      }
      System.out.print("|");
      System.out.println();
    }
    System.out.println("             ーーーーーーーーー");
    System.out.println();
    System.out.println();
  }
}
