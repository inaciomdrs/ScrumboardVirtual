package br.ufrn.imd.dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Participacao implements Serializable {


	private static final long serialVersionUID = 2480995003245115685L;

	@Id
	@SequenceGenerator(name = "SEQ_PARTICIPACAO", initialValue = 1, allocationSize = 1, sequenceName = "seq_participacao")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PARTICIPACAO")
	@Column(name = "id_participacao")
	private long id;
	
	@Enumerated(EnumType.STRING)
	private Papel papel;
	
	@ManyToOne
	@JoinColumn(name="id_projeto")
	private Projeto projeto;
	
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;
	
	public Participacao(){
		papel = Papel.DEVELOPER;
		projeto = new Projeto();
		usuario = new Usuario();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Papel getPapel() {
		return papel;
	}

	public void setPapel(Papel papel) {
		this.papel = papel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((papel == null) ? 0 : papel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Participacao other = (Participacao) obj;
		if (id != other.id)
			return false;
		if (papel != other.papel)
			return false;
		return true;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Participacao [id=" + id + ", papel=" + papel + ", projeto=" + projeto + ", usuario=" + usuario + "]";
	}

}
