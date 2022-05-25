import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Recursos {
	// atributos
	BufferedImage spriteBraidRun,spriteBraidJump;
	BufferedImage spriteMostroBola;
	BufferedImage imgGround,imgFundo;
	
	// construtor
	public Recursos() {
		// carrega os sprites a partir do arquivo
		try {
			spriteBraidJump = ImageIO.read(getClass().getResource("/imgs/braid-jump.png"));
			spriteBraidRun = ImageIO.read(getClass().getResource("/imgs/braid-run.png"));
			spriteMostroBola = ImageIO.read(getClass().getResource("/imgs/monster-ball.png"));
			imgGround = ImageIO.read(getClass().getResource("/imgs/ground.png"));
			imgFundo = ImageIO.read(getClass().getResource("/imgs/fundo.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}