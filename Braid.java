import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/* Representa o personagem do jogo */
public class Braid { 
	public BufferedImage sprite;
	public BufferedImage[] listaFrames; // cada um dos frames do sprite
	public BufferedImage imgAtual;
	int posX, posY, velX, width, height;
	int qtdLinhas = 4;
	int qtdColunas = 7;
	int indexAtual;
	// vari�veis de tempo
	long startTime,endTime,deltaTime, tempoDecorrido,frameTime;
	
	public Braid() {
		width = 130;
		height = 150;
		posX=30;
		posY=250;
		velX=0;
		velX=0;
		indexAtual = 0;
		frameTime = 20;
		tempoDecorrido = 0;
		
		listaFrames = new BufferedImage[qtdLinhas*qtdColunas];
		// faz o carregamento do sprite
		try {
			sprite = ImageIO.read(getClass().getResource("/imgs/sprite-braid.png"));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		for(int i=0; i<qtdLinhas;i++) {
			for(int j=0; j<qtdColunas;j++) { // percorre as colunas (0-6)
				int x1 = width*j;
				int y1 = height*i;
				int x2 = x1+width;
				int y2 = y1+height;
				listaFrames[i*qtdColunas+j] = Recursos.getInstance().cortarImagem(x1, y1, x2, y2, sprite);
			}
		}
		imgAtual = listaFrames[indexAtual];
	}
	
	public void mudarQuadro() {
		startTime = System.currentTimeMillis(); // tempo inicial desse quadro
		deltaTime = startTime - endTime; // quanto tempo se passou desde o ultimo quadro
		tempoDecorrido += deltaTime;
		
		// realiza a mudan�a de quadro apenas ap�s o tempo decorrido
		if(tempoDecorrido > frameTime) {
			indexAtual++;			
			if(indexAtual>=27) indexAtual =0;
			imgAtual = listaFrames[indexAtual];
			tempoDecorrido =0;
		}
		
		endTime = startTime; // tempo inicial do quadro anterior
	}
}
