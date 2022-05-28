import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.awt.Font;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Recursos {
	// atributos
	BufferedImage spriteBraidRun, spriteBraidJump, braidDied;
	BufferedImage spriteMostroBola, spriteMostroDino;
	BufferedImage imgGround, imgFundo, telaGameOver, telaLoading;
	public Font fontTexto;
	public double velocidadeJogo, fatorAceleracao;
	long recorde;
	AudioInputStream streamMusicaJogo,streamSomPulo,streamSomQueda;
	Clip clipMusicaJogo,clipSomPulo,clipSomQueda;

	// construtor
	public Recursos() {
		carregaRecorde();
		velocidadeJogo = 1.0;
		fatorAceleracao = 0.08;
		fontTexto = new Font("Arial narrow", Font.CENTER_BASELINE, 30);
		try {
			// carrega os sprites a partir do arquivo
			spriteBraidJump = ImageIO.read(getClass().getResource("/imgs/braid-jump.png"));
			spriteBraidRun = ImageIO.read(getClass().getResource("/imgs/braid-run.png"));
			braidDied = ImageIO.read(getClass().getResource("/imgs/braid-died.png"));
			spriteMostroBola = ImageIO.read(getClass().getResource("/imgs/monster-ball.png"));
			spriteMostroDino = ImageIO.read(getClass().getResource("/imgs/monster-dino.png"));
			imgGround = ImageIO.read(getClass().getResource("/imgs/ground.png"));
			imgFundo = ImageIO.read(getClass().getResource("/imgs/fundo.png"));
			telaGameOver = ImageIO.read(getClass().getResource("/imgs/game-over-screen.png"));
			telaLoading = ImageIO.read(getClass().getResource("/imgs/load-screen.png"));
			// carega os arquivos de audio
			streamMusicaJogo = AudioSystem.getAudioInputStream(getClass().getResource("audio/music.wav"));
			streamSomPulo = AudioSystem.getAudioInputStream(getClass().getResource("audio/jump.wav"));
			streamSomQueda= AudioSystem.getAudioInputStream(getClass().getResource("audio/toc.wav"));
			clipMusicaJogo = AudioSystem.getClip();
			clipSomPulo = AudioSystem.getClip();
			clipSomPulo.open(streamSomPulo);
			clipSomQueda= AudioSystem.getClip();
			clipSomQueda.open(streamSomQueda);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void carregaRecorde() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("recorde.txt"));
			// Declaring loop variable
			String line;
			while ((line = br.readLine()) != null) {
				recorde = Long.parseLong(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	long tempoMusica = 10000;
	public void tocarMusicaJogo() {
		try {
			clipMusicaJogo.setMicrosecondPosition(0);
			clipMusicaJogo.start();
			if(!clipMusicaJogo.isOpen())
				clipMusicaJogo.open(streamMusicaJogo);
			clipMusicaJogo.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public void pararMusicaJogo() {
		try {
			clipMusicaJogo.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void tocarSomPulo(){
		clipSomPulo.setFramePosition(0);
		clipSomPulo.start(); 
	}
	public void tocarSomQueda(){
		clipSomQueda.setFramePosition(0);
		clipSomQueda.start(); 
	}
}