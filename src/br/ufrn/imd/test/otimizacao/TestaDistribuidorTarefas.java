package br.ufrn.imd.test.otimizacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.ufrn.imd.dominio.Tarefa;
import br.ufrn.imd.dominio.Usuario;
import br.ufrn.imd.otimizacao.DistribuidorTarefas;

public class TestaDistribuidorTarefas {

	private static Random random = new Random(System.currentTimeMillis());

	public static void main(String[] args) {
		List<Tarefa> tarefas = gerarTarefas(10);
		List<Usuario> usuarios = gerarUsuarios(3);

		DistribuidorTarefas.getInstance().distribuirTarefas(tarefas, usuarios);

		for (Tarefa tarefa : tarefas) {
			System.out.println(
					tarefa.getTitulo() + " - " + tarefa.getPontuacao() + " pontos - " + tarefa.getUsuario().getLogin());
		}
		
		System.out.println();
		
		int somaTotal;
		List<Tarefa> tasks;
		for(Usuario usuario : usuarios){
			tasks = usuario.getTarefas();
			somaTotal = 0;
			for(Tarefa tarefa : tasks){
				somaTotal += tarefa.getPontuacao();
			}
			System.out.println(usuario.getLogin() + " - " + tasks.size() + " tarefas - " + somaTotal + " pontos");
		}
		
		
	}

	public static List<Tarefa> gerarTarefas(int quantidade) {
		List<Tarefa> tarefas = new ArrayList<Tarefa>();

		Tarefa tarefa;
		for (int i = 0; i < quantidade; i++) {
			tarefa = new Tarefa();
			tarefa.setTitulo("Titulo " + i);
			tarefa.setDescricao("Descrição " + i);
			tarefa.setPontuacao(random.nextInt(100));
			tarefas.add(tarefa);
		}

		return tarefas;
	}

	public static List<Usuario> gerarUsuarios(int quantidade) {
		List<Usuario> usuarios = new ArrayList<Usuario>();

		Usuario usuario;
		for (int i = 0; i < quantidade; i++) {
			usuario = new Usuario();
			usuario.setLogin("user" + i);
			usuario.setNome("User " + i);
			usuarios.add(usuario);
		}

		return usuarios;
	}

}
