package br.ufrn.imd.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Projeto implements Serializable {

	private static final long serialVersionUID = 2290027503454162006L;

	@Id
	@SequenceGenerator(name = "SEQ_PROJETO", initialValue = 1, allocationSize = 1, sequenceName = "seq_projeto")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PROJETO")
	@Column(name = "id_projeto")
	private long id;

	private String titulo;
	private String descricao;

	@OneToMany(mappedBy = "projeto")
	private List<Sprint> sprints;

	@OneToMany(mappedBy = "projeto")
	private List<Participacao> participacoes;

	@OneToOne
	@JoinColumn(name = "id_usuario")
	private Usuario coordenador;

	public Projeto() {
		sprints = new ArrayList<Sprint>();
		participacoes = new ArrayList<Participacao>();
		coordenador = new Usuario();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Projeto other = (Projeto) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public List<Participacao> getParticipacoes() {
		return participacoes;
	}

	public void setParticipacoes(List<Participacao> participacoes) {
		this.participacoes = participacoes;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Usuario getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(Usuario coordenador) {
		this.coordenador = coordenador;
	}

	public List<Sprint> getSprints() {
		return sprints;
	}

	public void setSprints(List<Sprint> sprints) {
		this.sprints = sprints;
	}

	@Override
	public String toString() {
		return "Projeto [id=" + id + ", titulo=" + titulo + ", descricao=" + descricao + ", sprints=" + sprints
				+ ", participacoes=" + participacoes + ", coordenador=" + coordenador + "]";
	}

}
