import java.awt.image.BufferedImage;
import java.awt.Font;
import javax.imageio.ImageIO;

public class Recursos {
	// atributos
	BufferedImage spriteBraidRun,spriteBraidJump,braidDied;
	BufferedImage spriteMostroBola,spriteMostroDino;
	BufferedImage imgGround,imgFundo;
	public Font fontGameOver;
	
	// construtor
	public Recursos() {
		fontGameOver = new Font("Arial narrow", Font.CENTER_BASELINE, 50);
		// carrega os sprites a partir do arquivo
		try {
			spriteBraidJump = ImageIO.read(getClass().getResource("/imgs/braid-jump.png"));
			spriteBraidRun = ImageIO.read(getClass().getResource("/imgs/braid-run.png"));
			braidDied = ImageIO.read(getClass().getResource("/imgs/braid-died.png"));
			spriteMostroBola = ImageIO.read(getClass().getResource("/imgs/monster-ball.png"));
			spriteMostroDino = ImageIO.read(getClass().getResource("/imgs/monster-dino.png"));
			imgGround = ImageIO.read(getClass().getResource("/imgs/ground.png"));
			imgFundo = ImageIO.read(getClass().getResource("/imgs/fundo.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}