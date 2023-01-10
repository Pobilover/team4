package main.minigame.different;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.minigame.GameDialog;

public class BulletGame extends JDialog {
	private MiniGame miniGame;
	
	public BulletGame(GameDialog gameDialog) {
		setUndecorated(true);
		setModal(true);
		setLayout(null);
		
		setBackground(new Color(0, 0, 0, 80));
		
		setBounds(500, 400, 350, 150);
		
		JButton btn = new JButton("시작하기");
		btn.setBounds(120, 50, 100, 50);
		add(btn);
//		btn.setBorderPainted(false);
//		btn.setFocusPainted(false);
//		btn.setContentAreaFilled(false);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				miniGame = new MiniGame(gameDialog);
				dispose();
			}
		});
		
	}
	public MiniGame getMiniGame() {
		return miniGame;
	}
	public void setMiniGame(MiniGame miniGame) {
		this.miniGame = miniGame;
	}
	public void showGUI() {
		setVisible(true);
	}
}

class MiniGame extends JDialog {
	private List<JLabel> lbl;
	private List<Bullet> bul;
	private int x;
	private int y;
	private int size;
	private int score;
	private boolean life;
	private PointerInfo pt;
	private JLabel lblScore;
	private int time;
	
	public MiniGame(GameDialog gameDialog) {
		setUndecorated(true);
		setModal(true);
		setLayout(null);;
		
		life = true;
		size = 20;
		score = 0;
		time = 0;
		
		x = 500;
		y = 500 - 7;
		
		lbl = new ArrayList<>();
		bul = new ArrayList<>();
		
		create(30);
		createT(30, 10000);
		createT(30, 20000);
		move(gameDialog);
		mouse();
		
		lblScore = new JLabel(String.valueOf(score));
		lblScore.setBounds(440, 5, 50, 10);
		add(lblScore);
		
		setBounds(300, 300, x, y);
		setVisible(true);
	}
	
	public void move(GameDialog gameDialog) {
		Timer timer=new Timer();
		TimerTask task=new TimerTask(){
		    @Override
		    public void run() {
				if(life){ // 수행
					if (bul.size() >= time && lbl.size() >= time){
						pt = MouseInfo.getPointerInfo();
						try {
							for (int i = 0; i < bul.size(); i++) {
								bul.get(i).setX(bul.get(i).getX() + bul.get(i).getMoveX());
								bul.get(i).setY(bul.get(i).getY() + bul.get(i).getMoveY());
								
								if (bul.get(i).getX() <= 0 || bul.get(i).getX() >= x || bul.get(i).getY() <= 0 || bul.get(i).getY() >= y) {
									value(bul.get(i));
								}
								lbl.get(i).setBounds(bul.get(i).getX(), bul.get(i).getY(), size, size);
							}
						} catch (IndexOutOfBoundsException e) {
						}
					}

					if (0 <= pt.getLocation().x - 300 && pt.getLocation().x - 300 < x
							&& 0 <= pt.getLocation().y - 300 && pt.getLocation().y - 300 < y) {
						score += 1;
					} else {
						score -= 3;
						if (score < 0) {
							score = 0;
						}
					}
					
					lblScore.setText(String.valueOf(score));
				} else {
					timer.cancel(); // 타이머 종료
					dispose();
					gameDialog.setScore(score);
					new BulletScoreDialog(score);
				}
		    }
		};
		timer.schedule(task, 0, 17); // 실행 Task, 1초뒤 실행, 반복
	}
	
	public void mouse() {
		Timer timer=new Timer();
		TimerTask task=new TimerTask(){
			@Override
			public void run() {
				if(life){ // 수행
					pt = MouseInfo.getPointerInfo();
					for (int i = 0; i < bul.size(); i++) {
						if (bul.get(i).getX() <= pt.getLocation().x - 300 && pt.getLocation().x - 300 < bul.get(i).getX() + size
								&& bul.get(i).getY() <= pt.getLocation().y - 300 && pt.getLocation().y - 300 < bul.get(i).getY() + size) {
							life = false;
						}
					}
				} else {
					timer.cancel(); // 타이머 종료
				}
			}
		};
		timer.schedule(task, 0, 1); // 실행 Task, 1초뒤 실행, 반복
	}
	
	public void createT(int c, int time) {
		Timer timer=new Timer();
		TimerTask task=new TimerTask(){
			@Override
			public void run() {
				if(life){ // 수행
					create(c);
				} else {
					timer.cancel(); // 타이머 종료
				}
			}
		};
		timer.schedule(task, time, time); // 실행 Task, 1초뒤 실행, 반복
	}
	
	public void create(int a) {
		for (int i = 0; i < a; i++) {
			bul.add(new Bullet());
			value(bul.get(bul.size() - 1));
			lbl.add(new JLabel());
			lbl.get(bul.size() - 1).setBounds(bul.get(bul.size() - 1).getX(), bul.get(bul.size() - 1).getY(), size, size);
			lbl.get(bul.size() - 1).setBackground(new Color(255, 0, 0));
			lbl.get(bul.size() - 1).setOpaque(true); 
			add(lbl.get(bul.size() - 1));
		}
		time += a;
	}
	
	public void value(Bullet bullet) {
		Random random = new Random();
		int bulX = 0;
		int bulY = 0;
		
		if (random.nextInt(4) == 0) {
			bulX = random.nextInt(x);
		} else if (random.nextInt(4) == 1) {
			bulY = random.nextInt(y);
		} else if (random.nextInt(4) == 2) {
			bulX = random.nextInt(x);
			bulY = y;
		} else {
			bulX = x;
			bulY = random.nextInt(y);
		}
		
		int bulMoveX = 0;
		int bulMoveY = 0;
		do {
			bulMoveX = random.nextInt(11) - 5;
			bulMoveY = random.nextInt(11) - 5;
		} while (bulMoveX == 0 && bulMoveY == 0);
		
		bullet.setX(bulX);
		bullet.setY(bulY);
		bullet.setMoveX(bulMoveX);
		bullet.setMoveY(bulMoveY);
	}
}

class BulletScoreDialog extends JDialog {
	public BulletScoreDialog(int score) {
		setUndecorated(true);
		setModal(true);
		setLayout(null);;
		
		JLabel lbl = new JLabel(score + "점 입니다. ");
		if(score < 100) {
			lbl.setBounds(70, 30, 80, 20);
		} else if (score < 1000) {
			lbl.setBounds(65, 30, 100, 20);
		} else if (score < 10000) {
			lbl.setBounds(60, 30, 120, 20);
		} else if (score < 100000) {
			lbl.setBounds(55, 30, 140, 20);
		} else {
			lbl.setBounds(50, 30, 160, 20);
		}
		add(lbl);
		
		JButton btn = new JButton("확인");
		btn.setBounds(70, 90, 60, 20);
		add(btn);
		
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		setBounds(500, 400, 200, 120);
		setVisible(true);
	}
}

class Bullet {
	private int x;
	private int y;
	private int moveX;
	private int moveY;
	public Bullet() {
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getMoveX() {
		return moveX;
	}
	public void setMoveX(int moveX) {
		this.moveX = moveX;
	}
	public int getMoveY() {
		return moveY;
	}
	public void setMoveY(int moveY) {
		this.moveY = moveY;
	}
	
}