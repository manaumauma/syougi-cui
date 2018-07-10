import java.io.*;
import java.net.*;
import java.util.Scanner;

public class SyougiClient{
  public static String my_piece;
  static String name;
  public static void main(String[] args) throws IOException{
    InetAddress addr = InetAddress.getByName("localhost");
    // System.out.println("addr = " + addr);
    Socket socket = new Socket(addr,Integer.parseInt(args[0]));

    try{
      // System.out.println("socket = " + socket);
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
      Scanner scanner = new Scanner(System.in);

      System.out.print("名前を入力してください: ");
      name = scanner.next();
      my_piece = in.readLine();
      System.out.println(name + "さん、挟み将棋を始めます。");
      System.out.println(name + "さんの駒は「" + my_piece + "」です。");
      System.out.println();

      int fight_count[] = {0}; //持ち駒(今回参照渡しにしたかったから配列にしている)

      int flag = 0;
      int wait = 1;
      while(true){

        if(flag == 0){   //プレイヤーの切り替え
          if(my_piece.equals("歩")){
            System.out.println("持ち駒は" + fight_count[0] + "枚です。");
            System.out.println();
            System.out.print("動かす駒「" + my_piece + "」の座標を入力(例: 3 7 (縦 横))         :");
            String my_before_point_x = scanner.next();
            String my_before_point_y = scanner.next();
            System.out.print("動かした後の駒「" + my_piece + "」の座標を入力(例: 4 7 (縦 横))   :");
            String my_after_point_x =  scanner.next();
            String my_after_point_y =  scanner.next();
            System.out.println();

            out.println(name);
            out.println(my_piece);
            out.println(fight_count[0]);
            out.println(my_before_point_x);
            out.println(my_before_point_y);
            out.println(my_after_point_x);
            out.println(my_after_point_y);
            wait = 1;
            fight_count[0] = Integer.parseInt(in.readLine());
          }else{
            System.out.println("持ち駒は" + fight_count[0] + "枚です。");
            //if(fight_count[0] >= 5) break;
            if (wait == 1){
              System.out.println();
              System.out.println("...相手の入力待ち...");   //出力は一回でいいからwaitで制限してる
              System.out.println();
              wait = 2;
            }
          }
          flag = 1; //プレイヤーの切り替え
        }else{
          if(my_piece.equals("と")){
            System.out.println("持ち駒は" + fight_count[0] + "枚です。");
            System.out.println();
            System.out.print("動かす駒「" + my_piece + "」の座標を入力(例: 3 7 (縦 横))          :");
            String my_before_point_x = scanner.next();
            String my_before_point_y = scanner.next();
            System.out.print("動かした後の駒「" + my_piece + "」の座標を入力(例: 4 7 (縦 横))    :");
            String my_after_point_x =  scanner.next();
            String my_after_point_y =  scanner.next();
            System.out.println();

            out.println(name);
            out.println(my_piece);
            out.println(fight_count[0]);
            out.println(my_before_point_x);
            out.println(my_before_point_y);
            out.println(my_after_point_x);
            out.println(my_after_point_y);
            wait = 1;
            fight_count[0] = Integer.parseInt(in.readLine());
          }else{
            System.out.println("持ち駒は" + fight_count[0] + "枚です。");
            //if(fight_count[0] >= 5) break;
            if (wait == 1){
              System.out.println();
              System.out.println("...相手の入力待ち...");
              System.out.println();
              wait = 2;
            }
          }
          flag = 0;
        }

        String state = in.readLine();
        System.out.println(state);
        System.out.println();
        if(state.equals("ーーーーーー勝利ーーーーーー")||state.equals("ーーーーーー敗北ーーーーーー")) break;
        if(state.equals("ーーーー不正な座標ですーーーー")){
          if(flag==0){ //不正な値だった場合もう一度
            flag = 1;
          }else{
            flag = 0;
          }

        }

      }

    } finally {
      // System.out.println("closing...");
      socket.close();
    }
  }
}

