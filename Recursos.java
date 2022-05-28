import java.awt.image.BufferedImage;
import java.awt.Font;
import javax.imageio.ImageIO;

public class Recursos {
	// atributos
	BufferedImage spriteBraidRun,spriteBraidJump,braidDied;
	BufferedImage spriteMostroBola,spriteMostroDino;
	BufferedImage imgGround,imgFundo, telaGameOver;
	public Font fontTexto;
	public double velocidadeJogo, fatorAceleracao;
	long recorde;
	
	// construtor
	public Recursos() {
		recorde = 0;
		velocidadeJogo = 1.0;
		fatorAceleracao = 0.08;
		fontTexto = new Font("Arial narrow", Font.CENTER_BASELINE, 30);
		// carrega os sprites a partir do arquivo
		try {
			spriteBraidJump = ImageIO.read(getClass().getResource("/imgs/braid-jump.png"));
			spriteBraidRun = ImageIO.read(getClass().getResource("/imgs/braid-run.png"));
			braidDied = ImageIO.read(getClass().getResource("/imgs/braid-died.png"));
			spriteMostroBola = ImageIO.read(getClass().getResource("/imgs/monster-ball.png"));
			spriteMostroDino = ImageIO.read(getClass().getResource("/imgs/monster-dino.png"));
			imgGround = ImageIO.read(getClass().getResource("/imgs/ground.png"));
			imgFundo = ImageIO.read(getClass().getResource("/imgs/fundo.png"));
			telaGameOver = ImageIO.read(getClass().getResource("/imgs/game-over-screen.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}