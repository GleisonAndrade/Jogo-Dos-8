package br.edu.ufpi.ia.jogodos8.buscas;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufpi.ia.jogodos8.modelo.Tabuleiro;

/**
 * Essa classe implementa os dois tipos de busca cega, em largura e profundidade
 * @author gleison
 *
 */
public class SolucaoCega {
	private Tabuleiro tabuleiroInicial; // tabuleiro inicial do problema
	private List<Tabuleiro> solucao; // lista dos tabueiros da arvore que fazem parte da solução
	private int maximoDeFilhosEmMemoria; // máximo de filhos que serão mantidos em memoria
	
	public int contadorDeVisitados; // contador de filhos visitados
	public int maxFilhosGerados; // quantos filhos no máximo foram gerados por um tabuleiro
	public int totalDeFilhos; // total de filhos gerados para solucionar o problema
	public int profundidade; // profundidade da arvore
	
	/**
	 * 
	 * @param tabuleiroInicial tabuleiro inicial do problema
	 * @param maximoDeFilhosGerados máximo de filhos que serão mantidos em memoria
	 */
	public SolucaoCega(Tabuleiro tabuleiroInicial, int maximoDeFilhosGerados) {
		this.tabuleiroInicial = tabuleiroInicial;
		this.solucao = new ArrayList<Tabuleiro>();
		this.contadorDeVisitados = 0;
		this.totalDeFilhos = 0;
		this.maxFilhosGerados = 0;
		this.profundidade = 0;
		this.maximoDeFilhosEmMemoria = maximoDeFilhosGerados;
	}
	
	/**
	 * Busca em largura.
	 * @return tem como retorno uma List<{@link Tabuleiro}> dos tabuleiros que foram percorridos até chegar na solução
	 */
	public List<Tabuleiro> largura(){
		this.contadorDeVisitados = 0;
		this.totalDeFilhos = 0;
		this.maxFilhosGerados = 0;
		this.profundidade = 0;
		
		int contadorVizinhos = 0;
		
		List<Tabuleiro> visitados = new ArrayList<Tabuleiro>();
		List<Tabuleiro> tabuleirosGerados = new ArrayList<Tabuleiro>();
		Tabuleiro atual = tabuleiroInicial.clone();
		visitados.add(atual);
		
		while(!atual.isEhSolucao()){ // testa se a solução é a final
			contadorVizinhos = 0;
			for (Tabuleiro vizinho : atual.expandeVizinhos()) {
				if (!visitados.contains(vizinho) && !tabuleirosGerados.contains(vizinho)){
					tabuleirosGerados.add(vizinho);
					contadorVizinhos++;
				}
			}
			
			contadorDeVisitados++;
			totalDeFilhos += contadorVizinhos;
			
			if (contadorVizinhos > maxFilhosGerados) {
				maxFilhosGerados = contadorVizinhos;
			}
			
			atual = tabuleirosGerados.get(0).clone();
			tabuleirosGerados.remove(0);
			visitados.add(atual);
			
			if (visitados.size() > maximoDeFilhosEmMemoria){
				break;
			}
		}
		
		while(atual.getPai() != null){
			this.solucao.add(atual);
			atual = atual.getPai();
		}
		
		solucao.add(tabuleiroInicial);		
		profundidade = solucao.size();
		
		return solucao;
	}
	
	/**
	 * Busca em profundidade.
	 * @return tem como retorno uma List<{@link Tabuleiro}> dos tabuleiros que foram percorridos até chegar na solução
	 */
	public List<Tabuleiro> profundidade(){
		this.contadorDeVisitados = 0;
		this.totalDeFilhos = 0;
		this.maxFilhosGerados = 0;
		this.profundidade = 0;
		
		int contadorVizinhos = 0;
		
		List<Tabuleiro> visitados = new ArrayList<Tabuleiro>();
		List<Tabuleiro> tabuleirosGerados = new ArrayList<Tabuleiro>();
		Tabuleiro atual = tabuleiroInicial.clone();
		visitados.add(atual);
		while(!atual.isEhSolucao()){
			contadorVizinhos = 0;
			for (Tabuleiro vizinho : atual.expandeVizinhos()) {
				if (!visitados.contains(vizinho) && !tabuleirosGerados.contains(vizinho)){
					tabuleirosGerados.add(vizinho);
					contadorVizinhos++;
				}
			}
			
			contadorDeVisitados++;
			totalDeFilhos += contadorVizinhos;
			
			if (contadorVizinhos > maxFilhosGerados) {
				maxFilhosGerados = contadorVizinhos;
			}
			
			atual = tabuleirosGerados.get(tabuleirosGerados.size()-1).clone();
			tabuleirosGerados.remove(tabuleirosGerados.size()-1);
			visitados.add(atual);
			
			if (visitados.size() > maximoDeFilhosEmMemoria){
				break;
			}
		}
		
		while(atual.getPai() != null){
			this.solucao.add(atual);
			atual = atual.getPai();
		}
		
		solucao.add(tabuleiroInicial);		
		profundidade = solucao.size();
		
		return solucao;
	}
	
	/**
	 * @return
	 */
	public List<Tabuleiro> getSolucao(){
		return this.solucao;
	}
	
	/**
	 * @return
	 */
	public Tabuleiro getTabuleiroInicial() {
		return tabuleiroInicial;
	}
	
}
