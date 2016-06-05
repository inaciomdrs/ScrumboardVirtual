package br.ufrn.imd.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.ufrn.imd.dominio.Projeto;
import br.ufrn.imd.dominio.Sprint;
import br.ufrn.imd.dominio.Usuario;
import br.ufrn.imd.negocio.ProjetoService;

@ManagedBean
@SessionScoped
public class ProjetoMBean {

	@EJB
	private ProjetoService projetoService;

	@ManagedProperty(value = "#{usuarioMBean}")
	private UsuarioMBean usuarioMBean;

	@ManagedProperty(value = "#{sprintMBean}")
	private SprintMBean sprintMBean;

	@ManagedProperty(value = "#{tarefaMBean}")
	private TarefaMBean tarefaMBean;

	private boolean exibirResultadosBuscaParticipantes;
	
	private Projeto projeto;
	private Projeto projetoSelecionadoBoardArea;
	private Projeto projetoSelecionadoProjectsArea;

	private List<Projeto> listaProjetos;

	private List<Usuario> participantesProjeto;

	private Usuario[] participantesSelecionadosDistribuicao;

	public ProjetoMBean() {
		exibirResultadosBuscaParticipantes = false;
		
		projeto = new Projeto();
		projetoSelecionadoBoardArea = new Projeto();
		projetoSelecionadoProjectsArea = new Projeto();

		listaProjetos = new ArrayList<Projeto>();

		participantesProjeto = new ArrayList<Usuario>();
	}

	public String selecionarProjetoBoardArea() {
		carregarListagemProjetos();
		return "/board_area/seleciona_projeto.jsf";
	}

	public String projectsArea() {
		carregarListagemProjetos();
		return "/projects_area/index.jsf";
	}
	
	public String novoProjeto() {
		projeto = new Projeto();
		return "/projects_area/novo_projeto.jsf";
	}
	
	public String cadastrarProjeto(){
		Usuario coordenador = usuarioMBean.getUsuarioLogado();
						
		projeto = projetoService.cadastrarProjeto(projeto, coordenador);
		
		if(projeto == null){
			projeto = new Projeto();
		}
		
		return projectsArea();
	}
	
	public String visualizaProjeto(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		projetoSelecionadoProjectsArea = selecionarProjeto(facesContext);
		
		participantesProjeto = usuarioMBean.usuariosDoProjeto(projetoSelecionadoProjectsArea);
		
		usuarioMBean.setUsuario(new Usuario());
		
		return "/projects_area/visualiza_projeto.jsf";
	}

	public String boardArea() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		projetoSelecionadoBoardArea = selecionarProjeto(facesContext);

		sprintMBean.prepareBoardAreaFrom(projetoSelecionadoBoardArea);

		participantesProjeto = usuarioMBean.usuariosDoProjeto(projetoSelecionadoBoardArea);

		int FIRST_SPRINT = 0;

		List<Sprint> sprints = sprintMBean.getListaSprints();

		if ((sprints != null) && (sprints.size() > 0)) {
			tarefaMBean.prepareBoardAreaFrom(participantesProjeto, sprints.get(FIRST_SPRINT));
		} else {
			sprintMBean.cleanData();
			tarefaMBean.cleanData();
		}

		return goBoardArea();
	}

	public String selecionarSprint() {
		sprintMBean.selecionarSprint();

		tarefaMBean.prepareBoardAreaFrom(participantesProjeto, sprintMBean.getSprint());

		return goBoardArea();
	}

	public String cadastrarSprint() {
		sprintMBean.cadastrarNovaSprint(projetoSelecionadoBoardArea);

		sprintMBean.prepareBoardAreaFrom(projetoSelecionadoBoardArea);

		return goBoardArea();
	}

	public String cadastrarTarefa() {
		tarefaMBean.cadastrarNovaTarefa(sprintMBean.getSprint());

		tarefaMBean.prepareBoardAreaFrom(participantesProjeto, sprintMBean.getSprint());

		return goBoardArea();
	}

	public String fecharTarefa() {
		tarefaMBean.fecharTarefa();
		return goBoardArea();
	}

	public String assumirTarefa() {
		tarefaMBean.assumirTarefa();

		tarefaMBean.prepareBoardAreaFrom(participantesProjeto, sprintMBean.getSprint());

		return goBoardArea();
	}
	
	public String distribuirTarefas(){
		tarefaMBean.distribuirTarefas(participantesProjeto);
		
		tarefaMBean.prepareBoardAreaFrom(participantesProjeto, sprintMBean.getSprint());
		
		return goBoardArea();
	}

	public String goBoardArea() {
		return "/board_area/index.jsf";
	}

	private void carregarListagemProjetos() {
		Usuario usuario = usuarioMBean.getUsuarioLogado();

		listaProjetos = projetoService.listarProjetosDeUsuario(usuario);
	}

	public Projeto selecionarProjeto(FacesContext facesContext) {
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();

		long idProjeto = Long.parseLong(params.get("projeto_id"));

		Iterator<Projeto> iteradorProjeto = listaProjetos.iterator();

		Projeto projetoSelecionado = null;
		while (iteradorProjeto.hasNext()) {
			projetoSelecionado = iteradorProjeto.next();
			if (projetoSelecionado.getId() == idProjeto) {
				break;
			}
		}

		return projetoSelecionado;
	}

	public Projeto getProjetoSelecionadoBoardArea() {
		return projetoSelecionadoBoardArea;
	}

	public void setProjetoSelecionadoBoardArea(Projeto projetoSelecionadoBoardArea) {
		this.projetoSelecionadoBoardArea = projetoSelecionadoBoardArea;
	}

	public Projeto getProjetoSelecionadoProjectsArea() {
		return projetoSelecionadoProjectsArea;
	}

	public void setProjetoSelecionadoProjectsArea(Projeto projetoSelecionadoProjectsArea) {
		this.projetoSelecionadoProjectsArea = projetoSelecionadoProjectsArea;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public List<Projeto> getListaProjetos() {
		return listaProjetos;
	}

	public void setListaProjetos(List<Projeto> listaProjetos) {
		this.listaProjetos = listaProjetos;
	}

	public UsuarioMBean getUsuarioMBean() {
		return usuarioMBean;
	}

	public void setUsuarioMBean(UsuarioMBean usuarioMBean) {
		this.usuarioMBean = usuarioMBean;
	}

	public SprintMBean getSprintMBean() {
		return sprintMBean;
	}

	public void setSprintMBean(SprintMBean sprintMBean) {
		this.sprintMBean = sprintMBean;
	}

	public List<Usuario> getParticipantesProjeto() {
		return participantesProjeto;
	}

	public void setParticipantesProjeto(List<Usuario> participantesProjeto) {
		this.participantesProjeto = participantesProjeto;
	}

	public TarefaMBean getTarefaMBean() {
		return tarefaMBean;
	}

	public void setTarefaMBean(TarefaMBean tarefaMBean) {
		this.tarefaMBean = tarefaMBean;
	}

	public Usuario[] getParticipantesSelecionadosDistribuicao() {
		return participantesSelecionadosDistribuicao;
	}

	public void setParticipantesSelecionadosDistribuicao(Usuario[] participantesSelecionadosDistribuicao) {
		this.participantesSelecionadosDistribuicao = participantesSelecionadosDistribuicao;
	}

	public boolean isExibirResultadosBuscaParticipantes() {
		return exibirResultadosBuscaParticipantes;
	}

	public void setExibirResultadosBuscaParticipantes(boolean exibirResultadosBuscaParticipantes) {
		this.exibirResultadosBuscaParticipantes = exibirResultadosBuscaParticipantes;
	}

}
