import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/* Representa o personagem do jogo */
public class Braid { 

	// variáveis das imagens
	public BufferedImage[] correndoQuadros;
	public BufferedImage correndoSprite;
	public int correndoQtdQuadros, correndoLargura, correndoAltura;
	public int correndoTempoQuadro;
	public int correndoIndexAtual;
	public BufferedImage[] pulandoQuadros;
	public BufferedImage pulandoSprite;
	public int pulandoQtdQuadros, pulandoLargura, pulandoAltura;
	public int pulandoTempoQuadro;
	public int pulandoIndexAtual;

	public int posX, posY, posY_inicial, velY; // variáveis de posição e velocidade
	public Estado estado; // variável que controla o estado

	// variáveis de tempo
	long tempoAtual,tempoAnterior,tempoDelta, tempoDecorrido;
	
	public Braid() {
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

		posX=30;
		posY_inicial=220;
		posY=posY_inicial;
		velY=0;
		estado = Estado.CORRENDO;
		tempoDecorrido = 0;
		
		// recorte dos sprites -------------------------------
		for(int i=0; i<correndoQtdQuadros; i++) {
			int x1 = correndoLargura*i;
			int y1 = 0;
			correndoQuadros[i] = correndoSprite.getSubimage(x1,y1,correndoLargura, correndoAltura);
		}
		for(int i=0; i<pulandoQtdQuadros; i++) {
			int x1 = pulandoLargura*i;
			int y1 = 0;
			pulandoQuadros[i] = pulandoSprite.getSubimage(x1,y1,pulandoLargura, pulandoAltura);
		}
	}

	public void update(){
		if(estado == Estado.PULANDO){
			posY = posY+velY;
			velY++;
		}
	}

	public void iniciarPulo(){
		pulandoIndexAtual=0;
		velY=-21; // inicia o pula
		estado = Braid.Estado.PULANDO;
	}

	public void pararPulo(){
		correndoIndexAtual=12;
		velY=0;
		estado = Braid.Estado.CORRENDO; // para o pulo
		posY=posY_inicial;
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
			if(tempoDecorrido > pulandoTempoQuadro) {
				pulandoIndexAtual++;			
				if(pulandoIndexAtual>=pulandoQtdQuadros) pulandoIndexAtual = 0;
				tempoDecorrido = 0;
			}
		}
		tempoAnterior = tempoAtual; // tempo inicial do quadro anterior
	}

	public BufferedImage obterQuadro(){
		if(estado == Estado.CORRENDO){
			return correndoQuadros[correndoIndexAtual];
		}else if(estado == Estado.PULANDO){
			return pulandoQuadros[pulandoIndexAtual];
		}
		return null;
	}

	public enum Estado{
		CORRENDO, PULANDO;
	}
}
