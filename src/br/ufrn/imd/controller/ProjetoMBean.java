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
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

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

	private Projeto projeto;
	private Projeto projetoSelecionadoBoardArea;
	private Projeto projetoSelecionadoProjectsArea;

	private DataModel<Projeto> listaProjetos;

	private List<Usuario> participantesProjeto;

	public ProjetoMBean() {
		projeto = new Projeto();
		projetoSelecionadoBoardArea = new Projeto();
		projetoSelecionadoProjectsArea = new Projeto();

		listaProjetos = new ListDataModel<Projeto>();

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

		return "/board_area/index.jsf";
	}

	public String selecionarSprint() {
		sprintMBean.selecionarSprint();

		tarefaMBean.prepareBoardAreaFrom(participantesProjeto, sprintMBean.getSprint());

		return "/board_area/index.jsf";
	}
	
	public String cadastrarNovaSprint(){
		sprintMBean.cadastrarNovaSprint(projetoSelecionadoBoardArea);
		
		sprintMBean.prepareBoardAreaFrom(projetoSelecionadoBoardArea);

		return "/board_area/index.jsf";
	}

	private void carregarListagemProjetos() {
		Usuario usuario = usuarioMBean.getUsuarioLogado();

		List<Projeto> projetos = projetoService.listarProjetosDeUsuario(usuario);

		listaProjetos = new ListDataModel<Projeto>(projetos);
	}

	public String fecharTarefa() {
		tarefaMBean.fecharTarefa();
		return "/board_area/index.jsf";
	}

	public String assumirTarefa() {
		tarefaMBean.assumirTarefa();

		tarefaMBean.prepareBoardAreaFrom(participantesProjeto, sprintMBean.getSprint());

		return "/board_area/index.jsf";
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

	public DataModel<Projeto> getListaProjetos() {
		return listaProjetos;
	}

	public void setListaProjetos(DataModel<Projeto> listaProjetos) {
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

}
