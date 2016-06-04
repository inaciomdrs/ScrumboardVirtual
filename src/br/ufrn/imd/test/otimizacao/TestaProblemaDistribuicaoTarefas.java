package br.ufrn.imd.test.otimizacao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

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

import br.ufrn.imd.otimizacao.DistribuicaoTarefasProblem;

public class TestaProblemaDistribuicaoTarefas {

	public static void main(String[] args) {
		Problem<IntegerSolution> problem;
		Algorithm<List<IntegerSolution>> algorithm;
		CrossoverOperator<IntegerSolution> crossover;
		MutationOperator<IntegerSolution> mutation;
		SelectionOperator<List<IntegerSolution>, IntegerSolution> selection;

		Random R = new Random();

		int[] pesos = new int[10];
		int numPessoas = 3;

		for (int i = 0; i < pesos.length; i++) {
			pesos[i] = R.nextInt(20) + 5;
		}

		problem = new DistribuicaoTarefasProblem(pesos, numPessoas);

		double crossoverProbability = 0.9;
		double crossoverDistributionIndex = 20.0;
		crossover = new IntegerSBXCrossover(crossoverProbability, crossoverDistributionIndex);

		double mutationProbability = 1.0 / problem.getNumberOfVariables();
		double mutationDistributionIndex = 20.0;
		mutation = new IntegerPolynomialMutation(mutationProbability, mutationDistributionIndex);

		selection = new BinaryTournamentSelection<IntegerSolution>(
				new RankingAndCrowdingDistanceComparator<IntegerSolution>());

		algorithm = new NSGAIIBuilder<IntegerSolution>(problem, crossover, mutation).setSelectionOperator(selection)
				.setPopulationSize(100).setMaxIterations(25000).build();

		new AlgorithmRunner.Executor(algorithm).execute();

		List<IntegerSolution> population = algorithm.getResult();

		HashMap<Integer, ArrayList<Integer>> pessoaTarefa = new HashMap<Integer, ArrayList<Integer>>();

		System.out.println(pesos.length + " Tarefas para " + numPessoas + " pessoas");

		population.sort(new ObjectiveComparator<IntegerSolution>(0));

		IntegerSolution solution = population.get(0);

		pessoaTarefa.clear();

		System.out.println("==================================");

		int pessoa;
		for (int tarefa = 0; tarefa < solution.getNumberOfVariables(); tarefa++) {
			pessoa = solution.getVariableValue(tarefa);

			if (pessoaTarefa.get(pessoa) == null) {
				pessoaTarefa.put(pessoa, new ArrayList<Integer>());
			}

			pessoaTarefa.get(pessoa).add(tarefa);

		}

		System.out.print("\n");

		List<Integer> tarefas = null;
		for (Entry<Integer, ArrayList<Integer>> entry : pessoaTarefa.entrySet()) {
			System.out.print("Pessoa " + entry.getKey() + " - Tarefas ");
			tarefas = pessoaTarefa.get(entry.getKey());
			for (Integer tar : tarefas) {
				System.out.print(tar + ", ");
			}
			System.out.print("\n");
		}

		tarefas.clear();

		System.out.println("==================================");

	}

}
