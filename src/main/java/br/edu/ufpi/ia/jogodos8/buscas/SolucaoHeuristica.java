package br.edu.ufpi.ia.jogodos8.buscas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.edu.ufpi.ia.jogodos8.modelo.Tabuleiro;

/**
 * @author gleison
 *
 */
public class SolucaoHeuristica {
	private Tabuleiro tabuleiroInicial; // tabuleiro inicial do problema
	private List<Tabuleiro> solucao; // lista dos tabueiros da arvore que fazem parte da solução
	private int maximoDeFilhosEmMemoria; // máximo de filhos que serão mantidos em memoria
	
	public int contadorDeVisitados; // contador de filhos visitados
	public int maxFilhosGerados; // quantos filhos no máximo foram gerados por um tabuleiro
	public int totalDeFilhos; // total de filhos gerados para solucionar o problema
	public int profundidade; // profundidade da arvore

	
	public SolucaoHeuristica(Tabuleiro tabuleiroInicial, int maximoDeFilhosGerados) {
		this.tabuleiroInicial = tabuleiroInicial;
		this.solucao = new ArrayList<Tabuleiro>();
		this.contadorDeVisitados = 0;
		this.totalDeFilhos = 0;
		this.maxFilhosGerados = 0;
		this.profundidade = 0;
		this.maximoDeFilhosEmMemoria = maximoDeFilhosGerados;
	}
	
	/** BUSCA HEURÍSTICA COM HEURÍSTICA DE MANHATTAN
	 * @return
	 */
	public List<Tabuleiro> heuristicaManhattan(){
		List<Tabuleiro> visitados = new ArrayList<Tabuleiro>();
		List<Tabuleiro> tabuleirosGerados = new ArrayList<Tabuleiro>();
		Tabuleiro atual = tabuleiroInicial.clone();
		atual.setCustoTotal(atual.getManhattan());
		visitados.add(atual);
		
		this.contadorDeVisitados = 0;
		this.totalDeFilhos = 0;
		this.maxFilhosGerados = 0;
		this.profundidade = 0;
		
		int contadorDeFilhos = 0;
		
		while(!atual.isEhSolucao()){
			contadorDeFilhos = 0;
			for (Tabuleiro vizinho : atual.expandeVizinhos()) {
				if (!visitados.contains(vizinho) && !tabuleirosGerados.contains(vizinho)) {
					vizinho.setCustoTotal(vizinho.getManhattan());
					tabuleirosGerados.add(vizinho);
					contadorDeFilhos++;
				}
			}
			
			contadorDeVisitados++;
			totalDeFilhos += contadorDeFilhos;
			
			if (contadorDeFilhos > maxFilhosGerados) {
				maxFilhosGerados = contadorDeFilhos;
			}
			
			sort(tabuleirosGerados);
			atual = tabuleirosGerados.get(0).clone();
			tabuleirosGerados.remove(0);
			visitados.add(atual);
			
			if (visitados.size() > maximoDeFilhosEmMemoria){
				break;
			}
		}
		
		if(atual.isEhSolucao()){
			while(atual.getPai() != null){
				this.solucao.add(atual);
				atual = atual.getPai();
			}
			
			solucao.add(tabuleiroInicial);
			profundidade = solucao.size();			
			
			return solucao;
		}
		
		return null;
	}
	
	
	/** BUSCA HEURÍSTICA COM HEURÍSTICA FORA DE POSIÇÃO
	 * @return
	 */
	public List<Tabuleiro> heuristicaForaDePosicao(){
		List<Tabuleiro> visitados = new ArrayList<Tabuleiro>();
		List<Tabuleiro> tabuleirosGerados = new ArrayList<Tabuleiro>();
		
		this.contadorDeVisitados = 0;
		this.totalDeFilhos = 0;
		this.maxFilhosGerados = 0;
		this.profundidade = 0;
		int contadorVizinhos = 0;
		
		Tabuleiro atual = tabuleiroInicial.clone();
		atual.setCustoTotal(atual.getForaDePosicao());
		visitados.add(atual);
		
		this.contadorDeVisitados = 0;
		this.maxFilhosGerados = 0;
		
		while(!atual.isEhSolucao()){
			contadorVizinhos = 0;
			for (Tabuleiro vizinho : atual.expandeVizinhos()) {
				if (!visitados.contains(vizinho) && !tabuleirosGerados.contains(vizinho)) {
					vizinho.setCustoTotal(vizinho.getForaDePosicao());
					tabuleirosGerados.add(vizinho); // adiciona a lista de filhos gerados, eliminando os já visitados		
					contadorVizinhos++;
				}
			}
			
			contadorDeVisitados++;
			totalDeFilhos += contadorVizinhos;
			
			if (contadorVizinhos > maxFilhosGerados) {
				maxFilhosGerados = contadorVizinhos;
			}
			
			sort(tabuleirosGerados);// Ordena todos os filhos já gerados
			
			atual = tabuleirosGerados.get(0).clone();
			tabuleirosGerados.remove(0);
			visitados.add(atual);
			
			if (visitados.size() > maximoDeFilhosEmMemoria){
				break;
			}
		}
		
		if(atual.isEhSolucao()){
			while(atual.getPai() != null){
				this.solucao.add(atual);
				atual = atual.getPai();
			}
			solucao.add(tabuleiroInicial);
			profundidade = solucao.size();	
			
			return solucao;
		}
		
		return null;
	}
	
//	public List<Tabuleiro> profundidade(){
//		List<Tabuleiro> visitados = new ArrayList<Tabuleiro>();
//		List<Tabuleiro> front = new ArrayList<Tabuleiro>();
//		Tabuleiro actual = tabuleiroInicial.clone();
//		actual.setCustoTotal(actual.getManhattan() * actual.getForaDePosicao());
//		visitados.add(actual);
//		this.contadorDeVisitados = 0;
//		this.fronteiraMaxima = 0;
//		
//		while(!actual.isEhSolucao()){
//			for (Tabuleiro visinhos : actual.expandeVizinhos()) {
//				if (!visitados.contains(visinhos) && !front.contains(visinhos)) {
//					front.add(visinhos);
//				}
//			}
//			actual = front.get(front.size()-1).clone();
//			front.remove(front.size()-1);
//			visitados.add(actual);
//			contadorDeVisitados++;
//			if (front.size() > fronteiraMaxima) {
//				fronteiraMaxima = front.size();
//			}
//		}
//		
//		if(actual.isEhSolucao()){
//			while(actual.getPai() != null){
//				this.solucao.add(actual);
//				actual = actual.getPai();
//			}
//			solucao.add(tabuleiroInicial);
//			return solucao;
//		}
//		
//		return null;
//	}
	
//	public List<Tabuleiro> solv(){
//	List<Tabuleiro> visitados = new ArrayList<Tabuleiro>();
//	List<Tabuleiro> front = new ArrayList<Tabuleiro>();
//	Tabuleiro tabuleiroAtual = tabuleiroInicial.clone();
//	visitados.add(tabuleiroAtual);
//	this.contador = 0;
//	this.fronteiraMaxima = 0;
//	while(!tabuleiroAtual.isEhSolucao()){
//		for (Tabuleiro vizinhos : tabuleiroAtual.expandeVizinhos()) {
//			if (!visitados.contains(vizinhos) && !front.contains(vizinhos)) {
//				front.add(vizinhos);
//			}
//		}
//		sort(front);
//		tabuleiroAtual = front.get(0).clone();
//		front.remove(0);
//		visitados.add(tabuleiroAtual);
//		contador++;
//		if (front.size() > fronteiraMaxima) {
//			fronteiraMaxima = front.size();
//		}
//	}
//	
//	if(tabuleiroAtual.isEhSolucao()){
//		while(tabuleiroAtual.getPai() != null){
//			this.solucao.add(tabuleiroAtual);
//			tabuleiroAtual = tabuleiroAtual.getPai();
//		}
//		solucao.add(tabuleiroInicial);
//		return solucao;
//	}
//	return null;
//}
	
	public void sort(List<Tabuleiro> tabuleiros){
		Collections.sort(tabuleiros, new Comparator<Tabuleiro>() {
			@Override
			public int compare(Tabuleiro o1, Tabuleiro o2) {
				return o1.getCustoTotal().compareTo(o2.getCustoTotal());
			}
		});
	}
	
	public List<Tabuleiro> getSolucao(){
		return this.solucao;
	}
	
	public Tabuleiro getTabuleiroInicial() {
		return tabuleiroInicial;
	}
}