import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.Random;

public class SyougiServer{

  public static void main(String[] args) throws IOException{
    ServerSocket s = new ServerSocket(Integer.parseInt(args[0])); //ソケットの作成
    System.out.println("Started:" +s);
    try{
      Socket socket0 = s.accept();                  //コネクション設定要求を待つ
      Socket socket1 = s.accept();                  //コネクション設定要求を待つ

      try{
        System.out.println("Connection accepted: " + socket0);
        System.out.println("Connection accepted: " + socket1);

        BufferedReader in0 = new BufferedReader(new InputStreamReader(socket0.getInputStream())); //データ受信用バッファの設定
        PrintWriter out0 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket0.getOutputStream())), true); //データの送信バッファ
        BufferedReader in1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
        PrintWriter out1 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket1.getOutputStream())), true);
        Random rand = new Random();
        int num = rand.nextInt(2);

        if(num == 0){                              //ランダムでプレイヤーがどちらかを最初に決める
          out0.println("歩");
          out1.println("と");
        } else {
          out0.println("と");
          out1.println("歩");
        }
        System.out.println("~~~~~~~~~~~~~挟み将棋が始まります。~~~~~~~~~~~~~");
        String masu[][] = new String [9][9];
        //----------碁盤初期-------------
        int i, j;
        for(i=0;i<9;i++){
          for(j=0;j<9;j++){
            if(i == 0){
              masu[i][j] = "と"; //と
            }else if(i == 8){
              masu[i][j] = "歩"; //歩
            }else{
            masu[i][j] = "・";   //何もなし
            }
          }
        }
        //------------------------------
        Print.map(masu);

        int flag = num;          //どちらのプレイヤーの番か

        while(true){
          int result;

          if(flag == 0){                            //player入力(交互に)
            String player0_name = in0.readLine();    //名前
            String player0_piece = in0.readLine();   //駒
            int test = Integer.parseInt(in0.readLine());
            int [] player0_fight_count = { test }; //持ち駒
            String player0_before_point_x = in0.readLine();   //動かす駒の座標
            String player0_before_point_y = in0.readLine();
            String player0_after_point_x = in0.readLine();    //動かした後の駒の座標
            String player0_after_point_y = in0.readLine();

            System.out.println("プレイヤー：" + player0_name + ", プレイヤーの駒：" + player0_piece + ", 動かす駒の座標：(" + player0_before_point_x + "," + player0_before_point_y + "), 動かした後の駒の座標：(" + player0_after_point_x + "," + player0_after_point_y + ")");
            result = Piece.move(masu, player0_before_point_x, player0_before_point_y, player0_after_point_x, player0_after_point_y, player0_piece, player0_fight_count);  //動かした結果
            flag = 1;
            out0.println(player0_fight_count[0]);

          }else{
            String player1_name = in1.readLine();    //名前
            String player1_piece = in1.readLine();   //駒
            int test = Integer.parseInt(in1.readLine());
            int [] player1_fight_count = { test }; //持ち駒
            String player1_before_point_x = in1.readLine();   //動かす駒の座標
            String player1_before_point_y = in1.readLine();
            String player1_after_point_x = in1.readLine();    //動かした後の駒の座標
            String player1_after_point_y = in1.readLine();

            System.out.println("プレイヤー：" + player1_name + ", プレイヤーの駒：「" + player1_piece + "」, 動かす駒の座標：(" + player1_before_point_x + "," + player1_before_point_y + "), 動かした後の駒の座標：(" + player1_after_point_x + "," + player1_after_point_y + ")");
            result = Piece.move(masu, player1_before_point_x, player1_before_point_y, player1_after_point_x, player1_after_point_y, player1_piece, player1_fight_count);  //動かした結果
            flag = 0;
            out1.println(player1_fight_count[0]);
          }

          Print.map(masu);    //碁盤の出力

          if(result == 2){     //対戦中
            System.out.println("ーーーーーー対戦中ーーーーーー");
            System.out.println();
            out0.println("ーーーーーターン終了ーーーーー");
            out1.println("ーーーーーターン終了ーーーーー");
          } else if (result == 0){           //プレイヤー0の勝ち
            System.out.println("ーーーーーー終了ーーーーーー");
            System.out.println();
            if(num==0){
              out0.println("ーーーーーー勝利ーーーーーー");
              out1.println("ーーーーーー敗北ーーーーーー");
              break;
            }else{
              out0.println("ーーーーーー敗北ーーーーーー");
              out1.println("ーーーーーー勝利ーーーーーー");
              break;
            }
          } else if (result == 1){           //プレイヤー1の勝ち
            System.out.println("ーーーーーー終了ーーーーーー");
            System.out.println();
            if(num==0){
              out0.println("ーーーーーー勝利ーーーーーー");
              out1.println("ーーーーーー敗北ーーーーーー");
              break;
            }else{
              out0.println("ーーーーーー敗北ーーーーーー");
              out1.println("ーーーーーー勝利ーーーーーー");
              break;
            }
          } else if (result == 3){
            System.out.println("ーーーー不正な座標ですーーーー");
            System.out.println();
            out0.println("ーーーー不正な座標ですーーーー");
            out1.println("ーーーー不正な座標ですーーーー");
            if(flag==0){
              flag = 1;
            }else{
              flag = 0;
            }
          }
        }
      }finally{
        // System.out.println("closing...");
        socket0.close();
        socket1.close();
      }
    }finally{
      s.close();
    }
  }

}
