import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/* Representa o personagem do jogo */
public class Piso { 
	public BufferedImage piso1,piso2;
    int pisoLargura,pisoAltura,pisoVelX;
	int piso1PosX, piso1posY;
	int piso2PosX, piso2posY;

	public Piso() {
		piso1 = Game.recursos.imgGround;
        piso2 = Game.recursos.imgGround;
		pisoLargura = 1024;
		pisoAltura = 128;
        pisoVelX = -9;

        piso1PosX=0;
        piso1posY=352;
        piso2PosX = piso1PosX+pisoLargura;
        piso2posY=piso1posY;

	}

	public void remontarPiso(){
		if(piso1PosX+pisoLargura <= 0){
            piso1PosX = piso2PosX+pisoLargura;
        }else if(piso2PosX+pisoLargura <= 0){
            piso2PosX = piso1PosX+pisoLargura;
        }
	}
}