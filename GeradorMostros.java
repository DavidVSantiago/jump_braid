import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.awt.Graphics;

/* Classe responsável por gerar monstros de forma aleatória */
public class GeradorMostros {
    
    // atributos -----------------------------
    public Stack<MonstroBola> pollMonstros;
    public ArrayList<MonstroBola> monstrosTela;
    public int qtdMonstros = 5;

    long tempoDecorrido;
    Random random;
    int tempoMin, tempoMax, tempoGeracao; 

    // construtor ----------------------------
    public GeradorMostros(){
        pollMonstros = new Stack<MonstroBola>();
        monstrosTela = new ArrayList<MonstroBola>();
        for(int i=0;i<qtdMonstros;i++){
            MonstroBola monstroBola = new MonstroBola();
            pollMonstros.push(monstroBola);
            monstrosTela.add(monstroBola);
        }
        monstrosTela.clear();
        tempoDecorrido = 0;
        random = new Random();
        tempoGeracao = 2000;
        tempoMin=2500;
        tempoMax=5500;
    }

    public void update(long tempoDelta){

        // geração de monstros ------------------
        tempoDecorrido+=tempoDelta;
        if(tempoDecorrido >= tempoGeracao) { // a cada 1 segundo
            tempoGeracao = random.nextInt(tempoMax-tempoMin)+tempoMin;
            // tenta gerar um novo monstro na tela
            gerarNovoMonstro();
            tempoDecorrido = 0;
        }

        checarSaidaMonstros(); // verifica sa saida dos monstros na tela

        // atualização dos monstros
        for(MonstroBola m: monstrosTela){
            m.update();
            m.mudarQuadro(tempoDelta);
        }
    }

    public void render(Graphics g){
		for(MonstroBola m: monstrosTela){
            g.drawImage(m.obterQuadro(), m.posX, m.posY, null);
        }
	}

    // metodos -------------------------------
    public void gerarNovoMonstro(){
        if(pollMonstros.empty()) return; // não gera monstro se não houver no poll
        MonstroBola monstroBola = pollMonstros.pop();
        monstroBola.reposicionar();
        monstrosTela.add(monstroBola);
    }
    public void checarSaidaMonstros(){
        for(int i=0;i<monstrosTela.size();i++){
            MonstroBola m = monstrosTela.get(i);
            // verifica se cada monstro saiu da tela pela esquerda
            if(m.posX+m.correndoLargura<=0){
                // recicla o mosntro
                pollMonstros.push(m);
                monstrosTela.remove(m);
            }
        }
    }
}
