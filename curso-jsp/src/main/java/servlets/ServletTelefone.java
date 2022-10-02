package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;
import model.ModelTelefone;

import java.io.IOException;
import java.util.List;

import dao.DAOTelefoneRepository;
import dao.DAOUsuarioRepository;


public class ServletTelefone extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;
      
	DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	DAOTelefoneRepository daoTelefoneRepository = new DAOTelefoneRepository();
	

    public ServletTelefone() {
        super();

    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
				String acao = request.getParameter("acao");
				
				if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("excluir")) {
					
					String idfone = request.getParameter("id");
					
					daoTelefoneRepository.deleteFone(Long.parseLong(idfone));
					
					String userpai = request.getParameter("userpai");
					
					ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioID(Long.parseLong(userpai));
					
					List<ModelTelefone> modelTelefones = daoTelefoneRepository.listFone(modelLogin.getId());
					
					request.setAttribute("modelTelefones", modelTelefones);
					request.setAttribute("modelLogin", modelLogin);
					request.setAttribute("msg", "Telefone excluído!");
					request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
					
					return; // não permiti que o bloco a baixo execute
					
				}
					
				String idUser = request.getParameter("iduser");
				
				if(idUser != null && !idUser.isEmpty()) {
				
						ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioID(Long.parseLong(idUser));
						
						List<ModelTelefone> modelTelefones = daoTelefoneRepository.listFone(modelLogin.getId());
						
						request.setAttribute("modelTelefones", modelTelefones);
						request.setAttribute("modelLogin", modelLogin);
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
		try {
			
				String usuario_pai_id = request.getParameter("id");
				String numero = request.getParameter("numero");
				
				ModelTelefone modelTelefone = new ModelTelefone();
				modelTelefone.setNumero(numero);
				modelTelefone.setUsuario_pai_id(daoUsuarioRepository.consultaUsuarioID(Long.parseLong(usuario_pai_id))); // informa o ID do usuário que vai ser cadastrado o telefone
				modelTelefone.setUsuario_cad_id(super.getUserLogadoObjet(request)); // informa o usuário logado
				
				daoTelefoneRepository.gravaTelefone(modelTelefone);
				
				List<ModelTelefone> modelTelefones = daoTelefoneRepository.listFone(Long.parseLong(usuario_pai_id));
				
				ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioID(Long.parseLong(usuario_pai_id));
				
				request.setAttribute("modelTelefones", modelTelefones);
				request.setAttribute("modelLogin", modelLogin);
				request.setAttribute("msg", "Salvo com sucesso");
				request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
				
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		
	}

}
