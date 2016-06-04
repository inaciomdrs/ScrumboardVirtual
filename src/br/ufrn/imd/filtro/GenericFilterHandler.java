package br.ufrn.imd.filtro;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufrn.imd.controller.UsuarioMBean;

public class GenericFilterHandler {
	
	public static void filterPages(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		UsuarioMBean usuarioMBean = (UsuarioMBean) req.getSession().getAttribute("usuarioMBean");

		if ((usuarioMBean == null) || (usuarioMBean.getUsuarioLogado() == null)) {
			res.sendRedirect("/ScrumboardVirtual/index.jsf");
		} else {
			chain.doFilter(req, res);
		}
	}
}
