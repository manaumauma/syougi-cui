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
	private static int maxConnection = 2; // �v���C���[���i2 �j
	private static String myName; // �����̖��O
	private static int myNumber; // �����̔ԍ�
	private static String font = "�l�r �S�V�b�N"; // �t�H���g�̎w��i������������ꍇ������ς��Ă��������j
	private static Socket svSocket;// �N���C�A���g�������͂���T�[�o�[�̃\�P�b�g
	private static SyougiServer server;
	private static SyougiClient client;
	private static String name[] = new String[5];
	private static JPanel menuPanel[] = new JPanel[7]; // �e���j���[�̃p�l��
	private static JLabel background[] = new JLabel[7]; // �e���j���[��ʂ̔w�i
	private static JLabel waitLabel[] = new JLabel[3];
	private static String roomIPText = null;
	private Container c = getContentPane(); // �R���e�i
	private static boolean flag = false; // ��ʑJ�ڂ�҂�����t���O

	/*
	 * -------------------------------------�R���X�g���N�^-----------------------------
	 * ---------
	 */
	Main() {

		/* �^�C�g����o�^ */
		setTitle("�͂��ݏ���");

		/* ��ʂ̑傫�� */
		setSize(500, 500);
		setResizable(false);

		/* ��ʈʒu(�^��) */
		setLocationRelativeTo(null);

		/* �E�B���h�E�����������v���O�������I������悤�ɂ��� */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* �A�C�R�� */
		setIconImage(new ImageIcon(Main.class.getResource("image/icon.jpg")).getImage());

		/* ���j���[�p�l�� */
		for (int i = 0; i < 7; i++) {
			menuPanel[i] = new JPanel();
			menuPanel[i].setLayout(null);
		}

		/* �w�i */
		for (int i = 0; i < 7; i++) {
			background[i] = new JLabel();
			background[i].setBounds(0, 0, 500, 500);
		}

		JLabel label4 = new JLabel();

		/* -----------------------�X�^�[�g���(0)----------------------- */
		// �^�C�g������
		JLabel label0 = new JLabel("�͂��ݏ���", JLabel.CENTER);
		label0.setBounds(20, 180, 300, 90); // �ύX(20,180,300,100)
		label0.setFont(new Font(font, Font.BOLD, 50));
		label0.setForeground(Color.RED);
		menuPanel[0].add(label0);
		// �X�^�[�g�{�^��
		JButton button0 = new JButton("START");
		menuPanel[0].add(button0);
		button0.setFont(new Font(font, Font.BOLD, 45));
		button0.setBounds(290, 370, 180, 80);
		button0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePanel(0, 1);
			}
		});
		// �w�i
		background[0].setIcon(new ImageIcon(Main.class.getResource("image/syougi.jpg")));
		menuPanel[0].add(background[0]);

		c.add(menuPanel[0]);

		/* -----------------------���O�̓���(1)----------------------- */
		// �w����
		JLabel label1 = new JLabel("<html>���O����͂��Ă�������<br>�i8�����ȓ��j</html>");
		label1.setBounds(70, 50, 400, 150);
		label1.setFont(new Font(font, Font.BOLD, 30));
		menuPanel[1].add(label1);
		// ���̓G���A
		JTextField nameText = new JTextField(10);
		nameText.setBounds(100, 200, 300, 90);
		nameText.setFont(new Font(font, Font.BOLD, 45));
		menuPanel[1].add(nameText);
		// ����{�^��
		JButton button1 = new JButton("����");
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
		// �w�i
		background[1].setIcon(new ImageIcon(Main.class.getResource("image/enter.jpg")));
		menuPanel[1].add(background[1]);

		/*
		 * -----------------------(���������/�����ɓ���)�I�����(�eor�q)(2)--------------------
		 * ---
		 */
		// ���������
		JButton makeBtn = new JButton("���������");
		menuPanel[2].add(makeBtn);
		makeBtn.setFont(new Font(font, Font.BOLD, 42));
		makeBtn.setBounds(100, 100, 300, 100);
		makeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (makeRoom()) {
					label4.setText("<html>������IP�A�h���X<br>" + roomIPText + "</html>");
					changePanel(2, 4);
				}
			}
		});
		// �����ɓ���
		JButton enterBtn = new JButton("�����ɓ���");
		menuPanel[2].add(enterBtn);
		enterBtn.setFont(new Font(font, Font.BOLD, 42));
		enterBtn.setBounds(100, 300, 300, 100);
		enterBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePanel(2, 3);
			}
		});
		// �w�i
		background[2].setIcon(new ImageIcon(Main.class.getResource("image/door.jpg")));
		menuPanel[2].add(background[2]);

		/* -----------------------(�q�̂�)������IP�A�h���X�̓���(3)----------------------- */
		// �w����
		JLabel label3 = new JLabel("<html>������IP�A�h���X����͂��Ă�������<br>(����͂̏ꍇ�A���g�̃��[�J���z�X�g�ɐڑ����܂�)</html>");
		label3.setBounds(50, 10, 400, 130);
		label3.setFont(new Font(font, Font.BOLD, 28));
		label3.setBackground(Color.WHITE);
		label3.setOpaque(true); // �s������
		menuPanel[3].add(label3);
		// ���̓G���A
		JTextField ipText = new JTextField(10);
		ipText.setBounds(80, 265, 340, 60);
		ipText.setFont(new Font(font, Font.BOLD, 30));
		menuPanel[3].add(ipText);
		// �ڑ��{�^��
		JButton button3 = new JButton("�ڑ�");
		menuPanel[3].add(button3);
		button3.setFont(new Font(font, Font.BOLD, 25));
		button3.setBounds(200, 360, 100, 60);
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					roomIPText = ipText.getText();
					/* ���͂��ꂽIP�A�h���X�Ń\�P�b�g�ʐM���s�� */
					client = new SyougiClient(myName, new Socket(roomIPText, 8080)); // SyougiClient��ς���K�v����B
					client.start();

					label4.setText("<html>������IP�A�h���X<br>" + roomIPText + "</html>");
					changePanel(3, 4);
				} catch (Exception ex) {
					errorMessage(ex);
				}
			}
		});
		background[3].setIcon(new ImageIcon(Main.class.getResource("image/connect.jpg")));
		menuPanel[3].add(background[3]);

		/* -----------------------�Q���҂�҂��(4)----------------------- */
		// �����ł̑҂���ʂ̖��O���x��
		for (int i = 1; i <= 2; i++) {
			// �w����
			waitLabel[i] = new JLabel(i + ":");
			waitLabel[i].setFont(new Font(font, Font.BOLD, 25));
			waitLabel[i].setBounds(115, 70 + i * 36, 370, 36);
			waitLabel[i].setForeground(Color.RED);
			waitLabel[i].setBackground(Color.WHITE);
			menuPanel[4].add(waitLabel[i]);
		}
		// IP�A�h���X�\��
		label4.setBounds(120, 15, 250, 60);
		label4.setFont(new Font(font, Font.BOLD, 25));
		label4.setForeground(Color.RED);
		menuPanel[4].add(label4);
		// �w�i
		background[4].setIcon(new ImageIcon(Main.class.getResource("image/wait.jpg")));
		menuPanel[4].add(background[4]);

	}

	/*
	 * ----------------------------------�ȉ��A���\�b�h--------------------------------
	 * -
	 */

	/* �G���[���b�Z�[�W�̕\�� */
	static void errorMessage(Exception ex) {
		JFrame messageFrame = new JFrame();
		JOptionPane.showMessageDialog(messageFrame, "<html>�G���[���������܂���<br>" + ex + "</html>");
	}

	/* ���j���[��ʂ�i����j�ɕς��� */
	void changePanel(int i, int j) {
		menuPanel[i].setVisible(false);
		c.add(menuPanel[j]);
		menuPanel[j].setVisible(true);
		c.remove(menuPanel[i]);
	}

	/* ���������e���A�T�[�o�[�𗧂��グ�A���g���q�Ƃ��ĕ����ɓ��� */
	static boolean makeRoom() {
		try {
			server = new SyougiServer();
			server.start();
			InetAddress roomIP = InetAddress.getLocalHost();
			client = new SyougiClient(myName, new Socket(roomIP, 8080));
			client.start();

			/* IP�A�h���X�𐔎����������ɂ��ėp�� */
			String divRoomIPText[] = roomIP.toString().split("/", 0);
			roomIPText = divRoomIPText[1];

			return true;
		} catch (Exception ex) {
			errorMessage(ex);
			return false;
		}
	}

	/* �����ő��̎Q���҂�҂��Ă���Ԃɖ��O��\�� */
	static void waitRoom(int i, String str) {
		name[i] = str;
		waitLabel[i].setText(i + ":" + name[i]);
	}

	static void setflag() {
		flag = true;
	}

	/* flag��true�ɂȂ�܂ő҂� */
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
	 * ----------------------------------���C���֐�---------------------------------
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