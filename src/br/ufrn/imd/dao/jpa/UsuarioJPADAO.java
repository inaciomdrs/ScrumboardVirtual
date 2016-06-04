package br.ufrn.imd.dao.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.ufrn.imd.dao.GenericJPADAO;
import br.ufrn.imd.dominio.Projeto;
import br.ufrn.imd.dominio.Usuario;

@Stateless
public class UsuarioJPADAO extends GenericJPADAO<Usuario, Projeto> {

	/**
	 * Lista os usuários de um projeto
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> listarComBaseEm(Projeto objetoFiltro) {
		String jpqlQuery = "Select u from Usuario u join u.participacoes part join part.projeto proj where proj.id = :id";
		Query query = getEntityManager().createQuery(jpqlQuery);
		query.setParameter("id", objetoFiltro.getId());
		return (List<Usuario>) query.getResultList();
	}

	public Usuario buscaPorLogin(String login) {
		try {
			Query query = getEntityManager().createQuery("select u from Usuario u where login = :login");
			query.setParameter("login", login);
			return (Usuario) query.getSingleResult();
		} catch (NoResultException nex) {
			return null;
		}
	}

}
