package br.ufrn.imd.dao.jpa;

import java.util.List;

import javax.ejb.Stateless;

import br.ufrn.imd.dao.GenericJPADAO;
import br.ufrn.imd.dominio.Participacao;

@Stateless
public class ParticipacaoJPADAO extends GenericJPADAO<Participacao, Participacao> {

	@Override
	public List<Participacao> listarComBaseEm(Participacao objetoFiltro) {
		return null;
	}

}
