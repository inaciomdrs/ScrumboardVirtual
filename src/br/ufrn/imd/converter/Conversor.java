package br.ufrn.imd.converter;

import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.ufrn.imd.dominio.Usuario;

@FacesConverter("usuarioConverter")
public class Conversor implements Converter {

	private static HashMap<String, Usuario> usuario;
	
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null) {
			return usuario.get(value);
		}
		return null;
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {

		if (object != null && !"".equals(object)) {
			Usuario entity = (Usuario) object;
			if (entity.getId() > 0) {
				addUsuario(entity);
				return String.valueOf(entity.getId());
			}
		}
		return "";
	}

	private void addUsuario(Usuario user) {
		if(usuario == null) 
			usuario = new HashMap<>();
		if(!usuario.containsKey(String.valueOf(user.getId())))
			usuario.put(String.valueOf(user.getId()), user);
	}

}
