package br.ufrn.imd.dao.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import br.ufrn.imd.dao.GenericJPADAO;
import br.ufrn.imd.dominio.Sprint;
import br.ufrn.imd.dominio.Tarefa;

@Stateless
public class TarefaJPADAO extends GenericJPADAO<Tarefa, Sprint> {

	/**
	 * Lista as tarefas de uma sprint
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Tarefa> listarComBaseEm(Sprint objetoFiltro) {
		String jpqlQuery = "Select t from Tarefa t join t.sprint s where s.id = :id";

		Query query = getEntityManager().createQuery(jpqlQuery);
		query.setParameter("id", objetoFiltro.getId());

		try {
			return (List<Tarefa>) query.getResultList();	
		} catch(NoResultException nrex){
			return null;
		}
		
	}

	public Tarefa listarPorId(long id) {
		String jpqlQuery = "Select t from Tarefa t where t.id = :id";

		Query query = getEntityManager().createQuery(jpqlQuery);
		query.setParameter("id", id);
		
		try {
			return (Tarefa) query.getSingleResult();	
		} catch(NoResultException | NonUniqueResultException ex){
			return null;
		}

	}

}
