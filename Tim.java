import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;

/* Representa o personagem do jogo */
public class Tim {

	// variáveis das imagens
	public BufferedImage[] correndoQuadros;
	public BufferedImage correndoSprite;
	public BufferedImage mortoImagem;
	public int correndoQtdQuadros, correndoLargura, correndoAltura;
	public int correndoTempoQuadro;
	public int correndoIndexAtual;
	public BufferedImage[] pulandoQuadros;
	public BufferedImage pulandoSprite;
	public int pulandoQtdQuadros, pulandoLargura, pulandoAltura;
	public int pulandoTempoQuadro;
	public int pulandoIndexAtual;

	public int posX, posY, posY_inicial, velY; // variáveis de posição e velocidade
	public int colX, colY, colLargura, colAltura;
	Color colColor;

	long tempoDecorrido;

	public Estado estado; // variável que controla o estado

	public Tim() {
		correndoSprite = Game.recursos.spriteBraidRun;
		correndoQtdQuadros = 27;
		correndoQuadros = new BufferedImage[correndoQtdQuadros];
		correndoLargura = 130;
		correndoAltura = 150;
		correndoIndexAtual = 0;
		correndoTempoQuadro = 15;
		pulandoSprite = Game.recursos.spriteBraidJump;
		pulandoQtdQuadros = 15;
		pulandoQuadros = new BufferedImage[pulandoQtdQuadros];
		pulandoLargura = 130;
		pulandoAltura = 150;
		pulandoIndexAtual = 0;
		pulandoTempoQuadro = 50;
		mortoImagem = Game.recursos.braidDied;

		posX = 30;
		posY_inicial = 295;
		posY = posY_inicial;
		velY = 0;
		colX = posX + 25;
		colY = posY + 10;
		colLargura = 75;
		colAltura = 130;
		colColor = new Color(0, 0, 255, 128); // azul transparente

		estado = Estado.CORRENDO;
		tempoDecorrido = 0;

		// recorte dos sprites -------------------------------
		for (int i = 0; i < correndoQtdQuadros; i++) {
			int x1 = correndoLargura * i;
			int y1 = 0;
			correndoQuadros[i] = correndoSprite.getSubimage(x1, y1, correndoLargura, correndoAltura);
		}
		for (int i = 0; i < pulandoQtdQuadros; i++) {
			int x1 = pulandoLargura * i;
			int y1 = 0;
			pulandoQuadros[i] = pulandoSprite.getSubimage(x1, y1, pulandoLargura, pulandoAltura);
		}
	}

	public void update() {
		if (estado == Estado.PULANDO) {
			posY = posY + velY;
			colY = colY + velY;
			velY++;
		}
	}

	public void render(Graphics g) {
		g.drawImage(obterQuadro(), posX, posY, null);
		// g.setColor(colColor);
		// g.fillRect(colX, colY, colLargura, colAltura);
	}

	public void iniciarPulo() {
		pulandoIndexAtual = 0;
		velY = -21; // inicia o pula
		estado = Tim.Estado.PULANDO;
	}

	public void pararPulo() {
		correndoIndexAtual = 12;
		velY = 0;
		estado = Tim.Estado.CORRENDO; // para o pulo
		posY = posY_inicial;
	}

	public void mudarQuadro(long tempoDelta) {

		tempoDecorrido += tempoDelta;

		if (estado == Estado.CORRENDO) {
			// realiza a mudança de quadro apenas após o tempo decorrido
			if (tempoDecorrido > correndoTempoQuadro) {
				correndoIndexAtual++;
				if (correndoIndexAtual >= correndoQtdQuadros)
					correndoIndexAtual = 0;
				tempoDecorrido = 0;
			}
		} else if (estado == Estado.PULANDO) {
			if (tempoDecorrido > pulandoTempoQuadro) {
				pulandoIndexAtual++;
				if (pulandoIndexAtual >= pulandoQtdQuadros)
					pulandoIndexAtual = 0;
				tempoDecorrido = 0;
			}
		}
	}

	public BufferedImage obterQuadro() {
		if (estado == Estado.CORRENDO) {
			return correndoQuadros[correndoIndexAtual];
		} else if (estado == Estado.PULANDO) {
			return pulandoQuadros[pulandoIndexAtual];
		} else if (estado == Estado.MORTO) {
			return mortoImagem;
		}
		return null;
	}

	public void reiniciaJogo() {
		correndoIndexAtual = 0;
		pulandoIndexAtual = 0;
		posX = 30;
		posY = posY_inicial;
		velY = 0;
		colX = posX + 25;
		colY = posY + 10;
		estado = Estado.CORRENDO;
	}

	public enum Estado {
		CORRENDO, PULANDO, MORTO;
	}
}
