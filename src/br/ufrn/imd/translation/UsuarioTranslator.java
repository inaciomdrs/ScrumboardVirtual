package br.ufrn.imd.translation;

import org.eclipse.egit.github.core.User;

import br.ufrn.imd.dominio.Usuario;

public class UsuarioTranslator implements ITranslator<User, Usuario> {

	@Override
	public Usuario convertToObject(User gitObject) {
		Usuario usuario = new Usuario();
		usuario.setId(gitObject.getId());
		usuario.setLogin(gitObject.getLogin());
		usuario.setNome(gitObject.getName());

		return usuario;
	}

	@Override
	public User convertToGitObject(Usuario object) {
		User user = new User();
		user.setLogin(object.getLogin());
		user.setName(object.getNome());
		return user;
	}

}
