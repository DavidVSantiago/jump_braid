import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import java.awt.Color;

public class Game extends JPanel {

	// ATRIBUTOS ---------------------------------------------------------
	public static Recursos recursos;

	public Tim tim;
	public GeradorMostros geradorMostros;
	public Piso piso;
	public Fundo fundo;

	// os estados de pressionamento das teclas direcionais
	public boolean k_cima = false;
	public boolean k_baixo = false;
	public boolean k_direita = false;
	public boolean k_esquerda = false;

	// variáveis de tempo
	public long tempoAtual,tempoAnterior,tempoDelta;

	// Estado do jogo
	public Estado ESTADO;
	long tempoDecorrido,tempoGameOver;
	Color pretoTransp;

	// CONSTRUTOR ---------------------------------------------------------
	public Game() {
		ESTADO = Estado.CARREGANDO;
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
					case KeyEvent.VK_Q:
						ESTADO = Estado.EXECUTANDO;
						break;
				}
			}
		});

		tim = new Tim();
		geradorMostros = new GeradorMostros();
		piso = new Piso();
		fundo = new Fundo();

		tempoDecorrido = 0;
		tempoGameOver = 2000;
		pretoTransp = new Color(0, 0, 0, 128); // preto transparente

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
		ESTADO = Estado.EXECUTANDO;
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
		if(ESTADO == Estado.EXECUTANDO){
			if(k_cima && tim.estado!=Tim.Estado.PULANDO){
				tim.iniciarPulo();
			}
		}
	}

	public void update(long tempoDelta) {
		if(ESTADO == Estado.EXECUTANDO){
			piso.update();
			// atualiza o fundo
			fundo.fundo1PosX = fundo.fundo1PosX + fundo.fundovelX;
			fundo.fundo2PosX = fundo.fundo2PosX + fundo.fundovelX;
			fundo.remontarFundo();
			// atualiza o personagem
			tim.update();
			tim.mudarQuadro(tempoDelta);
			// atualiza os monstros
			geradorMostros.update(tempoDelta);

			testeColisoes();
		}
	}

	public void render() {
		repaint(); // forçar o redesenho da tela
	}

	// METODOS ---------------------------------------------------------
	public void testeColisoes() {
		// verifica a colisão do personagem com o chão
		if(tim.estado==Tim.Estado.PULANDO &&
			(tim.posY+tim.pulandoAltura)>=tim.posY_inicial+tim.pulandoAltura){
			tim.pararPulo();
		}

		// verifica a colisão do personagem com os monstros bola
		for(MonstroBola m : geradorMostros.listaMonstrosBola){
			if(tim.colX+tim.colLargura>=m.colX // lado esquerdo do monstro
			&& tim.colX<=m.colX+m.colLargura // lado direito do monstro
			&& tim.colY+tim.colAltura>=m.colY // lado superior do monstro
			&& tim.colY<=m.colY+m.colAltura // lado inferior do monstro
			){
				ESTADO = Estado.GAMEOVER;
				tim.estado = Tim.Estado.MORTO;
			}
		}

		// verifica a colisão do personagem com os monstros dino
		for(MonstroDino m : geradorMostros.listaMonstrosDino){
			if(tim.colX+tim.colLargura>=m.colX // lado esquerdo do monstro
			&& tim.colX<=m.colX+m.colLargura // lado direito do monstro
			&& tim.colY+tim.colAltura>=m.colY // lado superior do monstro
			&& tim.colY<=m.colY+m.colAltura // lado inferior do monstro
			){
				ESTADO = Estado.GAMEOVER;
				tim.estado = Tim.Estado.MORTO;
			}
		}
	}

	// METODOS ESPECIAIS ---------------------------------------------------------

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// desenha o personagem na tela
		g.drawImage(fundo.fundo1, fundo.fundo1PosX, fundo.fundoPosY, null);
		g.drawImage(fundo.fundo2, fundo.fundo2PosX, fundo.fundoPosY, null);
		geradorMostros.render(g); // pinta os monstros na tela
		tim.render(g);
		piso.render(g);


		if(ESTADO==Estado.GAMEOVER){
			tempoDecorrido+=tempoDelta;

			g.setColor(pretoTransp);
			g.fillRect(0,0,Principal.LARGURA_TELA, Principal.ALTURA_TELA);
			
			g.setFont(recursos.fontGameOver);
			g.setColor(Color.WHITE);
			g.drawString("GAME OVER!",(int)(Principal.LARGURA_TELA*0.4),(int)(Principal.ALTURA_TELA*0.2));
			
			if(tempoDecorrido>=tempoGameOver){
				tempoDecorrido=0;
				reiniciaJogo();
				ESTADO=Estado.EXECUTANDO;
			}
		}

		Toolkit.getDefaultToolkit().sync(); // bug do linux		
	}

	public void reiniciaJogo(){
		tim.reiniciaJogo();
		geradorMostros.reiniciaJogo();
		piso.reiniciaJogo();
		fundo.reiniciaJogo();
	}

	public enum Estado{
		CARREGANDO, EXECUTANDO, PAUSADO, GAMEOVER;
	}
}
