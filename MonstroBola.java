import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;

/* Representa o personagem do jogo */
public class MonstroBola { 

	// variáveis das imagens
	public BufferedImage[] correndoQuadros;
	public BufferedImage correndoSprite;
	public int correndoQtdQuadros, correndoLargura, correndoAltura;
	public int correndoTempoQuadro;
	public int correndoIndexAtual;

	public double posX, posY, velX; // variáveis de posição e velocidade
	public double colX, colY, colLargura, colAltura;
	Color colColor;

	long tempoDecorrido;
	
	public MonstroBola() {
		correndoSprite = Game.recursos.spriteMostroBola;
		correndoQtdQuadros = 16;
		correndoQuadros = new BufferedImage[correndoQtdQuadros];
		correndoLargura = 102;
		correndoAltura = 100;
		correndoIndexAtual = 0;
		correndoTempoQuadro = 30;
		
		reposicionar();

		colLargura = 72;
		colAltura = 80;
		colColor = new Color(255, 0, 0, 128); // vermelho transparente

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
		colX = posX+15;
		colY = posY+10;
	}

	public void update(){
		velX = -7*Game.recursos.velocidadeJogo;
		posX = posX+velX;
		colX = colX+velX;
	}

	public void render(Graphics g){
        g.drawImage(obterQuadro(), (int)posX, (int)posY, null);
		//g.setColor(colColor);
		//g.fillRect(colX, colY, colLargura, colAltura);
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

	public void reiniciaJogo(){
		correndoIndexAtual = 0;
		reposicionar();
	}

	public enum Estado{
		CORRENDO, PULANDO;
	}
}
