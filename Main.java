package software;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main extends JFrame {
	private static int maxConnection = 2; // プレイヤー数（2 ）
	private static String myName; // 自分の名前
	private static int myNumber; // 自分の番号
	private static String font = "ＭＳ ゴシック"; // フォントの指定（文字化けする場合ここを変えてください）
	private static Socket svSocket;// クライアント側が入力するサーバーのソケット
	private static SyougiServer server;
	private static SyougiClient client;
	private static String name[] = new String[5];
	private static JPanel menuPanel[] = new JPanel[7]; // 各メニューのパネル
	private static JLabel background[] = new JLabel[7]; // 各メニュー画面の背景
	private static JLabel waitLabel[] = new JLabel[3];
	private static String roomIPText = null;
	private Container c = getContentPane(); // コンテナ
	private static boolean flag = false; // 画面遷移を待たせるフラグ

	/*
	 * -------------------------------------コンストラクタ-----------------------------
	 * ---------
	 */
	Main() {

		/* タイトルを登録 */
		setTitle("はさみ将棋");

		/* 画面の大きさ */
		setSize(500, 500);
		setResizable(false);

		/* 画面位置(真ん中) */
		setLocationRelativeTo(null);

		/* ウィンドウを消した時プログラムが終了するようにする */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* アイコン */
		setIconImage(new ImageIcon(Main.class.getResource("image/icon.jpg")).getImage());

		/* メニューパネル */
		for (int i = 0; i < 7; i++) {
			menuPanel[i] = new JPanel();
			menuPanel[i].setLayout(null);
		}

		/* 背景 */
		for (int i = 0; i < 7; i++) {
			background[i] = new JLabel();
			background[i].setBounds(0, 0, 500, 500);
		}

		JLabel label4 = new JLabel();

		/* -----------------------スタート画面(0)----------------------- */
		// タイトル文字
		JLabel label0 = new JLabel("はさみ将棋", JLabel.CENTER);
		label0.setBounds(20, 180, 300, 90); // 変更(20,180,300,100)
		label0.setFont(new Font(font, Font.BOLD, 50));
		label0.setForeground(Color.RED);
		menuPanel[0].add(label0);
		// スタートボタン
		JButton button0 = new JButton("START");
		menuPanel[0].add(button0);
		button0.setFont(new Font(font, Font.BOLD, 45));
		button0.setBounds(290, 370, 180, 80);
		button0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePanel(0, 1);
			}
		});
		// 背景
		background[0].setIcon(new ImageIcon(Main.class.getResource("image/syougi.jpg")));
		menuPanel[0].add(background[0]);

		c.add(menuPanel[0]);

		/* -----------------------名前の入力(1)----------------------- */
		// 指示文
		JLabel label1 = new JLabel("<html>名前を入力してください<br>（8文字以内）</html>");
		label1.setBounds(70, 50, 400, 150);
		label1.setFont(new Font(font, Font.BOLD, 30));
		menuPanel[1].add(label1);
		// 入力エリア
		JTextField nameText = new JTextField(10);
		nameText.setBounds(100, 200, 300, 90);
		nameText.setFont(new Font(font, Font.BOLD, 45));
		menuPanel[1].add(nameText);
		// 決定ボタン
		JButton button1 = new JButton("決定");
		menuPanel[1].add(button1);
		button1.setFont(new Font(font, Font.BOLD, 25));
		button1.setBounds(200, 350, 100, 60);
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = nameText.getText();
				if (str.length() <= 8 && str.length() > 0) {
					myName = str;
					changePanel(1, 2);
				}
			}
		});
		// 背景
		background[1].setIcon(new ImageIcon(Main.class.getResource("image/enter.jpg")));
		menuPanel[1].add(background[1]);

		/*
		 * -----------------------(部屋を作る/部屋に入る)選択画面(親or子)(2)--------------------
		 * ---
		 */
		// 部屋を作る
		JButton makeBtn = new JButton("部屋を作る");
		menuPanel[2].add(makeBtn);
		makeBtn.setFont(new Font(font, Font.BOLD, 42));
		makeBtn.setBounds(100, 100, 300, 100);
		makeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (makeRoom()) {
					label4.setText("<html>部屋のIPアドレス<br>" + roomIPText + "</html>");
					changePanel(2, 4);
				}
			}
		});
		// 部屋に入る
		JButton enterBtn = new JButton("部屋に入る");
		menuPanel[2].add(enterBtn);
		enterBtn.setFont(new Font(font, Font.BOLD, 42));
		enterBtn.setBounds(100, 300, 300, 100);
		enterBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePanel(2, 3);
			}
		});
		// 背景
		background[2].setIcon(new ImageIcon(Main.class.getResource("image/door.jpg")));
		menuPanel[2].add(background[2]);

		/* -----------------------(子のみ)部屋のIPアドレスの入力(3)----------------------- */
		// 指示文
		JLabel label3 = new JLabel("<html>部屋のIPアドレスを入力してください<br>(空入力の場合、自身のローカルホストに接続します)</html>");
		label3.setBounds(50, 10, 400, 130);
		label3.setFont(new Font(font, Font.BOLD, 28));
		label3.setBackground(Color.WHITE);
		label3.setOpaque(true); // 不透明化
		menuPanel[3].add(label3);
		// 入力エリア
		JTextField ipText = new JTextField(10);
		ipText.setBounds(80, 265, 340, 60);
		ipText.setFont(new Font(font, Font.BOLD, 30));
		menuPanel[3].add(ipText);
		// 接続ボタン
		JButton button3 = new JButton("接続");
		menuPanel[3].add(button3);
		button3.setFont(new Font(font, Font.BOLD, 25));
		button3.setBounds(200, 360, 100, 60);
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					roomIPText = ipText.getText();
					/* 入力されたIPアドレスでソケット通信を行う */
					client = new SyougiClient(myName, new Socket(roomIPText, 8080)); // SyougiClientを変える必要あり。
					client.start();

					label4.setText("<html>部屋のIPアドレス<br>" + roomIPText + "</html>");
					changePanel(3, 4);
				} catch (Exception ex) {
					errorMessage(ex);
				}
			}
		});
		background[3].setIcon(new ImageIcon(Main.class.getResource("image/connect.jpg")));
		menuPanel[3].add(background[3]);

		/* -----------------------参加者を待つ画面(4)----------------------- */
		// 部屋での待ち画面の名前ラベル
		for (int i = 1; i <= 2; i++) {
			// 指示文
			waitLabel[i] = new JLabel(i + ":");
			waitLabel[i].setFont(new Font(font, Font.BOLD, 25));
			waitLabel[i].setBounds(115, 70 + i * 36, 370, 36);
			waitLabel[i].setForeground(Color.RED);
			waitLabel[i].setBackground(Color.WHITE);
			menuPanel[4].add(waitLabel[i]);
		}
		// IPアドレス表示
		label4.setBounds(120, 15, 250, 60);
		label4.setFont(new Font(font, Font.BOLD, 25));
		label4.setForeground(Color.RED);
		menuPanel[4].add(label4);
		// 背景
		background[4].setIcon(new ImageIcon(Main.class.getResource("image/wait.jpg")));
		menuPanel[4].add(background[4]);

	}

	/*
	 * ----------------------------------以下、メソッド--------------------------------
	 * -
	 */

	/* エラーメッセージの表示 */
	static void errorMessage(Exception ex) {
		JFrame messageFrame = new JFrame();
		JOptionPane.showMessageDialog(messageFrame, "<html>エラーが発生しました<br>" + ex + "</html>");
	}

	/* メニュー画面をiからjに変える */
	void changePanel(int i, int j) {
		menuPanel[i].setVisible(false);
		c.add(menuPanel[j]);
		menuPanel[j].setVisible(true);
		c.remove(menuPanel[i]);
	}

	/* 部屋を作る親が、サーバーを立ち上げ、自身も子として部屋に入る */
	static boolean makeRoom() {
		try {
			server = new SyougiServer();
			server.start();
			InetAddress roomIP = InetAddress.getLocalHost();
			client = new SyougiClient(myName, new Socket(roomIP, 8080));
			client.start();

			/* IPアドレスを数字部分だけにして用意 */
			String divRoomIPText[] = roomIP.toString().split("/", 0);
			roomIPText = divRoomIPText[1];

			return true;
		} catch (Exception ex) {
			errorMessage(ex);
			return false;
		}
	}

	/* 部屋で他の参加者を待っている間に名前を表示 */
	static void waitRoom(int i, String str) {
		name[i] = str;
		waitLabel[i].setText(i + ":" + name[i]);
	}

	static void setflag() {
		flag = true;
	}

	/* flagがtrueになるまで待つ */
	static void flagWait() {
		while (!flag) {
			try {
				Thread.sleep(100);
			} catch (Exception ex) {
				errorMessage(ex);
			}
		}
	}

	/*
	 * ----------------------------------メイン関数---------------------------------
	 */

	public static void main(String args[]) {
		Main Menuframe = new Main();
		Menuframe.setVisible(true);

		flagWait();
		Menuframe.setVisible(false);
		client.OpenGame(font);
		flag = false;

	}
}