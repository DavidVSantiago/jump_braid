import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/* Representa o personagem do jogo */
public class MonstroBola { 

	// variáveis das imagens
	public BufferedImage[] correndoQuadros;
	public BufferedImage correndoSprite;
	public int correndoQtdQuadros, correndoLargura, correndoAltura;
	public int correndoTempoQuadro;
	public int correndoIndexAtual;

	public int posX, posY, velX; // variáveis de posição e velocidade

	long tempoDecorrido;
	
	public MonstroBola() {
		correndoSprite = Game.recursos.spriteMostroBola;
		correndoQtdQuadros = 16;
		correndoQuadros = new BufferedImage[correndoQtdQuadros];
		correndoLargura = 102;
		correndoAltura = 100;
		correndoIndexAtual = 0;
		correndoTempoQuadro = 30;
		
		posX=1000;
		posY=298;
		velX=-7;
		tempoDecorrido = 0;
		
		// recorte dos sprites -------------------------------
		for(int i=0; i<correndoQtdQuadros; i++) {
			int x1 = correndoLargura*i;
			int y1 = 0;
			correndoQuadros[i] = correndoSprite.getSubimage(x1,y1,correndoLargura, correndoAltura);
		}
	}
	public void reposicionar(){
		posX=1000;
		posY=338;
	}

	public void update(){
		posX = posX+velX;
	
	}
	
	public void mudarQuadro(long tempoDelta) {
		tempoDecorrido += tempoDelta;

		if (tempoDecorrido > correndoTempoQuadro) {
			correndoIndexAtual++;
			if (correndoIndexAtual >= correndoQtdQuadros)
				correndoIndexAtual = 0;
			tempoDecorrido = 0;
		}
	}

	public BufferedImage obterQuadro(){
		return correndoQuadros[correndoIndexAtual];
	}

	public enum Estado{
		CORRENDO, PULANDO;
	}
}
