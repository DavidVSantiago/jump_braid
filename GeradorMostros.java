import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.awt.Graphics;


/* Classe responsável por gerar monstros de forma aleatória */
public class GeradorMostros {
    
    // atributos -----------------------------
    public Stack<MonstroDino> pilhaMonstrosDino;
    public Stack<MonstroBola> pilhaMonstrosBola;
    public ArrayList<MonstroDino> listaMonstrosDino;
    public ArrayList<MonstroBola> listaMonstrosBola;
    public int qtdMonstros = 2; // para cada tipo

    long tempoDecorrido;
    Random random;
    int tempoMin, tempoMax, tempoGeracao; 

    // construtor ----------------------------
    public GeradorMostros(){
        pilhaMonstrosDino = new Stack<MonstroDino>();
        pilhaMonstrosBola = new Stack<MonstroBola>();
        listaMonstrosDino = new ArrayList<MonstroDino>();
        listaMonstrosBola = new ArrayList<MonstroBola>();
        for(int i=0;i<qtdMonstros;i++){
            MonstroDino monstroDino = new MonstroDino();
            pilhaMonstrosDino.push(monstroDino);
            listaMonstrosDino.add(monstroDino);
            MonstroBola monstroBola = new MonstroBola();
            pilhaMonstrosBola.push(monstroBola);
            listaMonstrosBola.add(monstroBola);
        }
        listaMonstrosDino.clear();
        listaMonstrosBola.clear();
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

        // atualização dos monstros dino
        for(MonstroDino m : listaMonstrosDino){
            m.update();
            m.mudarQuadro(tempoDelta);
        }
        for(MonstroBola m : listaMonstrosBola){
            m.update();
            m.mudarQuadro(tempoDelta);
        }
    }

    public void render(Graphics g){
        for(MonstroDino m : listaMonstrosDino){
            m.render(g);
        }
        for(MonstroBola m : listaMonstrosBola){
            m.render(g);
        }
	}

    // metodos -------------------------------
    public void gerarNovoMonstro(){
        // gera um numero aleatório para saber se vai ser dino ou bola
        if(random.nextInt()%2==0){ // se for par, vai ser dino
            if(pilhaMonstrosDino.empty()) return; // não gera monstro se não houver no poll
            MonstroDino monstroDino = pilhaMonstrosDino.pop();
            monstroDino.reposicionar();
            listaMonstrosDino.add(monstroDino);
        }else{// se for ímpar, vai ser bola
            if(pilhaMonstrosBola.empty()) return; // não gera monstro se não houver no poll
            MonstroBola monstroBola = pilhaMonstrosBola.pop();
            monstroBola.reposicionar();
            listaMonstrosBola.add(monstroBola);
        }

        
    }
    public void checarSaidaMonstros(){
        for(int i=0;i<listaMonstrosDino.size();i++){
            MonstroDino m = listaMonstrosDino.get(i);
            // verifica se cada monstro saiu da tela pela esquerda
            if(m.posX+m.correndoLargura<=0){
                // recicla o mosntro
                pilhaMonstrosDino.push(m);
                listaMonstrosDino.remove(m);
            }
        }
        for(int i=0;i<listaMonstrosBola.size();i++){
            MonstroBola m = listaMonstrosBola.get(i);
            // verifica se cada monstro saiu da tela pela esquerda
            if(m.posX+m.correndoLargura<=0){
                // recicla o mosntro
                pilhaMonstrosBola.push(m);
                listaMonstrosBola.remove(m);
            }
        }
    }

    public void reiniciaJogo(){
        for(int i=0;i<listaMonstrosDino.size();i++){
            MonstroDino m = listaMonstrosDino.get(i);
            m.reiniciaJogo();
            pilhaMonstrosDino.push(m);
            listaMonstrosDino.remove(m);
        }
		for(int i=0;i<listaMonstrosBola.size();i++){
            MonstroBola m = listaMonstrosBola.get(i);
            m.reiniciaJogo();
            pilhaMonstrosBola.push(m);
            listaMonstrosBola.remove(m);
        }
	}
}
