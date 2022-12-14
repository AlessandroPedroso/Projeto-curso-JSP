package filter;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import connection.SingleConnectionBanco;
import dao.DaoVersionadorBanco;


@WebFilter(urlPatterns = {"/principal/*"})/*Interceptas todas as requisições que vierem do projeto ou mapeamento*/
public class FilterAutenticacao extends HttpFilter implements Filter{
       
	private static final long serialVersionUID = 1L;
	
	private static Connection connection;


	public FilterAutenticacao() {
        super();
    }
    
    /*Encerra os processos quando o servidor é parado*/
    /*Mataria os processos de conexão com o banco*/
	public void destroy() {
		
		try {
			
			connection.close();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	/*Intercepta as requisições e as repostas no sistema*/
	/*tudo o que fizer no sistema vai fazer por aqui*/
	/*validação de autenticação*/
	/*Dar commit e rolback de transações do banco*/
	/*validar e fazer redirecionamento de paginas*/
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		
		try {
			
				HttpServletRequest req = (HttpServletRequest) request;
				HttpSession session = req.getSession();
				
				String usuarioLogado = (String) session.getAttribute("usuario");
				
				String urlParaAutenticar = req.getServletPath();/*Url que está sendo processada*/
				
				/*Validar se está logado, senão redireciona para tela de login*/
				if(usuarioLogado == null && !urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) { /*não está logado*/
					
					RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
					request.setAttribute("msg", "Por favor realize o login!");
					redireciona.forward(request, response);
					return;/*para a execução e redireciona para o login*/
					
				}else {
					chain.doFilter(request, response);
				}
				
				connection.commit();/*deu tudo certo, então comita as alterações no banco de dados*/
		} catch (Exception e) {
			
			e.printStackTrace();
			
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
			
			try {
				connection.rollback();
				
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			
		}
		
	}

	/*Inicia os processos ou recursos quando o servidor sobe o projeto*/
	//iniciar a conexão com o banco
	public void init(FilterConfig fConfig) throws ServletException {
			
		connection = SingleConnectionBanco.getConnection();
		
		DaoVersionadorBanco daoVersionadorBanco = new DaoVersionadorBanco();
		
		String caminhoPastaSQL = fConfig.getServletContext().getRealPath("versionadorbancosql") + File.separator;
		
		File[] filesSql = new File(caminhoPastaSQL).listFiles();
		
		try {
			for (File file : filesSql) {
				
				boolean arquivoJaRodado = daoVersionadorBanco.arquivoSqlRodado(file.getName());
				
				if(!arquivoJaRodado) {
					
					FileInputStream entradaArquivo = new FileInputStream(file);
					java.util.Scanner lerArquivo = new java.util.Scanner(entradaArquivo, "UTF-8");
					
					StringBuilder sql = new StringBuilder();
					
					while(lerArquivo.hasNext()) {
						
						sql.append(lerArquivo.nextLine());
						sql.append("\n");
					}
					
					connection.prepareStatement(sql.toString()).execute();
					daoVersionadorBanco.gravaArquivoSqlRodado(file.getName());
					connection.commit();
					lerArquivo.close();
				}
			}
		}
		catch (Exception e) {
			try {
				connection.rollback();
				
			} catch (SQLException e1) {
			
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}


}

