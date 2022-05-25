import java.awt.image.BufferedImage;
import java.awt.Graphics;

/* Representa o personagem do jogo */
public class Piso { 
	public BufferedImage piso1,piso2,piso3;
    int pisoLargura,pisoAltura,pisoVelX,pisoPosY;
	int piso1PosX;
	int piso2PosX;
    int piso3PosX;

	public Piso() {
		piso1 = Game.recursos.imgGround;
        piso2 = Game.recursos.imgGround;
        piso3 = Game.recursos.imgGround;
        //piso3 = Game.recursos.imgGround;
		pisoLargura = 909;
		pisoAltura = 62;
        pisoVelX = -9;
        pisoPosY=418;

        piso1PosX=0;
        piso2PosX = piso1PosX+pisoLargura;
        piso3PosX = piso2PosX+pisoLargura;
	}

    public void update(){
        // atualiza o piso
		piso1PosX = piso1PosX + pisoVelX;
        piso2PosX = piso2PosX + pisoVelX;
        piso3PosX = piso3PosX + pisoVelX;
		remontarPiso();
    }

    public void render(Graphics g){
        g.drawImage(piso1,piso1PosX,pisoPosY, null);
		g.drawImage(piso2,piso2PosX,pisoPosY, null);
        g.drawImage(piso3,piso3PosX,pisoPosY, null);
    }

	public void remontarPiso(){
		if(piso1PosX+pisoLargura <= 0){
            piso1PosX = piso3PosX+pisoLargura;
        }else if(piso2PosX+pisoLargura <= 0){
            piso2PosX = piso1PosX+pisoLargura;
        }else if(piso3PosX+pisoLargura <= 0){
            piso3PosX = piso2PosX+pisoLargura;
        }
	}
}