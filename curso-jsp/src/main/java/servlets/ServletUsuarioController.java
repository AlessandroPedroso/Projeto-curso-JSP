package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOUsuarioRepository;



public class ServletUsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

    public ServletUsuarioController() {
      
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		try {
		
			String acao = request.getParameter("acao");
			
			if(acao !=null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
				
				String idUser = request.getParameter("id");
				
				daoUsuarioRepository.deletarUser(idUser);
				
				List<ModelLogin> listModelLogin = daoUsuarioRepository.consultaUsuarioList();
				request.setAttribute("modelLogins", listModelLogin);
				
				request.setAttribute("msg", "Excluído com sucesso!");
				
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
		
			}else if(acao !=null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {
					
					String idUser = request.getParameter("id");
					
					daoUsuarioRepository.deletarUser(idUser);
					
					response.getWriter().write("Excluído com sucesso!");
			
				}else if(acao !=null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
					
					String nomeBusca = request.getParameter("nomeBusca");
					//System.out.println(nomeBusca);
					
					List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioList(nomeBusca);
					
					ObjectMapper objectMapper = new ObjectMapper();
					String json = objectMapper.writeValueAsString(dadosJsonUser);
					
					response.getWriter().write(json);
					
					//response.getWriter().write("Excluído com sucesso!");
			
				}else if(acao !=null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
					
					String id = request.getParameter("id");
					ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioID(id);
					
					List<ModelLogin> listModelLogin = daoUsuarioRepository.consultaUsuarioList();
					request.setAttribute("modelLogins", listModelLogin);
					
					request.setAttribute("msg", "Usuário em edição");
					request.setAttribute("modelLogin", modelLogin);
				    request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
					
				}else if(acao !=null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {// carrega na página usando JSTL
					
					List<ModelLogin> listModelLogin = daoUsuarioRepository.consultaUsuarioList();
					
					request.setAttribute("msg", "Usuários carregados");
					request.setAttribute("modelLogins", listModelLogin);
					
				    request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
					
					
				}
				else {
					request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
					List<ModelLogin> listModelLogin = daoUsuarioRepository.consultaUsuarioList();
					request.setAttribute("modelLogins", listModelLogin);
				}
			
			
			
			
		}catch (Exception e) {
			
			e.printStackTrace();
			
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
			
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
		
					String msg = "Operação realizada com sucesso!";
					
					String id = request.getParameter("id");
					String nome = request.getParameter("nome");
					String email = request.getParameter("email");
					String login = request.getParameter("login");
					String senha = request.getParameter("senha");
					
					ModelLogin modelLogin = new ModelLogin();
					modelLogin.setId(id !=null && !id.isEmpty() ? Long.parseLong(id) : null);
					modelLogin.setNome(nome);
					modelLogin.setEmail(email);
					modelLogin.setLogin(login);
					modelLogin.setSenha(senha);
					
					if(daoUsuarioRepository.validaLogin(modelLogin.getLogin()) && modelLogin.getId() == null){
						
						msg = "Já existe usuário com o mesmo login, informe outro login!";
						
					}else {
						
						if(modelLogin.isNovo()) {
							
							msg = "Gravado com sucesso!";
							
						}else {
							
							msg = "Atualizado com sucesso!";
						}
						
						
						modelLogin = daoUsuarioRepository.gravarUser(modelLogin);
						
						
					}
					
					List<ModelLogin> listModelLogin = daoUsuarioRepository.consultaUsuarioList();
					request.setAttribute("modelLogins", listModelLogin);
					
					request.setAttribute("msg", msg);
					request.setAttribute("modelLogin", modelLogin);
				    RequestDispatcher redireciona = request.getRequestDispatcher("principal/usuario.jsp");
				    redireciona.forward(request, response);
	    
		}catch (Exception e) {
			
			e.printStackTrace();
			
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}

	}

}
