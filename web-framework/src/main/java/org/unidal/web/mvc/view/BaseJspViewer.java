package org.unidal.web.mvc.view;

import java.io.EOFException;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.unidal.web.mvc.Action;
import org.unidal.web.mvc.ActionContext;
import org.unidal.web.mvc.Page;
import org.unidal.web.mvc.ViewModel;

public abstract class BaseJspViewer<P extends Page, A extends Action, S extends ActionContext<?>, T extends ViewModel<P, A, S>>
      implements Viewer<P, A, S, T> {
	public void view(S ctx, T model) throws ServletException, IOException {
		HttpServletRequest req = ctx.getHttpServletRequest();
		HttpServletResponse res = ctx.getHttpServletResponse();

		req.setAttribute("ctx", ctx);
		req.setAttribute("payload", ctx.getPayload());
		req.setAttribute("model", model);

		try {
			req.getRequestDispatcher(getJspFilePath(ctx, model)).forward(req, res);
		} catch (EOFException e) {
			// Caused by: java.net.SocketException: Broken pipe
			// ignore it
			System.out.println(String.format("[%s] HTTP request(%s) stopped by client(%s) explicitly!", new Date(),
			      req.getRequestURI(), req.getRemoteAddr()));
		}
	}

	protected abstract String getJspFilePath(S ctx, T model);
}
