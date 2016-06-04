package br.ufrn.imd.otimizacao;

import java.util.Comparator;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.IntegerSBXCrossover;
import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.comparator.ObjectiveComparator;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;

import br.ufrn.imd.dominio.Tarefa;
import br.ufrn.imd.dominio.Usuario;

public class DistribuidorTarefas {

	private static DistribuidorTarefas distribuidorTarefas;

	private Problem<IntegerSolution> problem;
	private CrossoverOperator<IntegerSolution> crossover;
	private MutationOperator<IntegerSolution> mutation;
	private SelectionOperator<List<IntegerSolution>, IntegerSolution> selection;
	private Algorithm<List<IntegerSolution>> algorithm;
	private Comparator<IntegerSolution> comparator;
	private final int FIRST_OBJECTIVE;
	private final int BEST_SOLUTION_INDEX;

	private DistribuidorTarefas() {
		FIRST_OBJECTIVE     = 0;
		BEST_SOLUTION_INDEX = 0;
	}

	public static DistribuidorTarefas getInstance() {
		if(distribuidorTarefas == null){
			distribuidorTarefas = new DistribuidorTarefas();
		}
		return distribuidorTarefas;
	}

	public void distribuirTarefas(List<Tarefa> tarefas, List<Usuario> usuarios) {
		int quantidadePessoas = usuarios.size();

		buildProblem(tarefas, quantidadePessoas);
		buildOperators();
		buildAlgorithm();

		new AlgorithmRunner.Executor(algorithm).execute();

		List<IntegerSolution> population = algorithm.getResult();
		
		comparator = new ObjectiveComparator<IntegerSolution>(FIRST_OBJECTIVE);
		population.sort(comparator);
		
		IntegerSolution melhorSolucao = population.get(BEST_SOLUTION_INDEX);
						
		aplicarSolucaoNasTarefas(melhorSolucao, tarefas, usuarios);
	}
	
	private void aplicarSolucaoNasTarefas(IntegerSolution solucao, List<Tarefa> tarefas, List<Usuario> usuarios){
		// A posição do vetor é a posição da tarefa na lista de tarefas
		// e o valor guardado nesta posição é a posição da pessoa da lista
		// de usuários
		
		int[] associacaoPessoaTarefa = extrairAssociacoesDaSolucao(solucao);
		int quantidadeTarefas = associacaoPessoaTarefa.length;
		
		Tarefa tarefa;
		Usuario usuario;
		for (int tarefaPos = 0; tarefaPos < quantidadeTarefas; tarefaPos++) {
			tarefa  = tarefas.get(tarefaPos);
			usuario = usuarios.get(associacaoPessoaTarefa[tarefaPos]);
			
			tarefa.setUsuario(usuario);
		}
	}
	
	private int[] extrairAssociacoesDaSolucao(IntegerSolution solucao){
		int tamanhoSolucao = solucao.getNumberOfVariables();
		
		int[] vetorInteiros = new int[tamanhoSolucao];
		
		for (int i = 0; i < tamanhoSolucao; i++) {
			vetorInteiros[i] = solucao.getVariableValue(i);
		}
		
		return vetorInteiros;
	}

	private void buildProblem(List<Tarefa> tarefas, int quantidadePessoas) {
		int numeroPesos = tarefas.size();

		int[] pesos = new int[numeroPesos];

		for (int i = 0; i < numeroPesos; i++) {
			pesos[i] = tarefas.get(i).getPontuacao();
		}

		problem = new DistribuicaoTarefasProblem(pesos, quantidadePessoas);
	}

	private void buildOperators() {
		double crossoverProbability = 0.9;
		double crossoverDistributionIndex = 20.0;
		crossover = new IntegerSBXCrossover(crossoverProbability, crossoverDistributionIndex);

		double mutationProbability = 1.0 / problem.getNumberOfVariables();
		double mutationDistributionIndex = 20.0;
		mutation = new IntegerPolynomialMutation(mutationProbability, mutationDistributionIndex);

		selection = new BinaryTournamentSelection<IntegerSolution>(
				new RankingAndCrowdingDistanceComparator<IntegerSolution>());
	}

	private void buildAlgorithm() {
		algorithm = new NSGAIIBuilder<IntegerSolution>(problem, crossover, mutation).setSelectionOperator(selection)
				.setMaxIterations(100).setPopulationSize(100).build();
	}

}
