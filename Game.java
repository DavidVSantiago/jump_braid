import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

public class Game extends JPanel {

	// ATRIBUTOS ---------------------------------------------------------
	public static Recursos recursos;

	public Braid braid;
	public Piso piso;
	public Fundo fundo;

	// os estados de pressionamento das teclas direcionais
	public boolean k_cima = false;
	public boolean k_baixo = false;
	public boolean k_direita = false;
	public boolean k_esquerda = false;

	// CONSTRUTOR ---------------------------------------------------------
	public Game() {
		recursos = new Recursos();
		// adiciona o escutador de eventos de pressionamento do teclado
		requestFocus();
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

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
		piso = new Piso();
		fundo = new Fundo();

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
		while (true) {
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

	public void handlerEvent() {

	}

	public void update() {
		// movimenta o piso
		piso.piso1PosX = piso.piso1PosX + piso.pisovelX;
		piso.piso2PosX = piso.piso2PosX + piso.pisovelX;
		piso.remontarPiso();
		// movimenta o fundo
		fundo.fundo1PosX = fundo.fundo1PosX + fundo.fundovelX;
		fundo.fundo2PosX = fundo.fundo2PosX + fundo.fundovelX;
		fundo.remontarFundo();

		braid.mudarQuadro();

		testeColisao();
	}

	public void render() {
		repaint(); // forçar o redesenho da tela
	}

	// METODOS ---------------------------------------------------------
	public void testeColisao() {

	}

	// METODOS ESPECIAIS ---------------------------------------------------------

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// desenhar os elementos na tela
		setBackground(Color.LIGHT_GRAY);

		// desenha o personagem na tela
		g.drawImage(fundo.fundo1, fundo.fundo1PosX, fundo.fundo1posY, null);
		g.drawImage(fundo.fundo2, fundo.fundo2PosX, fundo.fundo2posY, null);
		g.drawImage(piso.piso1, piso.piso1PosX, piso.piso1posY, null);
		g.drawImage(piso.piso2, piso.piso2PosX, piso.piso2posY, null);
		g.drawImage(braid.obterQuadro(), braid.posX, braid.posY, null);
	}
}
