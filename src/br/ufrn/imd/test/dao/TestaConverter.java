package br.ufrn.imd.test.dao;

public class TestaConverter {

//	static Usuario inacio, itamir, isaac, ivan;
//	static Projeto web2;
//	static Projeto embarcados;
//	static Sprint sprintWeb;
//	static Sprint sprintEmbarcados;
//	static Tarefa tarefaAWeb;
//	static Tarefa tarefaBWeb;
//	static Tarefa tarefaAEmbarcados;
//	static Tarefa tarefaBEmbarcados;
//	static Participacao partA;
//	static Participacao partB;
//	static Participacao partC;
//	static Participacao partD;
//	
//	static User user;
//	static Issue issue;
//	static Milestone milestone;
//	static Repository repo;
//	
//	static Usuario usuario;
//	static Tarefa tarefa;
//	static Sprint sprint;
//	static Projeto projeto;

	public static void main(String[] args) {
//		initializeObjects();
//		convertToGit();
//		convertToNormal();
	}
	
//	public static void convertToNormal(){
//		usuario = new UsuarioConverter().convertToObject(user);
//
//		System.out.println(usuario.getNome());
//		System.out.println(usuario.getLogin());
//		
//		System.out.println("=========================================");
//		
//		tarefa = new TarefaConverter().convertToObject(issue);
//		
//		System.out.println(tarefa.getTitulo());
//		System.out.println(tarefa.getDescricao());
//		System.out.println(tarefa.getPontuacao());
//		System.out.println(tarefa.getEstado());
//		
//		
//		System.out.println("=========================================");
//		
//		sprint = new SprintConverter().convertToObject(milestone);
//		System.out.println(sprint.getTitulo());
//		System.out.println(sprint.getDescricao());
//		System.out.println(sprint.getDataFinalizacao());
//		
//		System.out.println("=========================================");
//		
//		projeto = new ProjetoConverter().convertToObject(repo);
//		System.out.println(projeto.getTitulo());
//		System.out.println(projeto.getDescricao());
//	}
//	
//	public static void convertToGit(){
//		user = new UsuarioConverter().convertToGitObject(inacio);
//
//		System.out.println(user.getName());
//		System.out.println(user.getLogin());
//		
//		System.out.println("=========================================");
//		
//		issue = new TarefaConverter().convertToGitObject(tarefaAWeb);
//		
//		System.out.println(issue.getTitle());
//		System.out.println(issue.getBody());
//		
//		List<Label> labels = issue.getLabels();
//		
//		for(Label label : labels){
//			System.out.println(label.getName());
//		}
//		
//		System.out.println("=========================================");
//		
//		milestone = new SprintConverter().convertToGitObject(sprintWeb);
//		System.out.println(milestone.getTitle());
//		System.out.println(milestone.getDescription());
//		System.out.println(milestone.getDueOn());
//		
//		System.out.println("=========================================");
//		
//		repo = new ProjetoConverter().convertToGitObject(embarcados);
//		System.out.println(repo.getName());
//		System.out.println(repo.getDescription());
//
//	}
//
//	public static void initializeObjects() {
//		inacio = new Usuario();
//		itamir = new Usuario();
//		isaac = new Usuario();
//		ivan = new Usuario();
//
//		inacio.setNome("inacio");
//		inacio.setLogin("boyinacio");
//		inacio.setSenha("123");
//
//		itamir.setNome("itamir");
//		itamir.setLogin("itamir");
//		itamir.setSenha("123");
//
//		ivan.setNome("ivanovitch");
//		ivan.setLogin("ivan");
//		ivan.setSenha("123");
//
//		isaac.setNome("isaac");
//		isaac.setLogin("isaac");
//		isaac.setSenha("123");
//
//		web2 = new Projeto();
//		embarcados = new Projeto();
//
//		web2.setTitulo("Web 2");
//		web2.setDescricao("Projeto de Web2");
//
//		embarcados.setTitulo("Embarcados");
//		embarcados.setDescricao("Projeto de sistemas embarcados");
//
//		sprintWeb = new Sprint();
//		sprintEmbarcados = new Sprint();
//
//		sprintWeb.setTitulo("Sprint A - Web");
//		sprintWeb.setDescricao("Desenvolver a interface grafica");
//		try {
//			sprintWeb.setDataFinalizacao(new SimpleDateFormat("dd/MM/yyyy").parse("01/06/2016"));
//		} catch (ParseException e) {
//			sprintWeb.setDataFinalizacao(Calendar.getInstance().getTime());
//		}
//
//		sprintEmbarcados.setTitulo("Sprint A - Embarcados");
//		sprintEmbarcados.setDescricao("Desenvolver o circuito elétrico");
//		try {
//			sprintWeb.setDataFinalizacao(new SimpleDateFormat("dd/MM/yyyy").parse("01/06/2016"));
//		} catch (ParseException e) {
//			sprintWeb.setDataFinalizacao(Calendar.getInstance().getTime());
//		}
//
//		tarefaAWeb = new Tarefa();
//		tarefaBWeb = new Tarefa();
//
//		tarefaAWeb.setTitulo("Tarefa A Web");
//		tarefaAWeb.setDescricao("Descrição AWEB");
//		tarefaAWeb.setPontuacao(1);
//		tarefaAWeb.setEstado(Estado.OPEN);
//
//		tarefaBWeb.setTitulo("Tarefa B Web");
//		tarefaBWeb.setDescricao("Descrição BWEB");
//		tarefaBWeb.setPontuacao(2);
//		tarefaBWeb.setEstado(Estado.OPEN);
//
//		tarefaAEmbarcados = new Tarefa();
//		tarefaBEmbarcados = new Tarefa();
//
//		tarefaAEmbarcados.setTitulo("Tarefa A Embarcados");
//		tarefaAEmbarcados.setDescricao("Descrição AEmb");
//		tarefaAEmbarcados.setPontuacao(3);
//		tarefaAEmbarcados.setEstado(Estado.CLOSED);
//
//		tarefaBEmbarcados.setTitulo("Tarefa B Embarcados");
//		tarefaBEmbarcados.setDescricao("Descrição BEmb");
//		tarefaBEmbarcados.setPontuacao(4);
//		tarefaBEmbarcados.setEstado(Estado.CLOSED);
//
//		partA = new Participacao();
//		partA.setPapel(Papel.ADMIN);
//
//		partB = new Participacao();
//		partB.setPapel(Papel.DEVELOPER);
//
//		partC = new Participacao();
//		partC.setPapel(Papel.ADMIN);
//
//		partD = new Participacao();
//		partD.setPapel(Papel.DEVELOPER);
//
//		// Faz as associações
//		// ======================================================
//		tarefaAWeb.setProjeto(web2);
//		tarefaAWeb.setSprint(sprintWeb);
//		tarefaAWeb.setUsuario(inacio);
//
//		tarefaBWeb.setProjeto(web2);
//		tarefaBWeb.setSprint(sprintWeb);
//		tarefaBWeb.setUsuario(itamir);
//
//		tarefaAEmbarcados.setProjeto(embarcados);
//		tarefaAEmbarcados.setSprint(sprintEmbarcados);
//		tarefaAEmbarcados.setUsuario(isaac);
//
//		tarefaBEmbarcados.setProjeto(embarcados);
//		tarefaBEmbarcados.setSprint(sprintEmbarcados);
//		tarefaBEmbarcados.setUsuario(ivan);
//
//		partA.setUsuario(itamir);
//		partA.setProjeto(web2);
//
//		partB.setUsuario(inacio);
//		partB.setProjeto(web2);
//
//		partC.setUsuario(ivan);
//		partC.setProjeto(embarcados);
//
//		partD.setUsuario(isaac);
//		partD.setProjeto(embarcados);
//	}

}
