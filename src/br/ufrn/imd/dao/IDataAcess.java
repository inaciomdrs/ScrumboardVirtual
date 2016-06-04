package br.ufrn.imd.dao;

import java.util.List;

/**
 * Interface para implementação de persistência e recuperação de dados
 * @author inacio-medeiros
 *
 * @param <T> Tipo de objeto base para as operações
 * @param <F> Objeto de filtro para a operação de listagem
 */
public interface IDataAcess<T,F> {

	public T salvar(T object);
	
	public List<T> listarComBaseEm(F objetoFiltro);
	
}
