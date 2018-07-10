public class Piece extends SyougiServer{

  public static void print_result(String a){
    System.out.println();
    System.out.println(a);
  }

  static int move(String masu[][], String be_x, String be_y, String af_x, String af_y, String player_piece, int fight_count[]){
    int i, j, k, l, m;
    int between_count = 1;
    int between_count1 = 1;
    int between_count2 = 1;
    int between_count3 = 1;
    int between_count4 = 1;
    int corner_count = 1;
    int sur_count = 1;
    int b_x = Integer.parseInt(be_x) - 1; //表示の座標の数字と実際の配列の数字は違う
    int b_y = Integer.parseInt(be_y) - 1;
    int a_x = Integer.parseInt(af_x) - 1;
    int a_y = Integer.parseInt(af_y) - 1;

    if((0<=b_x && b_x<=8)&&(0<=b_y && b_y<=8)&&(0<=a_x && a_x<=8)&&(0<=a_y && a_y<=8)&&((b_x==a_x)||(b_y==a_y))){   //碁盤内の座標でないといけない&縦移動と横移動のみ

      //-------------駒を跨がないようにする--------------
      if(a_y==b_y){//縦
        for(i=b_x+1;i<a_x;i++){// ＋１してるのは自分の駒が入らないように(間を考えるから)
          if(masu[i][a_y].equals("歩")||masu[i][a_y].equals("と")){
            return 3;
          }
        }
        for(i=a_x+1;i<b_x;i++){
          if(masu[i][a_y].equals("歩")||masu[i][a_y].equals("と")){
            return 3;
          }
        }
      }else {//横
        for(j=b_y+1;j<a_y;j++){
          if(masu[a_x][j].equals("歩")||masu[a_x][j].equals("と")){
            return 3;
          }
        }
        for(j=a_y+1;j<b_y;j++){
          if(masu[a_x][j].equals("歩")||masu[a_x][j].equals("と")){
            return 3;
          }
        }
      }

      //----------------------------------------------

      if(masu[b_x][b_y].equals(player_piece) && masu[a_x][a_y].equals("・")){ //正しい自分の駒の選択をし、移動先がかならず「・」な場合
        masu[b_x][b_y] = "・";
        masu[a_x][a_y] = player_piece;

        //-----------------横で複数どり----------------
        for(m=0;m<2;m++){
          for(i=0;i<9;i++){
            for(j=1;j<8;j++){
              for(k=j;k<9;k++){
                if(masu[i][j-1].equals(player_piece) && masu[i][k].equals(player_piece)){// 歩.....歩　の歩探し
                  for(l=j;l<k;l++){//歩と歩の間の...の部分
                    if(masu[i][l].equals("と")&&masu[i][l-1].equals("と")){
                      between_count1++;
                    }//間に何駒あるか数えてる

                    if(between_count1==(k-j)){//間にある駒が全てplayer_pieceではない場合(次の行のif文も含め)
                      if((masu[i][l].equals("と") && player_piece.equals("歩"))){
                        for(l=j;l<k;l++){
                          masu[i][l] = "・";
                        }
                        print_result("ーーーー駒を取りましたーーーー");
                        fight_count[0] += between_count1;
                      }
                    }
                    between_count1 = 1;

                    if(masu[i][l].equals("歩")&&masu[i][l-1].equals("歩")){
                      between_count2++;
                    }//間に何駒あるか数えてる

                    if(between_count2==(k-j)){//間にある駒が全てplayer_pieceではない場合(次の行のif文も含め)
                      if((masu[i][l].equals("歩") && player_piece.equals("と"))){
                        for(l=j;l<k;l++){
                          masu[i][l] = "・";
                        }
                        print_result("ーーーー駒を取りましたーーーー");
                        fight_count[0] += between_count2;
                      }
                    }
                    between_count2 = 1;
                  }
                }
              }
            }
          }
        }
        //-------------------------------------------

        //------------------縦で複数どり----------------
        for(m=0;m<2;m++){
          for(i=1;i<8;i++){
            for(j=0;j<9;j++){
              for(k=i;k<9;k++){
                if(masu[i-1][j].equals(player_piece) && masu[k][j].equals(player_piece)){// 歩.....歩　の歩探し
                  for(l=i;l<k;l++){//歩と歩の間の...の部分
                    if(masu[l][j].equals("と") && masu[l+1][j].equals("と")){
                      between_count3++;
                    }//間に何駒あるか数えてる

                    if(between_count3==(k-i)){//間にある駒が全てplayer_pieceではない場合(次の行のif文も含め)
                      if((masu[l][j].equals("と") && player_piece.equals("歩"))){
                        for(l=i;l<k;l++){
                          masu[l][j] = "・";
                        }
                        print_result("ーーーー駒を取りましたーーーー");
                        fight_count[0] += between_count3;
                      }
                    }
                    between_count3 = 1;

                    if(masu[l][j].equals("歩") && masu[l+1][j].equals("歩")){
                      between_count4++;
                    }//間に何駒あるか数えてる

                    if(between_count4==(k-i)){//間にある駒が全てplayer_pieceではない場合(次の行のif文も含め)
                      if((masu[l][j].equals("歩") && player_piece.equals("と"))){
                        for(l=i;l<k;l++){
                          masu[l][j] = "・";
                        }
                        print_result("ーーーー駒を取りましたーーーー");
                        fight_count[0] += between_count4;
                      }
                    }
                    between_count4 = 1;
                  }
                }
              }
            }
          }
        }
        //--------------------------------------------


        //------------------[0][0]で複数どり(四隅)-----------------

        if(masu[0][0].equals("と") && player_piece.equals("歩")){

          for(i=1;i<4;i++){
            if(masu[0][i-1].equals("と") && masu[0][i].equals("と")){ //連続していたらカウント
                corner_count++;
            }
          }
          if(masu[0][corner_count].equals(player_piece)&&masu[1][0].equals(player_piece)){//２番目の条件がないと角１つとるだけの時count同士が一緒になってしまいなんであっても取れてしまう
            for(j=1;j<corner_count;j++){
              if(masu[1][j-1].equals(player_piece) && masu[1][j].equals(player_piece)){
                sur_count++;
              }
            }
            if(sur_count == corner_count){
              for(k=0;k<corner_count;k++){
                masu[0][k] = "・";
                fight_count[0]++;
              }
              print_result("ーーーー駒を取りましたーーーー");
            }
          }
          corner_count = 1;
          sur_count = 1;
        }

        if(masu[0][0].equals("歩") && player_piece.equals("と")){

          for(i=1;i<4;i++){
            if(masu[0][i-1].equals("歩") && masu[0][i].equals("歩")){ //連続していたらカウント
                corner_count++;
            }
          }
          if(masu[0][corner_count].equals(player_piece)&&masu[1][0].equals(player_piece)){
            for(j=1;j<corner_count;j++){
              if(masu[1][j-1].equals(player_piece) && masu[1][j].equals(player_piece)){
                sur_count++;
              }
            }
            if(sur_count == corner_count){
              for(k=0;k<corner_count;k++){
                masu[0][k] = "・";
                fight_count[0]++;
              }
              print_result("ーーーー駒を取りましたーーーー");
            }
          }
          corner_count = 1;
          sur_count = 1;
        }

        if(masu[0][0].equals("と") && player_piece.equals("歩")){

          for(i=1;i<4;i++){
            if(masu[i-1][0].equals("と") && masu[i][0].equals("と")){ //連続していたらカウント
                corner_count++;
            }
          }
          if(masu[corner_count][0].equals(player_piece)&&masu[0][1].equals(player_piece)){
            for(j=1;j<corner_count;j++){
              if(masu[j-1][1].equals(player_piece) && masu[j][1].equals(player_piece)){
                sur_count++;
              }
            }
            if(sur_count == corner_count){
              for(k=0;k<corner_count;k++){
                masu[k][0] = "・";
                fight_count[0]++;
              }
              print_result("ーーーー駒を取りましたーーーー");
            }
          }
        }

        if(masu[0][0].equals("歩") && player_piece.equals("と")){

          for(i=1;i<4;i++){
            if(masu[i-1][0].equals("歩") && masu[i][0].equals("歩")){ //連続していたらカウント
                corner_count++;
            }
          }
          if(masu[corner_count][0].equals(player_piece)&&masu[0][1].equals(player_piece)){
            for(j=1;j<corner_count;j++){
              if(masu[j-1][1].equals(player_piece) && masu[j][1].equals(player_piece)){
                sur_count++;
              }
            }
            if(sur_count == corner_count){
              for(k=0;k<corner_count;k++){
                masu[k][0] = "・";
                fight_count[0]++;
              }
              print_result("ーーーー駒を取りましたーーーー");
            }
          }
        }
        //----------------------------------------------

        //------------------[0][8]で複数どり(四隅)-----------------

        if(masu[0][8].equals("と") && player_piece.equals("歩")){

          for(i=7;i>4;i--){
            if(masu[0][i+1].equals("と") && masu[0][i].equals("と")){ //連続していたらカウント
                corner_count++;
            }
          }
          if(masu[0][8-corner_count].equals(player_piece)&&masu[1][8].equals(player_piece)){//２番目の条件がないと角１つとるだけの時count同士が一緒になってしまいなんであっても取れてしまう
            for(j=7;j>8-corner_count;j--){
              if(masu[1][j+1].equals(player_piece) && masu[1][j].equals(player_piece)){
                sur_count++;
              }
            }
            if(sur_count == corner_count){
              for(k=8;k>8-corner_count;k--){
                masu[0][k] = "・";
                fight_count[0]++;
              }
              print_result("ーーーー駒を取りましたーーーー");
            }
          }
          corner_count = 1;
          sur_count = 1;
        }

        if(masu[0][8].equals("歩") && player_piece.equals("と")){

          for(i=7;i>4;i--){
            if(masu[0][i+1].equals("歩") && masu[0][i].equals("歩")){ //連続していたらカウント
                corner_count++;
            }
          }
          if(masu[0][8-corner_count].equals(player_piece)&&masu[1][8].equals(player_piece)){//２番目の条件がないと角１つとるだけの時count同士が一緒になってしまいなんであっても取れてしまう
            for(j=7;j>8-corner_count;j--){
              if(masu[1][j+1].equals(player_piece) && masu[1][j].equals(player_piece)){
                sur_count++;
              }
            }
            if(sur_count == corner_count){
              for(k=8;k>8-corner_count;k--){
                masu[0][k] = "・";
                fight_count[0]++;
              }
              print_result("ーーーー駒を取りましたーーーー");
            }
          }
          corner_count = 1;
          sur_count = 1;
        }

        if(masu[0][8].equals("と") && player_piece.equals("歩")){

          for(i=1;i<4;i++){
            if(masu[i-1][8].equals("と") && masu[i][8].equals("と")){ //連続していたらカウント
                corner_count++;
            }
          }
          if(masu[corner_count][8].equals(player_piece)&&masu[0][7].equals(player_piece)){
            for(j=1;j<corner_count;j++){
              if(masu[j-1][7].equals(player_piece) && masu[j][7].equals(player_piece)){
                sur_count++;
              }
            }
            if(sur_count == corner_count){
              for(k=0;k<corner_count;k++){
                masu[k][8] = "・";
                fight_count[0]++;
              }
              print_result("ーーーー駒を取りましたーーーー");
            }
          }
        }

        if(masu[0][8].equals("歩") && player_piece.equals("と")){

          for(i=1;i<4;i++){
            if(masu[i-1][8].equals("歩") && masu[i][8].equals("歩")){ //連続していたらカウント
                corner_count++;
            }
          }
          if(masu[corner_count][8].equals(player_piece)&&masu[0][7].equals(player_piece)){
            for(j=1;j<corner_count;j++){
              if(masu[j-1][7].equals(player_piece) && masu[j][7].equals(player_piece)){
                sur_count++;
              }
            }
            if(sur_count == corner_count){
              for(k=0;k<corner_count;k++){
                masu[k][8] = "・";
                fight_count[0]++;
              }
              print_result("ーーーー駒を取りましたーーーー");
            }
          }
        }
        //----------------------------------------------

        //------------------[8][0]で複数どり(四隅)-----------------

        if(masu[8][0].equals("と") && player_piece.equals("歩")){

          for(i=1;i<4;i++){
            if(masu[8][i-1].equals("と") && masu[8][i].equals("と")){ //連続していたらカウント
                corner_count++;
            }
          }
          if(masu[8][corner_count].equals(player_piece)&&masu[7][0].equals(player_piece)){//２番目の条件がないと角１つとるだけの時count同士が一緒になってしまいなんであっても取れてしまう
            for(j=1;j<corner_count;j++){
              if(masu[7][j-1].equals(player_piece) && masu[7][j].equals(player_piece)){
                sur_count++;
              }
            }
            if(sur_count == corner_count){
              for(k=0;k<corner_count;k++){
                masu[8][k] = "・";
                fight_count[0]++;
              }
              print_result("ーーーー駒を取りましたーーーー");
            }
          }
          corner_count = 1;
          sur_count = 1;
        }

        if(masu[8][0].equals("歩") && player_piece.equals("と")){

          for(i=1;i<4;i++){
            if(masu[8][i-1].equals("歩") && masu[8][i].equals("歩")){ //連続していたらカウント
                corner_count++;
            }
          }
          if(masu[8][corner_count].equals(player_piece)&&masu[7][0].equals(player_piece)){
            for(j=1;j<corner_count;j++){
              if(masu[7][j-1].equals(player_piece) && masu[7][j].equals(player_piece)){
                sur_count++;
              }
            }
            if(sur_count == corner_count){
              for(k=0;k<corner_count;k++){
                masu[8][k] = "・";
                fight_count[0]++;
              }
              print_result("ーーーー駒を取りましたーーーー");
            }
          }
          corner_count = 1;
          sur_count = 1;
        }

        if(masu[8][0].equals("と") && player_piece.equals("歩")){
          for(i=7;i>4;i--){
            if(masu[i+1][0].equals("と") && masu[i][0].equals("と")){ //連続していたらカウント
                corner_count++;
            }
          }
          if(masu[8-corner_count][0].equals(player_piece)&&masu[8][1].equals(player_piece)){
            for(j=7;j>8-corner_count;j--){
              if(masu[j+1][1].equals(player_piece) && masu[j][1].equals(player_piece)){
                sur_count++;
              }
            }
            if(sur_count == corner_count){
              for(k=8;k>8-corner_count;k--){
                masu[k][0] = "・";
                fight_count[0]++;
              }
              print_result("ーーーー駒を取りましたーーーー");
            }
          }
        }

        if(masu[8][0].equals("歩") && player_piece.equals("と")){

          for(i=7;i>4;i--){
            if(masu[i+1][0].equals("歩") && masu[i][0].equals("歩")){ //連続していたらカウント
                corner_count++;
            }
          }
          if(masu[8-corner_count][0].equals(player_piece)&&masu[8][1].equals(player_piece)){
            for(j=7;j>8-corner_count;j--){
              if(masu[j+1][1].equals(player_piece) && masu[j][1].equals(player_piece)){
                sur_count++;
              }
            }
            if(sur_count == corner_count){
              for(k=8;k>8-corner_count;k--){
                masu[k][0] = "・";
                fight_count[0]++;
              }
              print_result("ーーーー駒を取りましたーーーー");
            }
          }
        }
        //----------------------------------------------

        //------------------[8][8]で複数どり(四隅)-----------------

        if(masu[8][8].equals("と") && player_piece.equals("歩")){

          for(i=7;i>4;i--){
            if(masu[8][i+1].equals("と") && masu[8][i].equals("と")){ //連続していたらカウント
                corner_count++;
            }
          }
          if(masu[8][8-corner_count].equals(player_piece)&&masu[7][8].equals(player_piece)){//２番目の条件がないと角１つとるだけの時count同士が一緒になってしまいなんであっても取れてしまう
            for(j=7;j>8-corner_count;j--){
              if(masu[7][j+1].equals(player_piece) && masu[7][j].equals(player_piece)){
                sur_count++;
              }
            }
            if(sur_count == corner_count){
              for(k=8;k>8-corner_count;k--){
                masu[8][k] = "・";
                fight_count[0]++;
              }
              print_result("ーーーー駒を取りましたーーーー");
            }
          }
          corner_count = 1;
          sur_count = 1;
        }

        if(masu[8][8].equals("歩") && player_piece.equals("と")){

          for(i=7;i>4;i--){
            if(masu[8][i+1].equals("歩") && masu[8][i].equals("歩")){ //連続していたらカウント
                corner_count++;
            }
          }
          if(masu[8][8-corner_count].equals(player_piece)&&masu[7][8].equals(player_piece)){//２番目の条件がないと角１つとるだけの時count同士が一緒になってしまいなんであっても取れてしまう
            for(j=7;j>8-corner_count;j--){
              if(masu[7][j+1].equals(player_piece) && masu[7][j].equals(player_piece)){
                sur_count++;
              }
            }
            if(sur_count == corner_count){
              for(k=8;k>8-corner_count;k--){
                masu[8][k] = "・";
                fight_count[0]++;
              }
              print_result("ーーーー駒を取りましたーーーー");
            }
          }
          corner_count = 1;
          sur_count = 1;
        }

        if(masu[8][8].equals("と") && player_piece.equals("歩")){
          for(i=7;i>4;i--){
            if(masu[i+1][8].equals("と") && masu[i][8].equals("と")){ //連続していたらカウント
                corner_count++;
            }
          }
          if(masu[8-corner_count][8].equals(player_piece)&&masu[8][7].equals(player_piece)){
            for(j=7;j>8-corner_count;j--){
              if(masu[j+1][7].equals(player_piece) && masu[j][7].equals(player_piece)){
                sur_count++;
              }
            }
            if(sur_count == corner_count){
              for(k=8;k>8-corner_count;k--){
                masu[k][8] = "・";
                fight_count[0]++;
              }
              print_result("ーーーー駒を取りましたーーーー");
            }
          }
        }

        if(masu[8][8].equals("歩") && player_piece.equals("と")){
          for(i=7;i>4;i--){
            if(masu[i+1][8].equals("歩") && masu[i][8].equals("歩")){ //連続していたらカウント
                corner_count++;
            }
          }
          if(masu[8-corner_count][8].equals(player_piece)&&masu[8][7].equals(player_piece)){
            for(j=7;j>8-corner_count;j--){
              if(masu[j+1][7].equals(player_piece) && masu[j][7].equals(player_piece)){
                sur_count++;
              }
            }
            if(sur_count == corner_count){
              for(k=8;k>8-corner_count;k--){
                masu[k][8] = "・";
                fight_count[0]++;
              }
              print_result("ーーーー駒を取りましたーーーー");
            }
          }
        }
        //----------------------------------------------




        //------------------四隅取り---------------------
        if(player_piece.equals("歩")){ //わざわざこう書かないと動かしたプレイヤーに持ち駒がいかない
          if(masu[0][0].equals("と") && masu[0][1].equals(player_piece) && masu[1][0].equals(player_piece)){
            masu[0][0] = "・";
                fight_count[0]++;
                print_result("ーーーー駒を取りましたーーーー");
          }else if(masu[0][8].equals("と") && masu[0][7].equals(player_piece) && masu[1][8].equals(player_piece)){
            masu[0][8] = "・";
                fight_count[0]++;
                print_result("ーーーー駒を取りましたーーーー");
          }else if(masu[8][0].equals("と") && masu[7][0].equals(player_piece) && masu[8][1].equals(player_piece)){
            masu[8][0] = "・";
                fight_count[0]++;
                print_result("ーーーー駒を取りましたーーーー");
          }else if(masu[8][8].equals("と") && masu[8][7].equals(player_piece) && masu[7][8].equals(player_piece)){
            masu[8][8] = "・";
                fight_count[0]++;
                print_result("ーーーー駒を取りましたーーーー");
          }
        }else if(player_piece.equals("と")){ //わざわざこう書かないと動かしたプレイヤーに持ち駒がいかない
          if(masu[0][0].equals("歩") && masu[0][1].equals(player_piece) && masu[1][0].equals(player_piece)){
            masu[0][0] = "・";
                fight_count[0]++;
                print_result("ーーーー駒を取りましたーーーー");
          }else if(masu[0][8].equals("歩") && masu[0][7].equals(player_piece) && masu[1][8].equals(player_piece)){
            masu[0][8] = "・";
                fight_count[0]++;
                print_result("ーーーー駒を取りましたーーーー");
          }else if(masu[8][0].equals("歩") && masu[7][0].equals(player_piece) && masu[8][1].equals(player_piece)){
            masu[8][0] = "・";
                fight_count[0]++;
                print_result("ーーーー駒を取りましたーーーー");
          }else if(masu[8][8].equals("歩") && masu[8][7].equals(player_piece) && masu[7][8].equals(player_piece)){
            masu[8][8] = "・";
                fight_count[0]++;
                print_result("ーーーー駒を取りましたーーーー");
          }
        }
        //---------------------------------------------


        if(player_piece.equals("歩")){
          if(fight_count[0] >= 5){
            return 0;
          }
        }else{
          if(fight_count[0] >= 5){
            return 1;
          }
        }
        return 2;
      }else{
        return 3; //間違えた座標を打った場合、もう一度。
      }

    }else{
      return 3;
    }


  }
}
