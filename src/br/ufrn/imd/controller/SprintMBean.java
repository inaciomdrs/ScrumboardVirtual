package br.ufrn.imd.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.ufrn.imd.dominio.Projeto;
import br.ufrn.imd.dominio.Sprint;
import br.ufrn.imd.negocio.SprintService;

@ManagedBean
@SessionScoped
public class SprintMBean {
	
	@EJB
	private SprintService sprintService;
	
	@ManagedProperty(value = "#{tarefaMBean}")
	private TarefaMBean tarefaMBean;
			
	private Sprint sprint;
	
	private Sprint novaSprint;
	
	private List<Sprint> listaSprints;
			
	public SprintMBean() {
		cleanData();
	}

	public Sprint getSprint() {
		return sprint;
	}

	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}

	public List<Sprint> getListaSprints() {
		return listaSprints;
	}

	public void setListaSprints(List<Sprint> listaSprints) {
		this.listaSprints = listaSprints;
	}
	
	public void selecionarSprint(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		selecionarSprint(facesContext);
	}
	
	private void selecionarSprint(FacesContext facesContext) {
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();

		long idTarefa = Long.parseLong(params.get("sprint_id"));

		Sprint sprintSelecionada = sprintService.buscaPorId(idTarefa);

		if (sprintSelecionada != null) {
			setSprint(sprintSelecionada);
		}

	}
	
	public void prepareBoardAreaFrom(Projeto projeto){
		int FIRST_SPRINT = 0;
		this.listaSprints = sprintService.listarSprintsDoProjeto(projeto);
		
		if((this.listaSprints != null) && (this.listaSprints.size() > 0)){
			this.sprint = this.listaSprints.get(FIRST_SPRINT);
		} else {
			cleanData();
		}
	}
	
	public void cleanData(){
		novaSprint = new Sprint();
		sprint = new Sprint();
		listaSprints = new ArrayList<Sprint>();
	}

	public TarefaMBean getTarefaMBean() {
		return tarefaMBean;
	}

	public void setTarefaMBean(TarefaMBean tarefaMBean) {
		this.tarefaMBean = tarefaMBean;
	}

	public Sprint getNovaSprint() {
		return novaSprint;
	}

	public void setNovaSprint(Sprint novaSprint) {
		this.novaSprint = novaSprint;
	}
	
	
}
