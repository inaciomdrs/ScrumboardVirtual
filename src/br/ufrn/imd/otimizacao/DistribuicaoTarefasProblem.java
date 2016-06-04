package br.ufrn.imd.otimizacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;

public class DistribuicaoTarefasProblem extends AbstractIntegerProblem {

	private static final long serialVersionUID = -4710218917872970813L;
	
	private int[] pesosDasTarefas;
	private int[] pesoTotalPorPessoa;
	private int quantidadeDePessoas;
	private int quantidadeDeTarefas;
	
		
	public DistribuicaoTarefasProblem(int[] pesosDasTarefas, int quantidadeDePessoas) {
		super();
		
		this.quantidadeDeTarefas = pesosDasTarefas.length;
		this.pesosDasTarefas = pesosDasTarefas;
		this.quantidadeDePessoas = quantidadeDePessoas;
		
		setNumberOfVariables(quantidadeDeTarefas);
	    setNumberOfObjectives(1);
	    setName("DistribuicaoTarefasProblem");

	    List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
	    List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

	    for (int i = 0; i < getNumberOfVariables(); i++) {
	      lowerLimit.add(0);
	      upperLimit.add(quantidadeDePessoas-1);
	    }

	    setLowerLimit(lowerLimit);
	    setUpperLimit(upperLimit);
	}

	/*
	 * Estrutura do cromossomo:
	 * C = [C_1, ..., C_n]
	 * n = quantidade de tarefas
	 * C_i = id da pessoa à qual a tarefa i foi associada
	 */
	@Override
	public void evaluate(IntegerSolution solution) {
		pesoTotalPorPessoa = new int[quantidadeDePessoas];
		Arrays.fill(pesoTotalPorPessoa, 0);
					
		int idPessoa;
		for (int tarefa = 0; tarefa < quantidadeDeTarefas; tarefa++) {
			idPessoa = solution.getVariableValue(tarefa);
			pesoTotalPorPessoa[idPessoa] += pesosDasTarefas[tarefa];
		}
						
		double variancia = 0;
		double media = 0;
		
		for (int pesoTotal = 0; pesoTotal < quantidadeDePessoas; pesoTotal++) {
			media += pesoTotalPorPessoa[pesoTotal];
		}
		
		media = media / quantidadeDePessoas;
		
		for (int pesoTotal = 0; pesoTotal < quantidadeDePessoas; pesoTotal++) {
			variancia += Math.pow((pesoTotalPorPessoa[pesoTotal]-media), 2);
		}
		
		variancia  = variancia / quantidadeDePessoas;
		
		solution.setObjective(0, Math.sqrt(variancia));
	}

	public int[] getPesosDasTarefas() {
		return pesosDasTarefas;
	}

	public void setPesosDasTarefas(int[] pesosDasTarefas) {
		this.pesosDasTarefas = pesosDasTarefas;
	}

	public int getQuantidadeDePessoas() {
		return quantidadeDePessoas;
	}

	public void setQuantidadeDePessoas(int quantidadeDePessoas) {
		this.quantidadeDePessoas = quantidadeDePessoas;
	}

	public int getQuantidadeDeTarefas() {
		return quantidadeDeTarefas;
	}

}
