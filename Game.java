import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

public class Game extends JPanel {

	// ATRIBUTOS ---------------------------------------------------------
	public static Recursos recursos;

	public Braid braid;
	public MonstroBola monstroBola;
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
				}
			}

			@Override
			public void keyReleased(KeyEvent e) { // escutados da soltura de tecla
				switch (e.getKeyCode()) {
					case KeyEvent.VK_UP: // tecla cima
						k_cima = false;
						break;
				}
			}
		});

		braid = new Braid();
		monstroBola = new MonstroBola();
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
		if(k_cima && braid.estado!=Braid.Estado.PULANDO){
			braid.iniciarPulo();
		}
	}

	public void update() {
		// movimenta o piso
		piso.piso1PosX = piso.piso1PosX + piso.pisoVelX;
		piso.piso2PosX = piso.piso2PosX + piso.pisoVelX;
		piso.remontarPiso();
		// movimenta o fundo
		fundo.fundo1PosX = fundo.fundo1PosX + fundo.fundovelX;
		fundo.fundo2PosX = fundo.fundo2PosX + fundo.fundovelX;
		fundo.remontarFundo();
		// movimenta o personagem
		braid.update();
		monstroBola.update();

		braid.mudarQuadro();
		monstroBola.mudarQuadro();

		testeColisao();
	}

	public void render() {
		repaint(); // forçar o redesenho da tela
	}

	// METODOS ---------------------------------------------------------
	public void testeColisao() {
		// verifica a colisão do personagem com o chão
		if(braid.estado==Braid.Estado.PULANDO &&
			(braid.posY+braid.pulandoAltura)>=braid.posY_inicial+braid.pulandoAltura){
			braid.pararPulo();
		}
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
		g.drawImage(monstroBola.obterQuadro(), monstroBola.posX, monstroBola.posY, null);
		g.drawImage(braid.obterQuadro(), braid.posX, braid.posY, null);
	}
}
