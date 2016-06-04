package br.ufrn.imd.test.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;

import br.ufrn.imd.dao.jpa.ParticipacaoJPADAO;
import br.ufrn.imd.dao.jpa.ProjetoJPADAO;
import br.ufrn.imd.dao.jpa.SprintJPADAO;
import br.ufrn.imd.dao.jpa.TarefaJPADAO;
import br.ufrn.imd.dao.jpa.UsuarioJPADAO;
import br.ufrn.imd.dominio.Estado;
import br.ufrn.imd.dominio.Papel;
import br.ufrn.imd.dominio.Participacao;
import br.ufrn.imd.dominio.Projeto;
import br.ufrn.imd.dominio.Sprint;
import br.ufrn.imd.dominio.Tarefa;
import br.ufrn.imd.dominio.Usuario;

@ManagedBean
@SessionScoped
public class TestaJPADAOMBean {

	private String returnUrl;

	private Usuario usuario;
	private Tarefa tarefa;
	private Sprint sprint;
	private Projeto projeto;

	private DataModel<Usuario> usuariosModel;
	private DataModel<Projeto> projetosModel;
	private DataModel<Tarefa> tarefasModel;
	private DataModel<Sprint> sprintModel;

	@Inject
	private UsuarioJPADAO usuarioDAO;

	@Inject
	private ProjetoJPADAO projetoDAO;

	@Inject
	private TarefaJPADAO tarefaDAO;

	@Inject
	private SprintJPADAO sprintDAO;

	@Inject
	private ParticipacaoJPADAO participacaoDAO;

	public TestaJPADAOMBean() {
		this.returnUrl = "/test.jsf";
		usuario = new Usuario();
		tarefa = new Tarefa();
		sprint = new Sprint();
		projeto = new Projeto();
	}

	public String testaCadastroUsuario() {
		Usuario usuario = new Usuario();
		usuario.setNome("Inácio Medeiros");
		usuario.setLogin("inaciogm");
		usuario.setSenha("123");

		usuario = usuarioDAO.salvar(usuario);

		return getReturnUrl();
	}

	public String testaBuscaUsuarios() {
		String login = "itamir";

		usuario = usuarioDAO.buscaPorLogin(login);

		return getReturnUrl();
	}

	public String testaListagemUsuarios() {
		projeto.setId(2);

		usuariosModel = new ListDataModel<Usuario>(usuarioDAO.listarComBaseEm(projeto));

		return getReturnUrl();
	}

	public String testaAdicaoColaboradorProjeto() {
		Usuario inacio = new Usuario();
		inacio.setId(5);

		Usuario novoUsuario = usuarioDAO.buscaPorLogin("dbraz");

		List<Projeto> projetos = projetoDAO.listarComBaseEm(inacio);

		Projeto projeto = null;

		for (Projeto p : projetos) {
			if (p.getId() == 4) {
				projeto = p;
				break;
			}
		}

		Participacao participacao = new Participacao();
		participacao.setUsuario(novoUsuario);
		participacao.setProjeto(projeto);
		participacao.setPapel(Papel.DEVELOPER);

		participacao = participacaoDAO.salvar(participacao);

		usuariosModel = new ListDataModel<Usuario>(usuarioDAO.listarComBaseEm(projeto));

		return getReturnUrl();
	}

	public String testaListagemProjetos() {
		Usuario itamir = new Usuario();
		itamir.setId(5);

		projetosModel = new ListDataModel<Projeto>(projetoDAO.listarComBaseEm(itamir));

		return getReturnUrl();
	}

	public String testaCriacaoProjeto() {
		Random randomJava = new Random();
		int randomId = randomJava.nextInt();

		Usuario coordena = usuarioDAO.buscaPorLogin("boyinacio");

		projeto = new Projeto();
		projeto.setId(randomId);
		projeto.setTitulo("MACS2 Pipeline");
		projeto.setDescricao("Implementação de pipeline para MACS2");
		projeto.setCoordenador(coordena);

		projeto = projetoDAO.atualizar(projeto);

		Participacao participacao = new Participacao();
		participacao.setUsuario(coordena);
		participacao.setProjeto(projeto);

		participacao = participacaoDAO.salvar(participacao);

		return getReturnUrl();
	}

	public String testaListagemSprint() {
		Usuario inacio = new Usuario();
		inacio.setId(5);

		projeto = projetoDAO.listarComBaseEm(inacio).get(0);

		sprintModel = new ListDataModel<Sprint>(sprintDAO.listarComBaseEm(projeto));

		return getReturnUrl();
	}
	
	public String testaCriacaoSprint() {
		Usuario inacio = new Usuario();
		inacio.setId(5);

		projeto = projetoDAO.listarComBaseEm(inacio).get(0);
		
		sprint = new Sprint();
		sprint.setTitulo("Camada de Negócio");
		sprint.setDescricao("Construção da camada de negócio");
		
		try {
			sprint.setDataFinalizacao(new SimpleDateFormat("dd/MM/yyyy").parse("01/06/2016"));
		} catch (ParseException e) {
			sprint.setDataFinalizacao(Calendar.getInstance().getTime());
		}
		
		sprint.setProjeto(projeto);
		
		sprint = sprintDAO.atualizar(sprint);
		
		return getReturnUrl();
	}
	
	public String testaCriacaoTarefa() {
		usuario = new Usuario();
		usuario.setId(5);

		projeto = projetoDAO.listarComBaseEm(usuario).get(0);
		
		sprint = sprintDAO.listarComBaseEm(projeto).get(0);
		
		Random randomJava = new Random();
		int randomId = randomJava.nextInt();

		tarefa = new Tarefa();
		tarefa.setId(randomId);
		tarefa.setTitulo("JSF Task");
		tarefa.setDescricao("Implementar os controllers JSF");
		tarefa.setEstado(Estado.OPEN);
		tarefa.setPontuacao(4);
		tarefa.setSprint(sprint);
		
		tarefa = tarefaDAO.atualizar(tarefa);
		
		return getReturnUrl();
	}
	
	public String testaListagemTarefa() {
		usuario = new Usuario();
		usuario.setId(5);

		projeto = projetoDAO.listarComBaseEm(usuario).get(0);
		
		sprint = sprintDAO.listarComBaseEm(projeto).get(0);
		
		tarefasModel = new ListDataModel<Tarefa>(tarefaDAO.listarComBaseEm(sprint));
		
		return getReturnUrl();
	}
	
	public String testaAtualizacaoTarefa() {
		usuario = new Usuario();
		usuario.setId(5);

		projeto = projetoDAO.listarComBaseEm(usuario).get(0);
		
		sprint = sprintDAO.listarComBaseEm(projeto).get(0);
		
		List<Tarefa> tarefas = tarefaDAO.listarComBaseEm(sprint);
		
		for(Tarefa t : tarefas){
			if(t.getId() == 5){
				tarefa = t;
				break;
			}
		}
		
		Usuario itamir = usuarioDAO.buscaPorLogin("itamir");
		tarefa.setUsuario(itamir);
		tarefa.setEstado(Estado.CLOSED);
		
		tarefa = tarefaDAO.atualizar(tarefa);
		
		return getReturnUrl();
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public Sprint getSprint() {
		return sprint;
	}

	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public DataModel<Usuario> getUsuariosModel() {
		return usuariosModel;
	}

	public void setUsuariosModel(DataModel<Usuario> usuariosModel) {
		this.usuariosModel = usuariosModel;
	}

	public DataModel<Projeto> getProjetosModel() {
		return projetosModel;
	}

	public void setProjetosModel(DataModel<Projeto> projetosModel) {
		this.projetosModel = projetosModel;
	}

	public DataModel<Tarefa> getTarefasModel() {
		return tarefasModel;
	}

	public void setTarefasModel(DataModel<Tarefa> tarefasModel) {
		this.tarefasModel = tarefasModel;
	}

	public DataModel<Sprint> getSprintModel() {
		return sprintModel;
	}

	public void setSprintModel(DataModel<Sprint> sprintModel) {
		this.sprintModel = sprintModel;
	}

	public UsuarioJPADAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioJPADAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public ProjetoJPADAO getProjetoDAO() {
		return projetoDAO;
	}

	public void setProjetoDAO(ProjetoJPADAO projetoDAO) {
		this.projetoDAO = projetoDAO;
	}

	public TarefaJPADAO getTarefaDAO() {
		return tarefaDAO;
	}

	public void setTarefaDAO(TarefaJPADAO tarefaDAO) {
		this.tarefaDAO = tarefaDAO;
	}

	public SprintJPADAO getSprintDAO() {
		return sprintDAO;
	}

	public void setSprintDAO(SprintJPADAO sprintDAO) {
		this.sprintDAO = sprintDAO;
	}

}
