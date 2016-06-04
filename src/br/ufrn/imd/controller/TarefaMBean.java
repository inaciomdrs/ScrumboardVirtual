package br.ufrn.imd.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.ufrn.imd.dominio.Estado;
import br.ufrn.imd.dominio.Sprint;
import br.ufrn.imd.dominio.Tarefa;
import br.ufrn.imd.dominio.Usuario;
import br.ufrn.imd.negocio.TarefaService;

@ManagedBean
@SessionScoped
public class TarefaMBean {

	@EJB
	private TarefaService tarefaService;

	private Tarefa tarefa;
	
	private final Estado OPEN_STATE; 
	private final Estado CLOSED_STATE;
	private final Usuario NO_OWNER;
	
	private Map<Usuario, List<Tarefa>> tarefasUsuarios;
	private List<Tarefa> tarefasLivres;
	
	public TarefaMBean() {
		cleanData();
		OPEN_STATE = Estado.OPEN;
		CLOSED_STATE = Estado.CLOSED;
		NO_OWNER = null;
	}
	
	public void cleanData(){
		tarefa = new Tarefa();
		tarefasUsuarios = new HashMap<Usuario, List<Tarefa>>();
		tarefasLivres = new ArrayList<Tarefa>();
	}

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}
	
	public String exibeTarefa(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		selecionarTarefa(facesContext);
		
		return "/board_area/index.jsf";
	}

	public void selecionarTarefa(FacesContext facesContext) {
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();

		long idTarefa = Long.parseLong(params.get("tarefa_id"));

		Tarefa tarefaSelecionada = tarefaService.buscaPorId(idTarefa);

		if (tarefaSelecionada != null) {
			setTarefa(tarefaSelecionada);
		}

	}
	
	public void prepareBoardAreaFrom(List<Usuario> participantesProjeto, Sprint sprint){
		cleanData();
		
		List<Tarefa> sprintTasks = tarefaService.tarefasDaSprint(sprint);

		if((sprintTasks != null) && (sprintTasks.size() > 0)){
			long userId;
			List<Tarefa> tarefasUsuario;
			for (Usuario participante : participantesProjeto) {
				tarefasUsuario = new ArrayList<Tarefa>();
				for (Tarefa tarefa : sprintTasks) {
					if(tarefa.getUsuario() != NO_OWNER){
						userId = tarefa.getUsuario().getId();
						if (userId == participante.getId()) {
							tarefasUsuario.add(tarefa);
						}
					} else if(!tarefasLivres.contains(tarefa)) {
						tarefasLivres.add(tarefa);
					}
				}
				tarefasUsuarios.put(participante, tarefasUsuario);
			}
		}
		
		setTarefa(new Tarefa());
	}
	
	public Estado getOPEN_STATE() {
		return OPEN_STATE;
	}

	public Estado getCLOSED_STATE() {
		return CLOSED_STATE;
	}

	public Usuario getNO_OWNER() {
		return NO_OWNER;
	}
	
	public Map<Usuario, List<Tarefa>> getTarefasUsuarios() {
		return tarefasUsuarios;
	}

	public void setTarefasUsuarios(Map<Usuario, List<Tarefa>> tarefasUsuarios) {
		this.tarefasUsuarios = tarefasUsuarios;
	}

	public List<Tarefa> getTarefasLivres() {
		return tarefasLivres;
	}

	public void setTarefasLivres(List<Tarefa> tarefasLivres) {
		this.tarefasLivres = tarefasLivres;
	}

}
