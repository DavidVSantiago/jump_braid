import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/* Representa o personagem do jogo */
public class Fundo { 
	public BufferedImage fundo1,fundo2;
    int fundoLargura,fundoAltura,fundovelX,fundoPosY;
	int fundo1PosX;
	int fundo2PosX;

	public Fundo() {
		fundo1 = Game.recursos.imgFundo;
        fundo2 = Game.recursos.imgFundo;
		fundoLargura = 1000;
		fundoAltura = 480;
        fundovelX = -1;

        fundo1PosX = 0;
        fundoPosY = 0;
        fundo2PosX = fundo1PosX+fundoLargura;
	}

	public void remontarFundo(){
		if(fundo1PosX+fundoLargura <= 0){
            fundo1PosX = fundo2PosX+fundoLargura;
        }else if(fundo2PosX+fundoLargura <= 0){
            fundo2PosX = fundo1PosX+fundoLargura;
        }
	}

    public void reiniciaJogo(){
        fundo1PosX = 0;
        fundo2PosX = fundo1PosX+fundoLargura;
    }
}