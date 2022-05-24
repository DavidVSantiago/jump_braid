import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Random;
import javax.swing.JPanel;

public class Game extends JPanel{
	
	// ATRIBUTOS ---------------------------------------------------------
	public Braid braid;
	// os estados de pressionamento das teclas direcionais
	public boolean k_cima = false;
	public boolean k_baixo = false;
	public boolean k_direita = false;
	public boolean k_esquerda = false;
	
	// CONSTRUTOR ---------------------------------------------------------
	public Game() {
		// adiciona o escutador de eventos de pressionamento do teclado
		
		addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) { // escutador de pressionamento de tecla
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP: // tecla cima
					k_cima = true;
					break;
				case KeyEvent.VK_DOWN: // tecla baixo
					k_baixo = true;
					break;
				case KeyEvent.VK_LEFT: // tecla esquerda
					k_esquerda = true;
					break;
				case KeyEvent.VK_RIGHT: // tecla direita
					k_direita = true;
					break;
				}
			}
			@Override
			public void keyReleased(KeyEvent e) { // escutados da soltura de tecla
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP: // tecla cima
					k_cima = false;
					break;
				case KeyEvent.VK_DOWN: // tecla baixo
					k_baixo = false;
					break;
				case KeyEvent.VK_LEFT: // tecla esquerda
					k_esquerda = false;
					break;
				case KeyEvent.VK_RIGHT: // tecla direita
					k_direita = false;
					break;
				}
			}
			
		});
		
		braid = new Braid();
		setFocusable(true); // para poder tratar eventos
		setLayout(null);
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				gameloop(); // dispara o gameloop do jogo
			}
		});
		thread.start();
	}
	
	
	// GAMELOOP ---------------------------------------------------------
	public void gameloop() {
		while(true) { // cada repetição é um quadro do jogo
			handlerEvent();
			update();
			render();

			try {// dando uma pause de 17 milisegundos (60FPS)
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
	}
	
	public void handlerEvent(){
		
	}
	public void update() {
		// movimenta a bola
		braid.posX = braid.posX + braid.velX;
		testeColisao();
		braid.mudarQuadro();
	}
	public void render() {
		repaint(); // forçar o redesenho da tela
	}
	
	// MÉTODOS ---------------------------------------------------------
	public void testeColisao() {
		
		
	}
	
	
	// MÉTODOS ESPECIAIS ---------------------------------------------------------
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// desenhar os elementos na tela
		setBackground(Color.LIGHT_GRAY);
		
		// desenha o personagem na tela
		//g.fillOval(bola.posX, bola.posY, bola.raio*2, bola.raio*2);
		g.drawImage(braid.imgAtual, braid.posX, braid.posY, null);
	}
}
