import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/* Representa o personagem do jogo */
public class Fundo { 
	public BufferedImage fundo1,fundo2;
    int fundoLargura,fundoAltura,fundovelX;
	int fundo1PosX, fundo1posY;
	int fundo2PosX, fundo2posY;

	public Fundo() {
		fundo1 = Game.recursos.imgFundo;
        fundo2 = Game.recursos.imgFundo;
		fundoLargura = 1000;
		fundoAltura = 480;
        fundovelX = -1;

        fundo1PosX = 0;
        fundo1posY = 0;
        fundo2PosX = fundo1PosX+fundoLargura;
        fundo2posY = fundo1posY;
	}

	public void remontarFundo(){
		if(fundo1PosX+fundoLargura <= 0){
            fundo1PosX = fundo2PosX+fundoLargura;
        }else if(fundo2PosX+fundoLargura <= 0){
            fundo2PosX = fundo1PosX+fundoLargura;
        }
	}
}