package br.ufrn.imd.test.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import br.ufrn.imd.dao.git.ProjetoGitDAO;
import br.ufrn.imd.dao.git.SprintGitDAO;
import br.ufrn.imd.dao.git.TarefaGitDAO;
import br.ufrn.imd.dao.git.UsuarioGitDAO;
import br.ufrn.imd.dominio.Estado;
import br.ufrn.imd.dominio.Papel;
import br.ufrn.imd.dominio.Participacao;
import br.ufrn.imd.dominio.Projeto;
import br.ufrn.imd.dominio.Sprint;
import br.ufrn.imd.dominio.Tarefa;
import br.ufrn.imd.dominio.Usuario;

public class TestaGitDAOs {

	private static Usuario usuario;
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		buildUsuario();
		testaListagemProjetos();

		sc.close();
	}

	public static void buildUsuario() {
		usuario = new Usuario();

		String login, senha;

		System.out.print("Login: ");
		login = sc.nextLine();
		System.out.print("Senha: ");
		senha = sc.nextLine();

		usuario.setLogin(login);
		usuario.setSenha(senha);
	}

	public static void testaCriacaoTarefa() {
		ProjetoGitDAO dao = new ProjetoGitDAO(usuario);
		SprintGitDAO sDao = new SprintGitDAO(usuario);
		TarefaGitDAO tDao = new TarefaGitDAO(usuario);

		List<Projeto> projetos = dao.listarComBaseEm(usuario);

		Projeto projetoScrum = null;
		for (Projeto projeto : projetos) {
			if (projeto.getTitulo().equals("Scrumboard-Virtual")) {
				projetoScrum = projeto;
				break;
			}
		}

		List<Sprint> sprints = sDao.listarComBaseEm(projetoScrum);

		Random randomJava = new Random();
		int randomId = randomJava.nextInt();

		Sprint sprint = sprints.get(0);

		Tarefa tarefa = new Tarefa();

		tarefa.setId(randomId);
		tarefa.setTitulo("JSF Task");
		tarefa.setDescricao("Implementar os controllers JSF");
		tarefa.setEstado(Estado.OPEN);
		tarefa.setPontuacao(4);
		tarefa.setSprint(sprint);

		System.out.println();

		if (tDao.salvar(tarefa) != null) {
			System.out.println("Tarefa cadastrada com sucesso");
		} else {
			System.out.println("Erro no cadastro da tarefa");
		}

	}

	public static void testaListagemTarefa() {
		ProjetoGitDAO dao = new ProjetoGitDAO(usuario);
		SprintGitDAO sDao = new SprintGitDAO(usuario);
		TarefaGitDAO tDao = new TarefaGitDAO(usuario);

		List<Projeto> projetos = dao.listarComBaseEm(usuario);

		Projeto projetoScrum = null;
		for (Projeto projeto : projetos) {
			if (projeto.getTitulo().equals("Scrumboard-Virtual")) {
				projetoScrum = projeto;
				break;
			}
		}

		List<Sprint> sprints = sDao.listarComBaseEm(projetoScrum);

		List<Tarefa> tarefas;
		Usuario user;
		String nome = "";
		for (Sprint sprint : sprints) {
			System.out.println(sprint.getTitulo());
			tarefas = tDao.listarComBaseEm(sprint);
			for (Tarefa tarefa : tarefas) {
				user = tarefa.getUsuario();
				nome = (user != null) ? user.getLogin() : "--";
				
				System.out.println("\t" + tarefa.getId() + " - " + nome + " - " + tarefa.getTitulo() + " - " + tarefa.getEstado()
						+ " (" + tarefa.getPontuacao() + " pontos)");
			}
		}

	}

	public static void testaAtualizacaoTarefa() {
		ProjetoGitDAO dao = new ProjetoGitDAO(usuario);
		SprintGitDAO sDao = new SprintGitDAO(usuario);
		TarefaGitDAO tDao = new TarefaGitDAO(usuario);

		List<Projeto> projetos = dao.listarComBaseEm(usuario);

		Projeto projetoScrum = null;
		for (Projeto projeto : projetos) {
			if (projeto.getTitulo().equals("Scrumboard-Virtual")) {
				projetoScrum = projeto;
				break;
			}
		}

		List<Sprint> sprints = sDao.listarComBaseEm(projetoScrum);

		Sprint sprint = null;
		for(Sprint issue : sprints){
			if(issue.getTitulo().equals("Camada de Acesso a Dados")){
				sprint = issue;
				break;
			}
		}
		
		System.out.println(projetoScrum.getCoordenador());
		
		List<Tarefa> tarefas = tDao.listarComBaseEm(sprint);
		
		Tarefa task = null;
		for(Tarefa tarefa : tarefas){
			if(tarefa.getTitulo().equals("JSF Task")){
				System.out.println(tarefa.toString());
				task = tarefa;
				break;
			}
		}
		
		Usuario inaciogm = new Usuario();
		inaciogm.setLogin("inaciogm");
		inaciogm.setNome("Inácio Gomes");
		
		task.setSprint(sprint);
		task.setPontuacao(20);
		task.setUsuario(inaciogm);
		tDao.atualizarTarefa(task);
		
		System.out.println("Alterações efetuadas");
		
	}

	public static void testaCriacaoSprint() {
		ProjetoGitDAO dao = new ProjetoGitDAO(usuario);
		SprintGitDAO sDao = new SprintGitDAO(usuario);

		List<Projeto> projetos = dao.listarComBaseEm(usuario);

		Projeto projetoScrum = null;
		for (Projeto projeto : projetos) {
			if (projeto.getTitulo().equals("Scrumboard-Virtual")) {
				projetoScrum = projeto;
				break;
			}
		}

		Sprint sprint = new Sprint();
		sprint.setTitulo("Camada de Apresentação");
		sprint.setDescricao("Construção da camada de apresentação");
		try {
			sprint.setDataFinalizacao(new SimpleDateFormat("dd/MM/yyyy").parse("01/06/2016"));
		} catch (ParseException e) {
			sprint.setDataFinalizacao(Calendar.getInstance().getTime());
		}
		sprint.setProjeto(projetoScrum);

		if (sDao.salvar(sprint) != null) {
			System.out.println("Sprint criada com sucesso!");
		} else {
			System.out.println("Falha na criação da Sprint!");
		}
	}

	public static void testaListagemSprint() {
		ProjetoGitDAO dao = new ProjetoGitDAO(usuario);
		SprintGitDAO sDao = new SprintGitDAO(usuario);

		List<Projeto> projetos = dao.listarComBaseEm(usuario);

		Projeto projetoScrum = null;
		for (Projeto projeto : projetos) {
			if (projeto.getTitulo().equals("Personal")) {
				projetoScrum = projeto;
				break;
			}
		}

		List<Sprint> sprints = sDao.listarComBaseEm(projetoScrum);

		for (Sprint sprint : sprints) {
			System.out.println(sprint.getTitulo());
			System.out.println(sprint.getDescricao());
			System.out.println(sprint.getDataFinalizacao());
			System.out.println("Projeto " + sprint.getProjeto().getTitulo());
			System.out.println("-------------------------------------");
		}
	}

	public static void testaCriacaoProjeto() {
		Random randomJava = new Random();
		int randomId = randomJava.nextInt();

		Projeto projeto = new Projeto();
		projeto.setId(randomId);
		projeto.setTitulo("Scrumboard Virtual");
		projeto.setDescricao("Distribui automaticamente tarefas scrum e provê visualização delas");
		projeto.setCoordenador(usuario);

		ProjetoGitDAO dao = new ProjetoGitDAO(usuario);
		projeto = dao.salvar(projeto);

		if (projeto != null) {
			System.out.println("Projeto salvo!");
		}
	}

	public static void testaListagemProjetos() {
		ProjetoGitDAO dao = new ProjetoGitDAO(usuario);
		List<Projeto> projetos = dao.listarComBaseEm(usuario);

		Participacao participacao;
		Papel papel;
		for (Projeto projeto : projetos) {
			participacao = projeto.getParticipacoes().get(0);
			papel = participacao.getPapel();

			System.out.println(projeto.getId() + " - " + projeto.getTitulo());
			System.out.println("\t" + projeto.getDescricao());
			System.out.println("Você é " + papel + " neste projeto");
		}
	}

	public static void testaAdicaoColaboradorProjeto() {
		Usuario coordenador = new Usuario();
		coordenador.setNome("Inácio Medeiros");
		coordenador.setLogin("inaciomdrs");

		Projeto projeto = new Projeto();
		projeto.setTitulo("Scrumboard-Virtual");
		projeto.setCoordenador(coordenador);

		Usuario colaborador = new Usuario();
		colaborador.setNome("Inácio G. Medeiros");
		colaborador.setLogin("inaciogm");

		ProjetoGitDAO dao = new ProjetoGitDAO(usuario);

		if (dao.adicionarUsuarioAoProjeto(colaborador, projeto)) {
			System.out.println("Adição feita com sucesso!");
		} else {
			System.out.println("Falha na adição!");
		}
	}

	public static void testaListagemUsuarios() {
		Usuario coordenador = new Usuario();
		coordenador.setNome("Pitágoras Alves");
		coordenador.setLogin("pentalpha");

		Projeto projeto = new Projeto();
		projeto.setTitulo("sequencer");
		projeto.setCoordenador(coordenador);

		UsuarioGitDAO dao = new UsuarioGitDAO(usuario);

		List<Usuario> usuarios = dao.listarComBaseEm(projeto);

		Participacao participacao;
		for (Usuario usuario : usuarios) {
			participacao = usuario.getParticipacoes().get(0);
			System.out.println(usuario.getNome() + " - " + usuario.getLogin() + "(" + participacao.getPapel() + ")");
		}

	}

	public static void testaBuscaUsuarios() {
		String login = "itamir";

		UsuarioGitDAO dao = new UsuarioGitDAO(usuario);

		Usuario usuario = dao.buscarUsuario(login);

		if (usuario != null) {
			System.out.println(usuario.getLogin() + " - " + usuario.getId() + " - " + usuario.getNome());
		} else {
			System.out.println("Não foram encontrados resultados");
		}

	}

}
