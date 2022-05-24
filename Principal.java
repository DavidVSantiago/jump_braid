import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Principal {

	public static final int LARGURA_TELA = 920;
	public static final int ALTURA_TELA = 480;
	
	// construtor
	public Principal() {

		// Criação do objeto que representa a janela da aplicação
		JFrame janela = new JFrame("Jogo 2D");
		Game game = new Game();
		// configurar os diversos aspectos da janela
		game.setPreferredSize(new Dimension(LARGURA_TELA, ALTURA_TELA));
		janela.setResizable(false); // evita o redimensionamento da janela
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setLocation(100,100); // define a posição de ancoragem da janela
		janela.setVisible(true); // torna a janela visível na tela
		janela.getContentPane().add(game);
		janela.pack();
	}
	
	public static void main(String[] args) {
		new Principal();
	}
}
