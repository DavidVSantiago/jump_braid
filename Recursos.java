import java.awt.image.BufferedImage;

public class Recursos {
	// atributos
	private static Recursos singleton = null;
	
	// construtor
	private Recursos() {
		
	}
	
	// métodos
	public BufferedImage cortarImagem(int x1,int y1,int x2,int y2, BufferedImage sprite) {
		int largura = x2-x1;
		int altura = y2-y1;
		BufferedImage pedacoRecortado = sprite.getSubimage(x1,y1, largura, altura);
		return pedacoRecortado;
	}
	
	// retorna a instancia
	public static Recursos getInstance() { // retorna o objeto único da classe Recursos
		// inicializar o singleton, caso ele nunca tenha sido inicializado
		if(singleton==null) { // ele nunca foi inicializado?
			singleton = new Recursos(); // invocando o construtor privado
		}
		return singleton;
	}
}