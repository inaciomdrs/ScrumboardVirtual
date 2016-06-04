package br.ufrn.imd.translation;

import java.util.Random;

/**
 * Conversor que recebe um objeto Git e converte em objeto normal
 * @author inacio-medeiros
 *
 * @param <G> Objeto Git
 * @param <O> Objeto Normal
 */
public interface ITranslator<G,O> {
	
	public static Random randomJava = new Random();
	
	public O convertToObject(G gitObject);
	public G convertToGitObject(O object);

}
