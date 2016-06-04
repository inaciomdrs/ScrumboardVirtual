package br.ufrn.imd.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class Sprint implements Serializable {

	private static final long serialVersionUID = 8855930560034829376L;

	@Id
	@SequenceGenerator(name = "SEQ_SPRINT", initialValue = 1, allocationSize = 1, sequenceName = "seq_sprint")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SPRINT")
	@Column(name = "id_sprint")
	private long id;

	private String titulo;
	private String descricao;

	@Temporal(TemporalType.DATE)
	private Date dataFinalizacao;

	@ManyToOne
	@JoinColumn(name = "id_projeto")
	private Projeto projeto;

	@OneToMany(mappedBy = "sprint")
	private List<Tarefa> tarefas;

	@Transient
	/**
	 * Atributo análago ao "number" de uma milestone. Colocado aqui para
	 * compatibilização com o GitHub
	 */
	private int numero;

	public Sprint() {
		dataFinalizacao = Calendar.getInstance().getTime();
		tarefas = new ArrayList<Tarefa>();
		numero = new Random(System.currentTimeMillis()).nextInt();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDataFinalizacao() {
		return dataFinalizacao;
	}

	public void setDataFinalizacao(Date dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataFinalizacao == null) ? 0 : dataFinalizacao.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Sprint other = (Sprint) obj;
		if (dataFinalizacao == null) {
			if (other.dataFinalizacao != null)
				return false;
		} else if (!dataFinalizacao.equals(other.dataFinalizacao))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	public List<Tarefa> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
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

	@Override
	public String toString() {
		return "Sprint [id=" + id + ", titulo=" + titulo + ", descricao=" + descricao + ", dataFinalizacao="
				+ dataFinalizacao + ", tarefas=" + tarefas + "]";
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

}
