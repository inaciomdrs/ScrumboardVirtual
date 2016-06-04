package br.ufrn.imd.test.otimizacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.comparator.ObjectiveComparator;

import br.ufrn.imd.otimizacao.DistribuicaoTarefasProblem;

public class TestaSolutionsSorting {

	public static void main(String[] args) {
		Problem<IntegerSolution> problem;

		Random R = new Random();

		int numTarefas = 100;
		int numPessoas = 35;
		
		int[] pesos = new int[numTarefas];
		
		for (int i = 0; i < pesos.length; i++) {
			pesos[i] = R.nextInt(20) + 5;
		}

		problem = new DistribuicaoTarefasProblem(pesos, numPessoas);
		
		List<IntegerSolution> solutions = new ArrayList<IntegerSolution>(10);

		IntegerSolution s;
		for (int i = 0; i < 100; i++) {
			s = problem.createSolution();
			problem.evaluate(s);
			solutions.add(s);
		}
		
		solutions.sort(new ObjectiveComparator<IntegerSolution>(0));
		
		solutions.forEach((solution) -> System.out.println(solution.getObjective(0)));

	}

}
