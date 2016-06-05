package br.ufrn.imd.dao.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
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
		} catch (NoResultException | NonUniqueResultException nex) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> buscaUsuariosComLogin(String login){
		try {
			Query query = getEntityManager().createQuery("select u from Usuario u where login LIKE :login");
			query.setParameter("login", '%' + login + '%');
			return (List<Usuario>) query.getResultList();
		} catch (NoResultException nex) {
			return null;
		}
	}

}
