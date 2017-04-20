/**
 * 
 */
package br.edu.ufpi.ia.jogodos8.main;

import java.util.Random;

/**
 * @author gleison
 *
 */
public class GeradorTabuleiro {
	private int tamanho;
	
	public void imprimir(int [][] tabuleiro){
		for (int i = 0; i < tabuleiro.length; i++){
			for (int j = 0; j < tabuleiro.length; j++){
				System.out.print(tabuleiro[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	private int[][] populaTabuleiro(){
		int tabuleiro [][] = new int [tamanho][tamanho];
		int contador = 0;
		
		for (int i = 0; i < tamanho; i++){
			for (int j = 0; j < tamanho; j++){
				tabuleiro[i][j] = contador++;
			}
		}
		
		imprimir(tabuleiro);
		
		return tabuleiro;
	}
	
	public int [][] gerarTabuleiro(){
//		this.tamanho = tamanho;
//		int tabuleiro [][] = populaTabuleiro();
		int tabuleiro[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
		
		Random random = new Random();
		
		for (int i = 0; i < tabuleiro.length; i++) {
			for (int j = 0; j < tabuleiro.length; j++) {
				int l = random.nextInt(tabuleiro.length - 1); 
				int c = random.nextInt(tabuleiro.length - 1); 
				
//				System.out.println("["+l+","+c+"]");
				
				int temp = tabuleiro[i][j];				
				tabuleiro[i][j] = tabuleiro[l][c];
				tabuleiro[l][c] = temp;
			}
		}		
		
		return tabuleiro;
	}
}
