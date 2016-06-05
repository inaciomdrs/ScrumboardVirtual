package br.ufrn.imd.dao.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.ufrn.imd.dao.GenericJPADAO;
import br.ufrn.imd.dominio.Participacao;

@Stateless
public class ParticipacaoJPADAO extends GenericJPADAO<Participacao, Participacao> {

	@Override
	public List<Participacao> listarComBaseEm(Participacao objetoFiltro) {
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Participacao> buscaParticipacao(Participacao participacao) {
		String jpqlQuery = "Select part from Participacao part " + "join part.projeto proj " + "join part.usuario user "
				+ "where proj.id = :idproj and user.id = :iduser";

		List<Participacao> results;
		
		try {
			Query query = getEntityManager().createQuery(jpqlQuery);
			query.setParameter("idproj", participacao.getProjeto().getId());
			query.setParameter("iduser", participacao.getUsuario().getId());
			
			results = (List<Participacao>) query.getResultList();
			
			if(results.size() == 0){
				return null;
			} else {
				return results;	
			}
		} catch (NoResultException nex) {
			return null;
		}

	}

}
