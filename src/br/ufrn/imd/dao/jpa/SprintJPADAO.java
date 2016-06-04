package br.ufrn.imd.dao.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import br.ufrn.imd.dao.GenericJPADAO;
import br.ufrn.imd.dominio.Projeto;
import br.ufrn.imd.dominio.Sprint;

@Stateless
public class SprintJPADAO extends GenericJPADAO<Sprint, Projeto> {

	/**
	 * Lista as sprints de um projeto
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Sprint> listarComBaseEm(Projeto objetoFiltro) {
		String jpqlQuery = "Select s from Sprint s join s.projeto p where p.id = :id";

		Query query = getEntityManager().createQuery(jpqlQuery);
		query.setParameter("id", objetoFiltro.getId());
		return (List<Sprint>) query.getResultList();
	}
	
	public Sprint listarPorId(long id) {
		String jpqlQuery = "Select s from Sprint s where s.id = :id";

		Query query = getEntityManager().createQuery(jpqlQuery);
		query.setParameter("id", id);
		
		try {
			return (Sprint) query.getSingleResult();	
		} catch(NoResultException | NonUniqueResultException ex){
			return null;
		}

	}

}
