package servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.ModelLogin;
import util.ReportUtil;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;


import com.fasterxml.jackson.databind.ObjectMapper;

import beandto.BeanDtoGraficoSalarioUser;
import dao.DAOUsuarioRepository;


@MultipartConfig //prepara para o upload
public class ServletUsuarioController extends ServletGenericUtil {
	
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
				
				List<ModelLogin> listModelLogin = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", listModelLogin);
				
				request.setAttribute("msg", "Excluído com sucesso!");
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(super.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
		
			}else if(acao !=null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {
					
					String idUser = request.getParameter("id");
					
					daoUsuarioRepository.deletarUser(idUser);
					
					response.getWriter().write("Excluído com sucesso!");
			
				}else if(acao !=null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
					
					String nomeBusca = request.getParameter("nomeBusca");
					//System.out.println(nomeBusca);
					
					List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioList(nomeBusca,super.getUserLogado(request));
					
					ObjectMapper objectMapper = new ObjectMapper();
					String json = objectMapper.writeValueAsString(dadosJsonUser);
					
					response.addHeader("totalPagina", ""+daoUsuarioRepository.consultaUsuarioListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request))); // retorna por fora da lista, por cabeçalho
					response.getWriter().write(json);
					
					//response.getWriter().write("Excluído com sucesso!");
			
				}else if(acao !=null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjaxPage")) {
					
					String nomeBusca = request.getParameter("nomeBusca");
					String paginaOffset = request.getParameter("pagina");
					//System.out.println(nomeBusca);
					
					List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioListOffSet(nomeBusca,super.getUserLogado(request), Integer.parseInt(paginaOffset));
					
					
					ObjectMapper objectMapper = new ObjectMapper();
					String json = objectMapper.writeValueAsString(dadosJsonUser);
					
					response.addHeader("totalPagina", ""+daoUsuarioRepository.consultaUsuarioListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request))); // retorna por fora da lista, por cabeçalho
					response.getWriter().write(json);
					
					//response.getWriter().write("Excluído com sucesso!");
			
				}else if(acao !=null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
					
					String id = request.getParameter("id");
					ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioID(id, super.getUserLogado(request));
					
					List<ModelLogin> listModelLogin = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
					request.setAttribute("modelLogins", listModelLogin);
					
					request.setAttribute("msg", "Usuário em edição");
					request.setAttribute("modelLogin", modelLogin);
					request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(super.getUserLogado(request)));
				    request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
					
				}else if(acao !=null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {// carrega na página usando JSTL
					
					List<ModelLogin> listModelLogin = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
					
					request.setAttribute("msg", "Usuários carregados");
					request.setAttribute("modelLogins", listModelLogin);
					request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(super.getUserLogado(request)));
				    request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
					
					
				}else if(acao !=null && !acao.isEmpty() && acao.equalsIgnoreCase("downloadFoto")) {
					
					String id = request.getParameter("id");
					
					ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioID(id, super.getUserLogado(request));
					
					if(modelLogin.getFotouser() != null && !modelLogin.getFotouser().isEmpty()) {
						
						response.setHeader("Content-Disposition", "attachment;filename=arquivo." + modelLogin.getExtensaofotouser());
						response.getOutputStream().write(new Base64().decodeBase64(modelLogin.getFotouser().split("\\,")[1]));
					}
					
					
				}else if(acao !=null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {
					
					Integer offset = Integer.parseInt(request.getParameter("pagina"));
					
					List<ModelLogin> listModelLogin = daoUsuarioRepository.consultaUsuarioListPaginada(super.getUserLogado(request), offset);
					request.setAttribute("modelLogins", listModelLogin);
					request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(super.getUserLogado(request)));
					request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
					
				}else if(acao !=null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioUser")) {
					
					String dataInicial = request.getParameter("dataInicial");
					String dataFinal = request.getParameter("dataFinal");
					
					if(dataInicial == null || dataInicial.isEmpty() 
							&& dataFinal == null || dataFinal.isEmpty()) {
						
						//List<ModelLogin> listModelLogin = daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request)) ;
						
						request.setAttribute("listaUser", daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request)));
						
						
					}else {
						
						request.setAttribute("listaUser", daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request), dataInicial,dataFinal));
						
					}
					
					request.setAttribute("dataInicial", dataInicial);
					request.setAttribute("dataFinal", dataFinal);
					request.getRequestDispatcher("principal/reluser.jsp").forward(request, response);
					
				}else if(acao !=null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioPdf") 
						|| acao.equalsIgnoreCase("imprimirRelatorioExcel")) {
					
					String dataInicial = request.getParameter("dataInicial");
					String dataFinal = request.getParameter("dataFinal");
					
					List<ModelLogin> ListmodelLogins = null;
					
					if(dataInicial == null || dataInicial.isEmpty() 
							&& dataFinal == null || dataFinal.isEmpty()) {
						
						ListmodelLogins = daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request));
						
					}else {
						
						ListmodelLogins = daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request), dataInicial, dataFinal);

					}
					
					HashMap<String, Object> params = new HashMap<String, Object>();
					params.put("PARAM_SUB_REPORT", request.getServletContext().getRealPath("relatorio") + File.separator);
					
					byte[] relatorio = null;
					String extensao = "";
					
					if(acao.equalsIgnoreCase("imprimirRelatorioPdf")) {
						relatorio = new ReportUtil().geraRelatorioPDF(ListmodelLogins, "rel-user-jsp_7", params, request.getServletContext());
						extensao="pdf";
						
					}else if (acao.equalsIgnoreCase("imprimirRelatorioExcel")) {
						relatorio = new ReportUtil().geraRelatorioExcel(ListmodelLogins, "rel-user-jsp_7", params, request.getServletContext());
						extensao="xls";
					}
					
					response.setHeader("Content-Disposition", "attachment;filename=arquivo." + extensao);
					response.getOutputStream().write(relatorio);
					
					
				}else if(acao !=null && !acao.isEmpty() && acao.equalsIgnoreCase("graficoSalario")) {
					
					String dataInicial = request.getParameter("dataInicial");
					String dataFinal = request.getParameter("dataFinal");
					
					if(dataInicial == null || dataInicial.isEmpty() && dataFinal == null || dataFinal.isEmpty()) {
						
						BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = daoUsuarioRepository.montarGraficoMediaSalario(super.getUserLogado(request));
						ObjectMapper mapper = new ObjectMapper();
						String json = mapper.writeValueAsString(beanDtoGraficoSalarioUser);
						response.getWriter().write(json);
						
					}else {
						
						
						BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = daoUsuarioRepository.montarGraficoMediaSalario(super.getUserLogado(request),dataInicial,dataFinal);
						ObjectMapper mapper = new ObjectMapper();
						String json = mapper.writeValueAsString(beanDtoGraficoSalarioUser);
						response.getWriter().write(json);

					}
					

					
					
				}
				else {
					List<ModelLogin> listModelLogin = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
					request.setAttribute("modelLogins", listModelLogin);
					request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(super.getUserLogado(request)));
					request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			
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
					String perfil = request.getParameter("perfil");
					String sexo = request.getParameter("sexo");
					String cep = request.getParameter("cep");
					String logradouro = request.getParameter("logradouro");
					String bairro = request.getParameter("bairro");
					String localidade = request.getParameter("localidade");
					String uf = request.getParameter("uf");
					String numero = request.getParameter("numero");
					String dataNascimento = request.getParameter("dataNascimento");
					String rendaMensal = request.getParameter("rendamensal");
					rendaMensal = rendaMensal.split("\\ ")[1].replaceAll("\\.", "").replaceAll("\\,", ".");
					
					ModelLogin modelLogin = new ModelLogin();
					modelLogin.setId(id !=null && !id.isEmpty() ? Long.parseLong(id) : null);
					modelLogin.setNome(nome);
					modelLogin.setEmail(email);
					modelLogin.setLogin(login);
					modelLogin.setSenha(senha);
					modelLogin.setPerfil(perfil);
					modelLogin.setSexo(sexo);
					modelLogin.setCep(cep);
					modelLogin.setLogradouro(logradouro);
					modelLogin.setBairro(bairro);
					modelLogin.setLocalidade(localidade);
					modelLogin.setUf(uf);
					modelLogin.setNumero(numero);
					
					//Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataNascimento)));
					
					modelLogin.setDataNascimento(Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataNascimento))));
					modelLogin.setRendamensal(Double.parseDouble(rendaMensal));
					
					if (request.getPart("filefoto") != null) {
						
						Part part = request.getPart("filefoto"); /*pega foto da tela*/
						if (part.getSize() > 0) {
							
							byte[] foto = IOUtils.toByteArray(part.getInputStream()); /*converte imagem para byte*/
							String imagemBase64 = "data:image/" + part.getContentType().split("\\/")[1] + ";base64," + new Base64().encodeBase64String(foto);
							
							modelLogin.setFotouser(imagemBase64);
							modelLogin.setExtensaofotouser(part.getContentType().split("\\/")[1]);
						}

						
					}
					
					if(daoUsuarioRepository.validaLogin(modelLogin.getLogin()) && modelLogin.getId() == null){
						
						msg = "Já existe usuário com o mesmo login, informe outro login!";
						
					}else {
						
						if(modelLogin.isNovo()) {
							
							msg = "Gravado com sucesso!";
							
						}else {
							
							msg = "Atualizado com sucesso!";
						}
						
						
						modelLogin = daoUsuarioRepository.gravarUser(modelLogin,super.getUserLogado(request));
						
					}
					
					List<ModelLogin> listModelLogin = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
					request.setAttribute("modelLogins", listModelLogin);
					
					request.setAttribute("msg", msg);
					request.setAttribute("modelLogin", modelLogin);
					request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(super.getUserLogado(request)));
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
