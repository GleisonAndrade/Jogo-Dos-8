package br.edu.ufpi.ia.jogodos8.modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Essa classe é responsável por simular o tabuleiro do jogo dos 8.
 * @author gleison
 *
 */
public class Tabuleiro {
	private int[][] tabuleiro;
	private int foraDePosicao;
	private int manhattan;
	private Map<Integer, Ponto> tabuleiroFinal;
	private List<Tabuleiro> vizinhos;
	private boolean ehSolucao;
	private int custo;
	private Integer custoTotal;
	private Tabuleiro pai;
	
	/**
	 * 
	 * @param inicial vetor 3 x 3, que representa o tabuleiro inicial.
	 * @param tabuleiroFinal Map<Integer, Ponto> que representa o número e posição que ele deve está no tabuleiro, ou seja, é a solução do problema.
	 * @param custo determina o custo do nó, que no cado é o tabuleiro.
	 * @param pai tabuleiro que antecessor, ou seja, pai do nó atual.
	 */
	public Tabuleiro(int[][] inicial, Map<Integer, Ponto> tabuleiroFinal, int custo, Tabuleiro pai) {
		this.tabuleiro = inicial;
		this.tabuleiroFinal = tabuleiroFinal;
		this.manhattan = calculeManhattan(); // Faz o cáculo da heurística de manhatan
		this.custo = custo;
		if(this.manhattan > 0){
			this.ehSolucao = false;
		}else{
			this.ehSolucao = true;
		}
		this.custoTotal = 0;
//		this.custoTotal = this.manhattan + this.custo;
		this.pai = pai;
	}
	
	public Tabuleiro(Tabuleiro board) {
		this.custo = board.custo;
		this.tabuleiroFinal = board.tabuleiroFinal;
		this.ehSolucao = board.ehSolucao;
		this.manhattan = board.manhattan;
		this.vizinhos = board.vizinhos;
		this.tabuleiro = board.tabuleiro;
		this.foraDePosicao = board.foraDePosicao;
		this.pai = board.pai;
		this.custoTotal = board.custoTotal;
	}
	
	public Tabuleiro clone(){
		return new Tabuleiro(this);
	}
	
	/**
	 * Realiza o calculo da heurística de Manhattan.
	 * somatório (|x-x0| + |y-y0|)
	 * @return
	 */
	private int calculeManhattan(){
		int somaDasDistancias = 0;
		for (int x = 0; x < tabuleiro.length; x++) {
			for (int y = 0; y < tabuleiro[x].length; y++) {
				Ponto ponto = tabuleiroFinal.get(tabuleiro[x][y]);
				
				int distancia = Math.abs(x - ponto.x) + Math.abs(y - ponto.y);
				somaDasDistancias += distancia; // distância do numero para sua posição correta
				
				if(distancia > 0){
					foraDePosicao++; // quantos números estão fora de posição
				}
			}
		}
		
		return somaDasDistancias;
	}
	
	/**
	 * Gera os possiveis filhos do tabuleito aqual.
	 * @return lista de filhos
	 */
	public List<Tabuleiro> expandeVizinhos(){
		List<Tabuleiro> vizinhos = new ArrayList<Tabuleiro>();
		Ponto posicaoVazia = obtemPosicaoVazia();
		
		if(posicaoVazia.x > 0){
			int[][] tabuleiro = new int[this.tabuleiro.length][]; 
			for (int i = 0; i < tabuleiro.length; i++) {
				tabuleiro[i] = this.tabuleiro[i].clone();
			}
			tabuleiro[posicaoVazia.x][posicaoVazia.y] = tabuleiro[posicaoVazia.x-1][posicaoVazia.y];
			tabuleiro[posicaoVazia.x-1][posicaoVazia.y] = 0;
			vizinhos.add(new Tabuleiro(tabuleiro, this.tabuleiroFinal, this.custo+1, this));
		}
		if(posicaoVazia.x < 2){
			int[][] tabuleiro = new int[this.tabuleiro.length][]; 
			for (int i = 0; i < tabuleiro.length; i++) {
				tabuleiro[i] = this.tabuleiro[i].clone();
			}
			tabuleiro[posicaoVazia.x][posicaoVazia.y] = tabuleiro[posicaoVazia.x+1][posicaoVazia.y];
			tabuleiro[posicaoVazia.x+1][posicaoVazia.y] = 0;
			vizinhos.add(new Tabuleiro(tabuleiro, this.tabuleiroFinal, this.custo+1, this));
		}
		if(posicaoVazia.y > 0){
			int[][] tabuleiro = new int[this.tabuleiro.length][]; 
			for (int i = 0; i < tabuleiro.length; i++) {
				tabuleiro[i] = this.tabuleiro[i].clone();
			}
			tabuleiro[posicaoVazia.x][posicaoVazia.y] = tabuleiro[posicaoVazia.x][posicaoVazia.y-1];
			tabuleiro[posicaoVazia.x][posicaoVazia.y-1] = 0;
			vizinhos.add(new Tabuleiro(tabuleiro, this.tabuleiroFinal, this.custo+1, this));
		}
		if(posicaoVazia.y < 2){
			int[][] tabuleiro = new int[this.tabuleiro.length][]; 
			for (int i = 0; i < tabuleiro.length; i++) {
				tabuleiro[i] = this.tabuleiro[i].clone();
			}
			tabuleiro[posicaoVazia.x][posicaoVazia.y] = tabuleiro[posicaoVazia.x][posicaoVazia.y+1];
			tabuleiro[posicaoVazia.x][posicaoVazia.y+1] = 0;
			vizinhos.add(new Tabuleiro(tabuleiro, this.tabuleiroFinal, this.custo+1, this));
		}
		
		this.vizinhos = vizinhos;
		return vizinhos;
	}
	
	/**
	 * Verifica onde está o espaço vazio no tabuleiro
	 * @return {@link Ponto} onde está o espaço vazio.
	 */
	private Ponto obtemPosicaoVazia(){
		for (int x = 0; x < tabuleiro.length; x++) {
			for (int y = 0; y < tabuleiro.length; y++) {
				if (tabuleiro[x][y] == 0) {
					return new Ponto(x, y);
				}
			}
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(tabuleiro);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tabuleiro other = (Tabuleiro) obj;
		if (!Arrays.deepEquals(tabuleiro, other.tabuleiro))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < tabuleiro.length; i++) {
			for (int j = 0; j < tabuleiro[i].length; j++) {
				sb.append(tabuleiro[i][j] + " ");
				if(i == 0 && j == 2){
					sb.append("foraDePosicao=" + foraDePosicao + ", manhattan="
				+ manhattan + ", custo=" + custo + ", custoTotal=" + custoTotal);
				}
			}
			sb.append("\n");
		}
		return sb.toString();
//		return "Board [numbers=" + Arrays.toString(numbers)
//				+ ", outNumbersSum=" + outNumbersSum + ", manhattan="
//				+ manhattan + ", cost=" + cost + ", totalCost=" + totalCost
//				+ "]";
	}

	/**
	 * @return the tabuleiro
	 */
	public int[][] getTabuleiro() {
		return tabuleiro;
	}

	/**
	 * @param tabuleiro the tabuleiro to set
	 */
	public void setTabuleiro(int[][] tabuleiro) {
		this.tabuleiro = tabuleiro;
	}

	/**
	 * @return the foraDePosicao
	 */
	public int getForaDePosicao() {
		return foraDePosicao;
	}

	/**
	 * @param foraDePosicao the foraDePosicao to set
	 */
	public void setForaDePosicao(int foraDePosicao) {
		this.foraDePosicao = foraDePosicao;
	}

	/**
	 * @return the manhattan
	 */
	public int getManhattan() {
		return manhattan;
	}

	/**
	 * @param manhattan the manhattan to set
	 */
	public void setManhattan(int manhattan) {
		this.manhattan = manhattan;
	}

	/**
	 * @return the tabuleiroFinal
	 */
	public Map<Integer, Ponto> getTabuleiroFinal() {
		return tabuleiroFinal;
	}

	/**
	 * @param tabuleiroFinal the tabuleiroFinal to set
	 */
	public void setTabuleiroFinal(Map<Integer, Ponto> tabuleiroFinal) {
		this.tabuleiroFinal = tabuleiroFinal;
	}

	/**
	 * @return the vizinhos
	 */
	public List<Tabuleiro> getVizinhos() {
		return vizinhos;
	}

	/**
	 * @param vizinhos the vizinhos to set
	 */
	public void setVizinhos(List<Tabuleiro> vizinhos) {
		this.vizinhos = vizinhos;
	}

	/**
	 * @return the ehSolucao
	 */
	public boolean isEhSolucao() {
		return ehSolucao;
	}

	/**
	 * @param ehSolucao the ehSolucao to set
	 */
	public void setEhSolucao(boolean ehSolucao) {
		this.ehSolucao = ehSolucao;
	}

	/**
	 * @return the custo
	 */
	public int getCusto() {
		return custo;
	}

	/**
	 * @param custo the custo to set
	 */
	public void setCusto(int custo) {
		this.custo = custo;
	}

	/**
	 * @return the custoTotal
	 */
	public Integer getCustoTotal() {
		return custoTotal;
	}

	/**
	 * @param custoTotal the custoTotal to set
	 */
	public void setCustoTotal(Integer custoTotal) {
		this.custoTotal = custoTotal;
	}

	/**
	 * @return the pai
	 */
	public Tabuleiro getPai() {
		return pai;
	}

	/**
	 * @param pai the pai to set
	 */
	public void setPai(Tabuleiro pai) {
		this.pai = pai;
	}
	
}	