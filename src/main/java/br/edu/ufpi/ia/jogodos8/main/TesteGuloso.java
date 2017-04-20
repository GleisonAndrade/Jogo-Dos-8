/**
 * 
 */
package br.edu.ufpi.ia.jogodos8.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ufpi.ia.jogodos8.buscas.SolucaoGulosa;
import br.edu.ufpi.ia.jogodos8.modelo.Ponto;
import br.edu.ufpi.ia.jogodos8.modelo.Tabuleiro;

/**
 * @author gleison
 *
 */
public class TesteGuloso {
	public static void main(String[] args) {
		GeradorTabuleiro gt = new GeradorTabuleiro();
		int QNTTESTE = 10;
		int maximoDeFilhosGerados = 10000;

		Map<Integer, Ponto> solucaoDoProblema = new HashMap<Integer, Ponto>();

		solucaoDoProblema.put(1, new Ponto(0, 0));
		solucaoDoProblema.put(2, new Ponto(0, 1));
		solucaoDoProblema.put(3, new Ponto(0, 2));
		solucaoDoProblema.put(4, new Ponto(1, 0));
		solucaoDoProblema.put(5, new Ponto(1, 1));
		solucaoDoProblema.put(6, new Ponto(1, 2));
		solucaoDoProblema.put(7, new Ponto(2, 0));
		solucaoDoProblema.put(8, new Ponto(2, 1));
		solucaoDoProblema.put(0, new Ponto(2, 2));

		long somaTempo = 0;
		int somaVisitados = 0;
		int somaMaxFilhosGerados = 0;
		int somaFilhosGerados = 0;
		int somaProfundidade = 0;
		
		List<Tabuleiro> solucao = null;
		int[][] tabuleiroInicial = {{5, 8, 2}, 
				{0, 4, 3}, 
				{1, 6, 7}};
//		int[][] tabuleiroInicial = {{7, 8, 2}, 
//		{0, 3, 4}, 
//		{5, 1, 6}};
		
//		int[][] tabuleiroInicial = {{1, 8, 2}, 
//				{0, 4, 3}, 
//				{7, 6, 5}};

//		int[][] tabuleiroInicial = gt.gerarTabuleiro();
		
		System.out.println("<<< TESTE BUSCA GULOSA MANHATTAN >>>");
		for (int teste = 0; teste < QNTTESTE; teste++) {

			// gt.imprimir(tabuleiroInicial);

			Tabuleiro problemaInicial = new Tabuleiro(tabuleiroInicial, solucaoDoProblema, 0, null);
			solucao = null;


			SolucaoGulosa busca = null;


			busca = new SolucaoGulosa(problemaInicial, maximoDeFilhosGerados);

			long start = System.currentTimeMillis();
			solucao = busca.gulosoManhattan();
			long end = System.currentTimeMillis();
			
			if (solucao == null){
				System.out.println("SEM SOLUÇÃO: ");
				gt.imprimir(tabuleiroInicial);
				break;
			}

			somaVisitados += busca.contadorDeVisitados;
			somaMaxFilhosGerados += busca.maxFilhosGerados;
			somaFilhosGerados += busca.totalDeFilhos;
			somaProfundidade += (busca.profundidade - 1);

			somaTempo += end - start;
		}

		System.out.println("Tempo médio de Execução: " + (somaTempo / 10) + " milisegundos");
		if (solucao != null){		
			System.out.println("Total de tabuleiros visitados: " + (somaVisitados / 10));
			System.out.println("Máximo de filhos gerados: " + (somaMaxFilhosGerados / 10));
			System.out.println("Total de filhos gerados: " + (somaFilhosGerados / 10));
			System.out.println("Profundidade da solução: " + (somaProfundidade / 10));
			
			System.out.println("\t\tSOLUÇÃO DO ÚLTIMO PROBLEMA GERADO");
		
			for (int i = solucao.size() - 1; i >= 0; i--) {
				System.out.println(solucao.get(i).toString());
				System.out.println("");
			}
		}
		
		
		
		System.out.println("<<< TESTE BUSCA GULOSA FORA DE POSIÇÃO >>>");
		for (int teste = 0; teste < QNTTESTE; teste++) {

			// gt.imprimir(tabuleiroInicial);

			Tabuleiro problemaInicial = new Tabuleiro(tabuleiroInicial, solucaoDoProblema, 0, null);
			solucao = null;


			SolucaoGulosa busca = null;


			busca = new SolucaoGulosa(problemaInicial, maximoDeFilhosGerados);

			long start = System.currentTimeMillis();
			solucao = busca.gulosoForaDePosicao();
			long end = System.currentTimeMillis();
			
			if (solucao == null){
				System.out.println("SEM SOLUÇÃO: ");
				gt.imprimir(tabuleiroInicial);
				break;
			}

			somaVisitados += busca.contadorDeVisitados;
			somaMaxFilhosGerados += busca.maxFilhosGerados;
			somaFilhosGerados += busca.totalDeFilhos;
			somaProfundidade += (busca.profundidade - 1);

			somaTempo += end - start;
		}

		System.out.println("Tempo médio de Execução: " + (somaTempo / 10) + " milisegundos");
		if (solucao != null){		
			System.out.println("Total de tabuleiros visitados: " + (somaVisitados / 10));
			System.out.println("Máximo de filhos gerados: " + (somaMaxFilhosGerados / 10));
			System.out.println("Total de filhos gerados: " + (somaFilhosGerados / 10));
			System.out.println("Profundidade da solução: " + (somaProfundidade / 10));
			
			System.out.println("\t\tSOLUÇÃO DO ÚLTIMO PROBLEMA GERADO");
		
			for (int i = solucao.size() - 1; i >= 0; i--) {
				System.out.println(solucao.get(i).toString());
				System.out.println("");
			}
		}
		
		
		
		
	}
}
