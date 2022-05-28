import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.FileWriter;

import javax.swing.JPanel;

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
	long tempoDecorridoGameOver,tempoJogo,tempoJogoFinal,tempoGameOver,tempoCarregando;

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

		tempoDecorridoGameOver = 0;
		tempoJogo = 0;
		tempoGameOver = 2000;
		tempoCarregando = 2000;

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
			tempoJogo+=tempoDelta;

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
				recursos.tocarSomPulo();
			}
		}
	}

	public void update(long tempoDelta) {
		if(ESTADO == Estado.EXECUTANDO){
			piso.update();
			// atualiza o fundo
			fundo.update();
			// atualiza o personagem
			tim.update();
			tim.mudarQuadro(tempoDelta);
			// atualiza os monstros
			geradorMostros.update(tempoDelta);

			testeColisoes();
		}else if(ESTADO==Estado.GAMEOVER){
			tempoDecorridoGameOver+=tempoDelta;
			if(tempoDecorridoGameOver>=tempoGameOver){
				tempoDecorridoGameOver=0;
				reiniciaJogo();
			}
		}else if(ESTADO==Estado.CARREGANDO){
			if(tempoJogo>tempoCarregando){
				reiniciaJogo();
			}
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
			recursos.tocarSomQueda();
		}

		// verifica a colisão do personagem com os monstros bola
		for(MonstroBola m : geradorMostros.listaMonstrosBola){
			if(tim.colX+tim.colLargura>=m.colX // lado esquerdo do monstro
			&& tim.colX<=m.colX+m.colLargura // lado direito do monstro
			&& tim.colY+tim.colAltura>=m.colY // lado superior do monstro
			&& tim.colY<=m.colY+m.colAltura // lado inferior do monstro
			){
				recursos.tocarSomQueda();
				ESTADO = Estado.GAMEOVER;
				tim.estado = Tim.Estado.MORTO;
				tempoJogoFinal = tempoJogo;
				recursos.pararMusicaJogo();
			}
		}

		// verifica a colisão do personagem com os monstros dino
		for(MonstroDino m : geradorMostros.listaMonstrosDino){
			if(tim.colX+tim.colLargura>=m.colX // lado esquerdo do monstro
			&& tim.colX<=m.colX+m.colLargura // lado direito do monstro
			&& tim.colY+tim.colAltura>=m.colY // lado superior do monstro
			&& tim.colY<=m.colY+m.colAltura // lado inferior do monstro
			){
				recursos.tocarSomQueda();
				ESTADO = Estado.GAMEOVER;
				tim.estado = Tim.Estado.MORTO;
				tempoJogoFinal = tempoJogo;
				recursos.pararMusicaJogo();
			}
		}
	}

	// METODOS ESPECIAIS ---------------------------------------------------------

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(recursos.fontTexto);
		g.setColor(Color.WHITE);

		if(ESTADO==Estado.CARREGANDO){	
			g.drawImage(recursos.telaLoading, 0,0, null);
			return;
		}

		// desenha o personagem na tela
		g.drawImage(fundo.fundo1, (int)fundo.fundo1PosX, (int)fundo.fundoPosY, null);
		g.drawImage(fundo.fundo2, (int)fundo.fundo2PosX, (int)fundo.fundoPosY, null);
		geradorMostros.render(g); // pinta os monstros na tela
		tim.render(g);
		piso.render(g);
		
		
		if(ESTADO==Estado.GAMEOVER){
			g.drawImage(recursos.telaGameOver, 0, 0, null);
			g.drawString("Tempo: "+(tempoJogoFinal/1000.0)+"s", (int)(Principal.LARGURA_TELA*0.36),(int)(Principal.ALTURA_TELA*0.5) );
			if(tempoJogoFinal>recursos.recorde){
				g.drawString("NOVO RECORDE!", (int)(Principal.LARGURA_TELA*0.36),(int)(Principal.ALTURA_TELA*0.65) );
			}
		}else{
			g.drawString("Tempo: "+tempoJogo/1000, 10, 30);
			g.drawString("Recorde: "+(recursos.recorde/1000.0)+"s", 10, 70);
		}
		Toolkit.getDefaultToolkit().sync(); // bug do linux		
	}

	public void reiniciaJogo(){
		ESTADO=Estado.EXECUTANDO;
		recursos.tocarMusicaJogo();
		verificaRecorde();
		Game.recursos.velocidadeJogo=1.0;
		tempoJogo = 0;
		tempoJogoFinal = 0;
		tim.reiniciaJogo();
		geradorMostros.reiniciaJogo();
		piso.reiniciaJogo();
		fundo.reiniciaJogo();
	}

	public void verificaRecorde(){
		if(tempoJogoFinal>recursos.recorde){
			recursos.recorde = tempoJogoFinal;
			salvarRecorde();		
		}
	}

	public void salvarRecorde(){
		BufferedWriter writer;
		try{
			String arquivo = "recorde.txt";
   			writer = new BufferedWriter(new FileWriter(arquivo));
			writer.write(""+recursos.recorde);
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public enum Estado{
		CARREGANDO, EXECUTANDO, GAMEOVER;
	}
}
