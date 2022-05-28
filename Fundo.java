import java.awt.image.BufferedImage;

/* Representa o personagem do jogo */
public class Fundo { 
	public BufferedImage fundo1,fundo2;
    int fundoLargura,fundoAltura;
    double fundoVelX;
	double fundo1PosX,fundo2PosX,fundoPosY;

	public Fundo() {
		fundo1 = Game.recursos.imgFundo;
        fundo2 = Game.recursos.imgFundo;
		fundoLargura = 1000;
		fundoAltura = 480;
        fundoVelX = -1*Game.recursos.velocidadeJogo;

        fundo1PosX = 0;
        fundoPosY = 0;
        fundo2PosX = fundo1PosX+fundoLargura;
	}

    public void update(){
        // atualiza o fundo
        fundoVelX = -1*Game.recursos.velocidadeJogo;
		fundo1PosX = fundo1PosX + fundoVelX;
	    fundo2PosX = fundo2PosX + fundoVelX;
		remontarFundo();
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