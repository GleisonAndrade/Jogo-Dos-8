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
public class SolucaoGulosa {
	private Tabuleiro tabuleiroInicial; // tabuleiro inicial do problema
	private List<Tabuleiro> solucao; // lista dos tabueiros da arvore que fazem parte da solução
	private int maximoDeFilhosEmMemoria; // máximo de filhos que serão mantidos em memoria
	
	public int contadorDeVisitados; // contador de filhos visitados
	public int maxFilhosGerados; // quantos filhos no máximo foram gerados por um tabuleiro
	public int totalDeFilhos; // total de filhos gerados para solucionar o problema
	public int profundidade; // profundidade da arvore
	
	public SolucaoGulosa(Tabuleiro inicial, int maximoDeFilhosGerados) {
		this.tabuleiroInicial = inicial;
		this.solucao = new ArrayList<Tabuleiro>();
		this.contadorDeVisitados = 0;
		this.totalDeFilhos = 0;
		this.maxFilhosGerados = 0;
		this.profundidade = 0;
		this.maximoDeFilhosEmMemoria = maximoDeFilhosGerados;
	}
	
	/** BUSCA GULOSA COM HEURÍSTICA DE MANHATTAN
	 * @return
	 */
	public List<Tabuleiro> gulosoManhattan(){
		List<Tabuleiro> visitados = new ArrayList<Tabuleiro>();
		List<Tabuleiro> tabuleirosGerados = new ArrayList<Tabuleiro>();
		
		this.contadorDeVisitados = 0;
		this.totalDeFilhos = 0;
		this.maxFilhosGerados = 0;
		this.profundidade = 0;
		
		int contadorVizinhos = 0;
		
		Tabuleiro atual = tabuleiroInicial.clone();
		atual.setCustoTotal(atual.getManhattan());
		visitados.add(atual);
		
		while(!atual.isEhSolucao()){
			contadorVizinhos = 0;
			List<Tabuleiro> tabuleirosGeradosLocal = new ArrayList<Tabuleiro>();
			
			for (Tabuleiro vizinho : atual.expandeVizinhos()) {
				if (!visitados.contains(vizinho) && !tabuleirosGerados.contains(vizinho)) {
					vizinho.setCustoTotal(vizinho.getManhattan());
					tabuleirosGerados.add(vizinho); // Todos os que já foram gerados
					tabuleirosGeradosLocal.add(vizinho); // Os que foram gerados apartir do tabuleiro atual
					contadorVizinhos++;
				}
			}
			
			contadorDeVisitados++;
			totalDeFilhos += contadorVizinhos;
			
			if (contadorVizinhos > maxFilhosGerados) {
				maxFilhosGerados = contadorVizinhos;
			}
			
//			
//			atual = tabuleirosGerados.get(0).clone();
//			tabuleirosGerados.remove(0);
			
			sort(tabuleirosGeradosLocal); // Ordena os tabuleiros gerados a partir no atual
			sort(tabuleirosGerados);// Ordena os tabuleiros gerados em toda a busca já realizada
			
			if (tabuleirosGeradosLocal.size() > 0){
				atual = tabuleirosGeradosLocal.get(0).clone();
				tabuleirosGeradosLocal.remove(0);
			}else{
				atual = tabuleirosGerados.get(0).clone();
			}
			
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
	
	/** BUSCA GULOSA COM HEURÍSTICA DE FORA DE POSIÇÃO
	 * @return
	 */
	public List<Tabuleiro> gulosoForaDePosicao(){
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
		
		while(!atual.isEhSolucao()){
			contadorVizinhos = 0;
			List<Tabuleiro> tabuleirosGeradosLocal = new ArrayList<Tabuleiro>();
			for (Tabuleiro vizinho : atual.expandeVizinhos()) {
				if (!visitados.contains(vizinho) && !tabuleirosGerados.contains(vizinho)) {
					vizinho.setCustoTotal(vizinho.getForaDePosicao());
					tabuleirosGerados.add(vizinho);
					tabuleirosGeradosLocal.add(vizinho);
					contadorVizinhos++;
				}
			}
			
			contadorDeVisitados++;
			totalDeFilhos += contadorVizinhos;
			
			if (contadorVizinhos > maxFilhosGerados) {
				maxFilhosGerados = contadorVizinhos;
			}
			
//			
//			atual = tabuleirosGerados.get(0).clone();
//			tabuleirosGerados.remove(0);
			
			sort(tabuleirosGeradosLocal);
			sort(tabuleirosGerados);
			
			if (tabuleirosGeradosLocal.size() > 0){
				atual = tabuleirosGeradosLocal.get(0).clone();
				tabuleirosGeradosLocal.remove(0);
			}else{
				atual = tabuleirosGerados.get(0).clone();
			}
			
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
	
	public void sort(List<Tabuleiro> boards){
		Collections.sort(boards, new Comparator<Tabuleiro>() {
			@Override
			public int compare(Tabuleiro o1, Tabuleiro o2) {
				return o1.getCustoTotal().compareTo(o2.getCustoTotal());
			}
		});
	}
	
	public List<Tabuleiro> getSolution(){
		return this.solucao;
	}
	
	public Tabuleiro getBoardInicial() {
		return tabuleiroInicial;
	}
}