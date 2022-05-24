import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/* Representa o personagem do jogo */
public class Braid { 

	public BufferedImage[] correndoQuadros;
	public BufferedImage correndoSprite;
	int correndoQtdQuadros, correndoLargura, correndoAltura;
	int correndoTempoQuadro;
	int correndoIndexAtual;

	int posX, posY, velY;
	Estado estado;

	// variáveis de tempo
	long tempoAtual,tempoAnterior,tempoDelta, tempoDecorrido;
	
	public Braid() {
		correndoSprite = Game.recursos.spriteBraidRun;
		correndoQtdQuadros = 27;
		correndoQuadros = new BufferedImage[correndoQtdQuadros];
		correndoLargura = 130;
		correndoAltura = 150;
		correndoIndexAtual = 0;
		correndoTempoQuadro = 20;

		posX=30;
		posY=220;
		velY=0;
		estado = Estado.CORRENDO;
		tempoDecorrido = 0;
		
		// recorte dos sprites -------------------------------
		for(int i=0; i<correndoQtdQuadros; i++) {
			int x1 = correndoLargura*i;
			int y1 = 0;
			correndoQuadros[i] = correndoSprite.getSubimage(x1,y1,correndoLargura, correndoAltura);
		}
	}
	
	public void mudarQuadro() {
		tempoAtual = System.currentTimeMillis(); // tempo inicial desse quadro
		tempoDelta = tempoAtual - tempoAnterior; // quanto tempo se passou desde o ultimo quadro
		tempoDecorrido += tempoDelta;
		
		if(estado == Estado.CORRENDO){
			// realiza a mudança de quadro apenas após o tempo decorrido
			if(tempoDecorrido > correndoTempoQuadro) {
				correndoIndexAtual++;			
				if(correndoIndexAtual>=correndoQtdQuadros) correndoIndexAtual = 0;
				tempoDecorrido = 0;
			}
		}else if(estado == Estado.PULANDO){

		}

		tempoAnterior = tempoAtual; // tempo inicial do quadro anterior
	}

	public BufferedImage obterQuadro(){
		if(estado == Estado.CORRENDO){
			return correndoQuadros[correndoIndexAtual];
		}else if(estado == Estado.PULANDO){

		}
		return null;
	}

	public enum Estado{
		CORRENDO, PULANDO;
	}
}
