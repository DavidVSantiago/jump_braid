import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Recursos {
	// atributos
	BufferedImage spriteBraidRun,imgGround,imgFundo;
	
	// construtor
	public Recursos() {
		try {
			spriteBraidRun = ImageIO.read(getClass().getResource("/imgs/braid-run.png"));
			imgGround = ImageIO.read(getClass().getResource("/imgs/ground.png"));
			imgFundo = ImageIO.read(getClass().getResource("/imgs/fundo.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}