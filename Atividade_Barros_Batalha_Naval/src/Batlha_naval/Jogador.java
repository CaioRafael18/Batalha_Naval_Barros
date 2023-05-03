package Batlha_naval;

import java.util.Scanner;
public class Jogador {
    private String nome;
    private char[][] meuJogo = new char[8][8];
    private char[][] jogoDoAdversario = new char[8][8];
    private int totalDeArmas = 3;
    private int S = 3;
    private int C = 2;
    private int P = 1;
    private int Atingidas = 6;

    public Jogador(String nome) {
        this.nome = nome;
    }

    public void iniciarJogo() { // Este metodo começa o jogo chamando os dois metodos abaixo para iniciar e mostrar o tabuleiro e depois chama o metodo posição para dizer a posição das armas.
        System.out.println("---------- BATALHA NAVAL ----------");
        iniciarTabuleiro(meuJogo);
        mostrarTabuleiro(meuJogo);
        Posicao();
    }

    public void iniciarTabuleiro(char[][] meujogo) { // Este metodo inicia o tabuleiro definindo cada posição com um espaço em branco.
        for (int l = 0; l < meujogo.length; l++) { // inicia o loop que itera as linhas do tabuleiro.
            for (int c = 0; c < meujogo.length; c++) { // inicia o loop que itera as colunas do tabuleiro.
                meujogo[l][c] = ' '; // preenche a posição com um espaço em branco.
            }
        }
    }

    public void mostrarTabuleiro(char[][] meujogo) { // Este metodo mostra o tabuleiro na tela usando um loop para iterar as linhas e colunas.
        System.out.println("    0   1   2   3   4   5   6   7");
        for (int l = 0; l < meujogo.length; l++) { // iterando as linhas com as letras.
            char[] linhas = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
            System.out.print(linhas[l] + "   ");
            for (int c = 0; c < meujogo.length; c++) { // itera cada coluna o caractere armazenado em meuJogo.
                System.out.print(meujogo[l][c]);
                if (c < (meujogo.length -1)) { // vai colocar o | dividindo cada coluna enquanto a coluna for menor que o ultimo numero.
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if (l < (meujogo.length -1)) { // vai colocar os '--' quando chegar na ultima linha.
                System.out.println("   -------------------------------");
            }
        }
    }

    public void Posicao() { // Este metodo pede para o jogador digitar a posição das armas e verifica se a posição é valida.
        System.out.println("Olá, " + this.nome + "! digite a posição das suas armas:");
        Scanner ler = new Scanner(System.in);
        System.out.print("Posição exemplo(A0): ");
        String posicao = ler.next();
        char[] caracteres = posicao.toCharArray();
        char linha = Character.toUpperCase(caracteres[0]); // converte em maiusculo e pega a primeira letra digitada.
        int coluna = (int) caracteres[1] - '0'; // converte em numero(inteiro) e pega a 2 caracteres digitada.
        char[] linhas = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
        int indice = -1;
        for (int c = 0; c < linhas.length; c++) { // encontra a linha que foi digitada e preenche com o indice dessa letra.
            if (linhas[c] == linha) {
                indice = c;
                break;
            }
        }
        if (indice != -1 && (coluna >= 0 && coluna <= 7)) { // conferindo se a posição é valida.
            Arma(indice, coluna);
        } else {
            System.out.println("Posição inválida! Digite novamente: ");
            Posicao();
        }
    }

    public void Arma(int linha, int coluna) { // Este metodo mostra as armas que tem para o jogador escolher e chama o metodo verificarArma() para verificar a arma e a posição no tabuleiro.
        System.out.print("Digite qual Arma você deseja[S/C/P]: ");
        Scanner ler = new Scanner(System.in);
        char arma = Character.toUpperCase(ler.nextLine().charAt(0)); // le a entrada e converte em maiusculo
        int direcao = Direcao(linha, coluna, arma); // chama o metodo direcao para confirmar a direção do posicionamento
        verificarArma(linha, coluna, arma, direcao); // chama o metodo verificarArma para confirmar se a posição da arma pode ser colocado na posição desejada.
    }

    public int Direcao(int linha, int coluna, char arma) { // Verifica qual posição é possivel direcionar a arma.
        int calcular = (meuJogo.length -1) - coluna; // calcular a posição desejada para não passar da ultima coluna.
        int espaco = 0;
        int direcao = 0;
        if (arma == 'S') { // definindo quantos espaços tem que ter para cada arma.
            espaco = 2;
        } else if (arma == 'C') {
            espaco = 1;
        } else if (arma == 'P') {
            espaco = 0;
        } else {
            System.out.println("Não é possivel colocar nessa posição!");
        }

        if (calcular == espaco) {
            direcao = 0; // vai pra frente
        } else if (calcular > espaco || espaco == 0) {
            direcao = 1; // vai pra trás
        } else {
            direcao = -1;
        }
        return direcao;
    }

    public void verificarArma(int linha, int coluna, char arma, int direcao) { // Este metodo verifica se a arma escolhida pode ser colocada na posição desejada.
        int total = 0; // numero de espaços que a arma ocupa.
        int contador = 0; // numero de espaços ainda disponivel.
        if (arma == 'S' && S > 0) {
            total = 3;
        } else if (arma == 'C' && C > 0) {
            total = 2;
        } else if (arma == 'P' && P > 0) {
            total = 1;
        } else {
            System.out.println("Arma inválida, você não tem mais essa arma! Digite novamente: ");
            Arma(linha, coluna);
        }
        int[] verificado = new int[total + 1]; // armazena as colunas do tabuleiro que a arma vai ocupar.
        if (direcao == 0) total -= 1;
        for (int l = 0; l <= total; l++) { // vai estar verificando se é possivel adicionar a arma
            if (direcao == 1 || direcao == 0) {
                if (meuJogo[linha][coluna+l] == ' ') { // verifica se a coluna ao seu lado está livre para adicionar a arma.
                    verificado[l] = coluna + l;
                    contador++;
                }
                if (coluna != 0) { // se não for a primeira coluna(0) verifica se está livre na esquerda.
                    if (meuJogo[linha][coluna-1] == ' ') {
                        contador = contador;
                    } else {
                        contador -= 1;
                    }
                }
            } else if (direcao == -1) {
                if (meuJogo[linha][coluna-l] == ' ') {
                    verificado[l] = coluna-l;
                    contador++;
                }
                if (coluna != 7) {
                    if (meuJogo[linha][coluna+1] == ' ') {
                        contador = contador;
                    } else {
                        contador -= 1;
                    }
                }
            }
        }
        if (contador < total + 1) { // Quando o contador for diferente que o tamanho da arma, vai pedir pra digitar outra.
            System.out.println("Digite outra Posição: ");
            verificado = new int[0];
            Posicao();
        } else { // se não for, vai diminuir uma arma.
            totalDeArmas--;
            for (int l = 0; l < (verificado.length -1); l++) { // chamando o medoto para diminuir a arma escolhida.
                meuJogo[linha][verificado[l]] = arma;
                decrementandoArma(arma);
            }
            System.out.println("---------- MEU TABULEIRO ----------");
            mostrarTabuleiro(meuJogo);
        }
        if (totalDeArmas > 0) {
            Posicao();
        }
    }

    public void decrementandoArma(char arma) { // Este metodo está diminuindo a quantidade de armas.
        if (arma == 'S') {
            S--;
        } else if (arma == 'C') {
            C--;
        } else if (arma == 'P') {
            P--;
        }
    }

    public void atirar(Jogador adversario) { // Este metodo vai estar simulando uma rodada da partida.
        System.out.println();
        System.out.println("Digite a Posição que você deseja atirar!");
        Jogador jogador1, jogador2;
        int contador = 0; // contando os tiros por rodadas
        int rodada = 1; // contando as rodadas.
        int agua = 0; // contando os tiros na agua
        int total = 0; // contando o total de tiros
        while (this.Atingidas > 0 && adversario.Atingidas > 0) { // enquanto ainda tiverem navios
            if (contador < 2) {
                jogador1 = adversario;
                jogador2 = this;
            } else {
                jogador1 = this;
                jogador2 = adversario;
            }
            System.out.println("---------------------------");
            System.out.println("Rodada: " + rodada);
            System.out.println( "É sua vez, " + jogador1.nome);
            jogador2.registrarTiro(jogador1);
            System.out.println(contador);
            total++;
            contador++;
            if (contador==4) {
                contador = 0;
                rodada++;
                total -= 2;
            }
        }
        if (adversario.Atingidas == 0) { // declarando vencedor
            System.out.println("Parabéns " + adversario.nome + " você venceu o jogo!!");
        } else {
            System.out.println("Parabéns " + this.nome + " você venceu o jogo!!");
        }
        agua = total - 6; // mostrando quantos tiros foram na agua
        System.out.println("Foram " + total + " Tiros, desses tiros " + agua + " na Água");
        System.out.println("--------- TABULEIRO ADVERSÁRIO ----------");
        mostrarTabuleiro(this.jogoDoAdversario);
        System.out.println("----------- MEU TABULEIRO -----------");
        mostrarTabuleiro(meuJogo);
    }

    public void registrarTiro(Jogador adversario) { // Este metodo vai estar verificando onde o tiro atingiu.
        Scanner ler = new Scanner(System.in);
        System.out.print("Posição exemplo(A0): ");
        String posicao = ler.next(); // mesmo processo do metodo posição!
        char[] caracteres = posicao.toCharArray();
        char linha = Character.toUpperCase(caracteres[0]);
        int coluna = (int) caracteres[1] - '0';
        char[] linhas = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
        int indice = -1;
        for (int c = 0; c < linhas.length; c++) {
            if (linhas[c] == linha) {
                indice = c;
                break;
            }
        }
        if (indice != -1 && (coluna >= 0 && coluna <= 7)) { // verifica se acertou o navio na posição que foi especificada.
            boolean acertou = verificarSeAcertou(indice, coluna);
            if (acertou) {
                System.out.println("Você acertou um navio inimigo!");
                adversario.atingiuArma();
            } else {
                System.out.println("Você errou o tiro, Acertou na Água!");
            }
        } else {
            System.out.println("Opção inválida! Digite novamente: ");
            Posicao();
        }
        System.out.println();
        System.out.println("----------- TABULEIRO ADVERSÁRIO ----------");
        mostrarTabuleiro(this.jogoDoAdversario);
    }

    public boolean verificarSeAcertou(int linha, int coluna) { // Este metodo verifica se o tiro acertou o navio e mostra no tabuleiro.
        if (meuJogo[linha][coluna] == 'S' || meuJogo[linha][coluna] == 'C' || meuJogo[linha][coluna] == 'P') {
            jogoDoAdversario[linha][coluna] = 'O'; // retorna um 0 na posição digitada se acertou o navio
            return true;
        } else {
            jogoDoAdversario[linha][coluna] = 'X'; // retorna um X na posição digita se errou o navio.
            return false;
        }
    }
    public int atingiuArma() { // Este metodo atualiza a quantidade de armas atingidas.
        return Atingidas--;
    }
}
