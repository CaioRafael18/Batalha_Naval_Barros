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

    public void iniciarJogo() {
        System.out.println("---------- BATALHA NAVAL ----------");
        iniciarTabuleiro(meuJogo);
        mostrarTabuleiro(meuJogo);
        Posicao();
    }

    public void iniciarTabuleiro(char[][] jogo) {
        for (int l = 0; l < jogo.length; l++) {
            for (int c = 0; c < jogo.length; c++) {
                jogo[l][c] = ' ';
            }
        }
    }

    public void mostrarTabuleiro(char[][] jogo) {
        System.out.println();
        System.out.println("    0   1   2   3   4   5   6   7");
        for (int l = 0; l < jogo.length; l++) {
            char[] linhas = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
            System.out.print(linhas[l] + "   ");
            for (int c = 0; c < jogo.length; c++) {
                System.out.print(jogo[l][c]);
                if (c < (jogo.length -1)) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if (l < (jogo.length -1)) {
                System.out.println("   -------------------------------");
            }
        }
        System.out.println();
    }

    public void Posicao() {
        System.out.println("Olá, " + this.nome + "! digite a posição das suas armas:");
        Scanner ler = new Scanner(System.in);
        System.out.print("Posição exemplo(A0): ");
        String posicao = ler.next();
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
        if (indice != -1 && (coluna >= 0 && coluna <= 7)) {
            Arma(indice, coluna);
        } else {
            System.out.println("Posição inválida! Digite novamente: ");
            Posicao();
        }
    }

    public void Arma(int linha, int coluna) {
        System.out.print("Digite qual Arma você deseja[S/C/P]: ");
        Scanner ler = new Scanner(System.in);
        char arma = Character.toUpperCase(ler.nextLine().charAt(0));
        int direcao = Direcao(linha, coluna, arma);
        verificarArma(linha, coluna, arma, direcao);
    }

    public int Direcao(int linha, int coluna, char arma) {
        int calcular = (meuJogo.length -1) - coluna;
        int espaco = 0;
        int direcao;
        if (arma == 'S') {
            espaco = 2;
        } else if (arma == 'C') {
            espaco = 1;
        } else if (arma == 'P') {
            espaco = 0;
        }

        if (calcular == espaco) {
            direcao = 0;
        } else if (calcular > espaco || espaco == 0) {
            direcao = 1;
        } else {
            direcao = -1;
        }
        return direcao;
    }

    public void verificarArma(int linha, int coluna, char arma, int direcao) {
        int total = 0;
        int contador = 0;
        if (arma == 'S' && S > 0) {
            total = 3;
        } else if (arma == 'C' && C > 0) {
            total = 2;
        } else if (arma == 'P' && P > 0) {
            total = 1;
        } else {
            System.out.println("Arma inválida! Digite novamente: ");
            Arma(linha, coluna);
        }
        int[] verificado = new int[total + 1];
        if (direcao == 0) total -= 1;
        for (int l = 0; l <= total; l++) {
            if (direcao == 1 || direcao == 0) {
                if (meuJogo[linha][coluna+l] == ' ') {
                    verificado[l] = coluna + l;
                    contador++;
                }
                if (coluna != 0) {
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
        if (contador < total + 1) {
            System.out.println(contador);
            System.out.println(total + 1);
            System.out.println("Digite outra Posição: ");
            verificado = new int[0];
            Posicao();
        } else {
            totalDeArmas--;
            for (int l = 0; l < (verificado.length -1); l++) {
                meuJogo[linha][verificado[l]] = arma;
                decrementandoArma(arma);
            }
            System.out.println("---------- MEU TABULEIRO ----------");
            mostrarTabuleiro(meuJogo);
        }
        if (totalDeArmas > 0) Posicao();
    }

    public void decrementandoArma(char arma) {
        if (arma == 'S') {
            S--;
        } else if (arma == 'C') {
            C--;
        } else if (arma == 'P') {
            P--;
        }
    }

    public void atirar(Jogador adversario) {
        System.out.println();
        System.out.println("Digite a Posição que você deseja atirar!");
        Jogador jogador1, jogador2;
        int contador = 0;
        int rodada = 1;
        while (this.Atingidas > 0 && adversario.Atingidas > 0) {
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
            contador++;
            if (contador==4) {
                contador = 0;
                rodada++;
            }
        }
        if (adversario.Atingidas == 0) {
            System.out.println("Parabéns " + adversario.nome + " você venceu o jogo!!");
        } else {
            System.out.println("Parabéns " + this.nome + " você venceu o jogo!!");
        }
        System.out.println("--------- TABULEIRO ADVERSÁRIO ----------");
        mostrarTabuleiro(this.jogoDoAdversario);
        System.out.println("----------- MEU TABULEIRO -----------");
        mostrarTabuleiro(meuJogo);
    }

    public void registrarTiro(Jogador adversario) {
        Scanner ler = new Scanner(System.in);
        System.out.print("Posição exemplo(A0): ");
        String posicao = ler.next();
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
        if (indice != -1 && (coluna >= 0 && coluna <= 7)) {
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

    public boolean verificarSeAcertou(int linha, int coluna) {
        if (meuJogo[linha][coluna] == 'S' || meuJogo[linha][coluna] == 'C' || meuJogo[linha][coluna] == 'P') {
            jogoDoAdversario[linha][coluna] = 'O';
            return true;
        } else {
            jogoDoAdversario[linha][coluna] = 'X';
            return false;
        }
    }
    public int atingiuArma() {
        return Atingidas--;
    }
}
