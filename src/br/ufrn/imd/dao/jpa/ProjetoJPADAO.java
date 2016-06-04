package br.ufrn.imd.dao.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import br.ufrn.imd.dao.GenericJPADAO;
import br.ufrn.imd.dominio.Projeto;
import br.ufrn.imd.dominio.Usuario;

@Stateless
public class ProjetoJPADAO extends GenericJPADAO<Projeto, Usuario> {

	/**
	 * Lista os projetos de um usuário
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Projeto> listarComBaseEm(Usuario objetoFiltro) {
		String jpqlQuery = "Select proj from Projeto proj join proj.participacoes part join part.usuario u where u.id = :id";
		Query query = getEntityManager().createQuery(jpqlQuery);
		query.setParameter("id", objetoFiltro.getId());
		return (List<Projeto>) query.getResultList();
	}

}
