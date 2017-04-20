///**
// * 
// */
//package br.edu.ufpi.ia.jogodos8.main;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import br.edu.ufpi.ia.jogodos8.buscas.SolucaoHeuristica;
//import br.edu.ufpi.ia.jogodos8.modelo.Ponto;
//import br.edu.ufpi.ia.jogodos8.modelo.Tabuleiro;
//
///**
// * @author gleison
// *
// */
//public class Main {
//	private static final long KILOBYTE = 1024L;
//
//	public static long bytesToMegabytes(long bytes) {
//		return bytes / KILOBYTE;
//	}
//
//	public static void main(String[] args) {
//		int[][] tabuleiroInicial = {{1, 8, 2}, 
//									{0, 4, 3}, 
//									{7, 6, 5}};
//		
//		Map<Integer, Ponto> solucaoDoProblema = new HashMap<Integer, Ponto>();
//		
//		solucaoDoProblema.put(1, new Ponto(0, 0));
//		solucaoDoProblema.put(2, new Ponto(0, 1));
//		solucaoDoProblema.put(3, new Ponto(0, 2));
//		solucaoDoProblema.put(4, new Ponto(1, 0));
//		solucaoDoProblema.put(5, new Ponto(1, 1));
//		solucaoDoProblema.put(6, new Ponto(1, 2));
//		solucaoDoProblema.put(7, new Ponto(2, 0));
//		solucaoDoProblema.put(8, new Ponto(2, 1));
//		solucaoDoProblema.put(0, new Ponto(2, 2));
//
//		Tabuleiro problemaInicial = new Tabuleiro(tabuleiroInicial, solucaoDoProblema, 0, null);
//		List<Tabuleiro> solution = null;
//		
//		long sum = 0;
//		SolucaoHeuristica solver = null;
//		
//		for (int i = 0; i < 10; i++) {
////			BlindSolver solver = new BlindSolver(boardInicial);
//			solver = new SolucaoHeuristica(problemaInicial);
//			long start = System.currentTimeMillis();
//			solution = solver.heuristicaForaDePosicao();
//			long end = System.currentTimeMillis();
//			sum += end - start;
//		}
//		System.out.println("Tempo médio de Execução: " + (sum / 10) + " milisegundos");
//		// Get the Java runtime
//	    Runtime runtime = Runtime.getRuntime();
//	    // Run the garbage collector
//	    runtime.gc();
//	    // Calculate the used memory
//	    long memory = runtime.totalMemory() - runtime.freeMemory();
//	    System.out.println("Memoria utilizada em bytes: " + memory);
//	    System.out.println("Memoria utilizada em kilobytes: " + bytesToMegabytes(memory));
//	    
//	    System.out.println("Max Front: " + solver.maxFilhosGerados);
//	    System.out.println("Número de Visitados: " + solver.contadorDeVisitados);
//	    
//		for (int i = solution.size() - 1; i >= 0; i--) {
//			System.out.println(solution.get(i).toString());
//			System.out.println("");
//		}		
//	}
//
//}
