package br.ufrn.imd.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.ufrn.imd.dominio.Projeto;
import br.ufrn.imd.dominio.Usuario;
import br.ufrn.imd.negocio.UsuarioService;

@ManagedBean
@SessionScoped
public class UsuarioMBean {

	private Usuario usuario;
	private Usuario usuarioLogado;
	
	private List<Usuario> usuariosEncontrados;

	@EJB
	private UsuarioService usuarioService;
			
	public UsuarioMBean() {
		usuario = new Usuario();
		usuariosEncontrados = new ArrayList<Usuario>();
	}

	public String logar() {
		Usuario usuarioNoBanco = usuarioService.logar(usuario);

		if (usuarioNoBanco != null) {
			usuarioLogado = usuarioNoBanco;
			return "/common/seleciona_area.jsf";
		} else {
			FacesMessage msg = new FacesMessage("Erro: Login ou Senha incorretos.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return null;
		}
	}
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/index.jsf";
	}
			
			
	public String selecaoArea(){
		return "/common/seleciona_area.jsf";
	}
	
	public List<Usuario> usuariosDoProjeto(Projeto projeto){
		List<Usuario> usuarios = usuarioService.usuariosDoProjeto(projeto);
		
		return usuarios;
	}
		
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuariosEncontrados() {
		return usuariosEncontrados;
	}

	public void setUsuariosEncontrados(List<Usuario> usuariosEncontrados) {
		this.usuariosEncontrados = usuariosEncontrados;
	}

}
