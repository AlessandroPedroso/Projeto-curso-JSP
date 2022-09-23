package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

import java.io.IOException;
import java.util.List;

import dao.DAOUsuarioRepository;


public class ServletTelefone extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;
      
	DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

    public ServletTelefone() {
        super();

    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
				String idUser = request.getParameter("iduser");
				
				if(idUser != null && !idUser.isEmpty()) {
				
		
						
						ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioID(Long.parseLong(idUser));
						
						request.setAttribute("usuario", modelLogin);
						request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
						
			
				}else {
					
					List<ModelLogin> listModelLogin = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
					request.setAttribute("modelLogins", listModelLogin);
					request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(super.getUserLogado(request)));
					request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				}
		
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
