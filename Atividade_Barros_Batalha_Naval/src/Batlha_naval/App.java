package Batlha_naval;

public class App {

    public static void main(String[] args) {
        Jogador jogador1 = new Jogador("Caio");
        Jogador jogador2 = new Jogador("Cauã");

        jogador1.iniciarJogo();
        jogador2.iniciarJogo();
        jogador1.atirar(jogador2);
    }
}

