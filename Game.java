import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

public class Game extends JPanel {

	// ATRIBUTOS ---------------------------------------------------------
	public static Recursos recursos;

	public Braid braid;
	public GeradorMostros geradorMostros;
	public Piso piso;
	public Fundo fundo;

	// os estados de pressionamento das teclas direcionais
	public boolean k_cima = false;
	public boolean k_baixo = false;
	public boolean k_direita = false;
	public boolean k_esquerda = false;

	// variáveis de tempo
	long tempoAtual,tempoAnterior,tempoDelta;

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
		geradorMostros = new GeradorMostros();
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
		tempoAnterior=System.currentTimeMillis();
		while (true) {
			tempoAtual = System.currentTimeMillis(); // tempo inicial desse quadro
			tempoDelta = tempoAtual - tempoAnterior; // quanto tempo se passou desde o ultimo quadro

			handlerEvent();
			update(tempoDelta);
			render();

			tempoAnterior = tempoAtual; // tempo inicial do quadro anterior

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

	public void update(long tempoDelta) {
		piso.update();
		// atualiza o fundo
		fundo.fundo1PosX = fundo.fundo1PosX + fundo.fundovelX;
		fundo.fundo2PosX = fundo.fundo2PosX + fundo.fundovelX;
		fundo.remontarFundo();
		// atualiza o personagem
		braid.update();
		braid.mudarQuadro(tempoDelta);
		// atualiza os monstros
		geradorMostros.update(tempoDelta);

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
		geradorMostros.render(g); // pinta os monstros na tela
		g.drawImage(braid.obterQuadro(), braid.posX, braid.posY, null);
		piso.render(g);
		Toolkit.getDefaultToolkit().sync(); // bug do linux
	}
}
