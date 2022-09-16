package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {
	
	private Connection connection; 
	
	public DAOUsuarioRepository() {
		
		connection = SingleConnectionBanco.getConnection();
	}
	
	/*faz o inser no banco da página de cadastro usuario.jsp*/
	public ModelLogin gravarUser(ModelLogin objeto, Long userLogado) throws Exception {
		
		if(objeto.isNovo()) {/*Grava um novo*/
			
		
			String sql = "INSERT INTO model_login(login, senha, nome, email, usuario_id, perfil, sexo, cep, logradouro, bairro, localidade, uf, numero) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, objeto.getLogin());
			insert.setString(2, objeto.getSenha());
			insert.setString(3, objeto.getNome());
			insert.setString(4, objeto.getEmail());
			insert.setLong(5, userLogado);
			insert.setString(6, objeto.getPerfil());
			insert.setString(7, objeto.getSexo());
			
			insert.setString(8, objeto.getCep());
			insert.setString(9, objeto.getLogradouro());	
			insert.setString(10, objeto.getBairro());	
			insert.setString(11, objeto.getLocalidade());	
			insert.setString(12, objeto.getUf());
			insert.setString(13, objeto.getNumero());
			insert.execute();
			connection.commit();
			
			if(objeto.getFotouser() !=null && !objeto.getFotouser().isEmpty()) {
				
				sql = "update model_login set fotouser=?, extensaofotouser=? where login = ?";
				
				insert.setString(1, objeto.getFotouser());
				insert.setString(2, objeto.getExtensaofotouser());
				insert.setString(3, objeto.getLogin());
				
				insert.execute();
				connection.commit();
			}
			
			
		}else {
			
			String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=?, perfil=?, sexo=?, cep=?, logradouro=?, bairro=?, localidade=?, uf=?, numero=? WHERE id = " + objeto.getId() +"";
			PreparedStatement preparaSql = connection.prepareStatement(sql);
			preparaSql.setString(1, objeto.getLogin());
			preparaSql.setString(2, objeto.getSenha());
			preparaSql.setString(3, objeto.getNome());
			preparaSql.setString(4, objeto.getEmail());
			preparaSql.setString(5, objeto.getPerfil());
			preparaSql.setString(6, objeto.getSexo());
			
			preparaSql.setString(7, objeto.getCep());
			preparaSql.setString(8, objeto.getLogradouro());	
			preparaSql.setString(9, objeto.getBairro());	
			preparaSql.setString(10, objeto.getLocalidade());	
			preparaSql.setString(11, objeto.getUf());
			preparaSql.setString(12, objeto.getNumero());
			preparaSql.executeUpdate();
			connection.commit();
			
			
			if(objeto.getFotouser() !=null && !objeto.getFotouser().isEmpty()) {
				
				sql = "update model_login set fotouser=?, extensaofotouser=? where id = ?";
				PreparedStatement statementArquivo = connection.prepareStatement(sql);
				statementArquivo.setString(1, objeto.getFotouser());
				statementArquivo.setString(2, objeto.getExtensaofotouser());
				statementArquivo.setLong(3, objeto.getId());
				
				statementArquivo.execute();
				connection.commit();
			}
			
		}
			return this.consultaUsuario(objeto.getLogin(), userLogado);
		
	}
	
	public int totalPagina(Long userLogado) throws Exception {
		
		String sql = "select count(1) as total from model_login where usuario_id = " + userLogado;
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultSet = statement.executeQuery();
		
		Double cadastros = resultSet.getDouble("total");
		
		Double porpagina = 5.0;
		
		Double pagina = cadastros / porpagina;
		
		Double resto = pagina % 2; //resto da divisão
		
		System.out.println("resto: " + resto);
		
		if(resto > 0) {
			
			pagina ++;
			
		}
		
		return pagina.intValue();
		
	}
	
	public List<ModelLogin> consultaUsuarioListPaginada(Long userLogado, Integer offset) throws Exception{
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "SELECT * FROM model_login where useradmin is false and usuario_id = " + userLogado + "order by nome offset "+offset+" limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultSet.getString("email"));
			modelLogin.setId(resultSet.getLong("id"));
			modelLogin.setLogin(resultSet.getString("login"));
			modelLogin.setNome(resultSet.getString("nome"));
			modelLogin.setPerfil(resultSet.getString("perfil"));
			modelLogin.setSexo(resultSet.getString("sexo"));
			//modelLogin.setSenha(resultSet.getString("senha"));
			
			retorno.add(modelLogin);
		}
		
		return retorno;
		
	}
	
	public List<ModelLogin> consultaUsuarioList(Long userLogado) throws Exception{
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "SELECT * FROM model_login where useradmin is false and usuario_id = " + userLogado + "limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultSet.getString("email"));
			modelLogin.setId(resultSet.getLong("id"));
			modelLogin.setLogin(resultSet.getString("login"));
			modelLogin.setNome(resultSet.getString("nome"));
			modelLogin.setPerfil(resultSet.getString("perfil"));
			modelLogin.setSexo(resultSet.getString("sexo"));
			//modelLogin.setSenha(resultSet.getString("senha"));
			
			retorno.add(modelLogin);
		}
		
		return retorno;
		
	}
	
	public List<ModelLogin> consultaUsuarioList(String nome, Long userLogado) throws Exception{
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "SELECT * FROM model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ? limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);
		
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultSet.getString("email"));
			modelLogin.setId(resultSet.getLong("id"));
			modelLogin.setLogin(resultSet.getString("login"));
			modelLogin.setNome(resultSet.getString("nome"));
			modelLogin.setPerfil(resultSet.getString("perfil"));
			modelLogin.setSexo(resultSet.getString("sexo"));
			//modelLogin.setSenha(resultSet.getString("senha"));
			
			retorno.add(modelLogin);
		}
		
		
		return retorno;
		
	}
	
	public ModelLogin consultaUsuario(String login, Long userLogado) throws Exception {
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where upper(login) = upper('"+ login +"') and useradmin is false and usuario_id = " + userLogado;
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultSet = statement.executeQuery();
		
		while (resultSet.next()) /*se tem resultado*/ {
			
			modelLogin.setId(resultSet.getLong("id"));
			modelLogin.setEmail(resultSet.getString("email"));
			modelLogin.setLogin(resultSet.getString("login"));
			modelLogin.setSenha(resultSet.getString("senha"));
			modelLogin.setNome(resultSet.getString("nome"));
			modelLogin.setPerfil(resultSet.getString("perfil"));
			modelLogin.setSexo(resultSet.getString("sexo"));
			modelLogin.setFotouser(resultSet.getString("fotouser"));
			
			modelLogin.setCep(resultSet.getString("cep"));
			modelLogin.setLogradouro(resultSet.getString("logradouro"));
			modelLogin.setBairro(resultSet.getString("bairro"));
			modelLogin.setLocalidade(resultSet.getString("localidade"));
			modelLogin.setUf(resultSet.getString("uf"));
			modelLogin.setNumero(resultSet.getString("numero"));
		}
		
		return modelLogin;
		
	}
	
	public ModelLogin consultaUsuarioLogado(String login) throws Exception {
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where upper(login) = upper('"+ login +"')";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultSet = statement.executeQuery();
		
		while (resultSet.next()) /*se tem resultado*/ {
			
			modelLogin.setId(resultSet.getLong("id"));
			modelLogin.setEmail(resultSet.getString("email"));
			modelLogin.setLogin(resultSet.getString("login"));
			modelLogin.setSenha(resultSet.getString("senha"));
			modelLogin.setNome(resultSet.getString("nome"));
			modelLogin.setUseradmin(resultSet.getBoolean("useradmin"));
			modelLogin.setPerfil(resultSet.getString("perfil"));
			modelLogin.setSexo(resultSet.getString("sexo"));
			modelLogin.setFotouser(resultSet.getString("fotouser"));
			
			modelLogin.setCep(resultSet.getString("cep"));
			modelLogin.setLogradouro(resultSet.getString("logradouro"));
			modelLogin.setBairro(resultSet.getString("bairro"));
			modelLogin.setLocalidade(resultSet.getString("localidade"));
			modelLogin.setUf(resultSet.getString("uf"));
			modelLogin.setNumero(resultSet.getString("numero"));
		}
		
		return modelLogin;
		
	}
	
	public ModelLogin consultaUsuario(String login) throws Exception {
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where upper(login) = upper('"+ login +"') and useradmin is false";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultSet = statement.executeQuery();
		
		while (resultSet.next()) /*se tem resultado*/ {
			
			modelLogin.setId(resultSet.getLong("id"));
			modelLogin.setEmail(resultSet.getString("email"));
			modelLogin.setLogin(resultSet.getString("login"));
			modelLogin.setSenha(resultSet.getString("senha"));
			modelLogin.setNome(resultSet.getString("nome"));
			modelLogin.setUseradmin(resultSet.getBoolean("useradmin"));
			modelLogin.setPerfil(resultSet.getString("perfil"));
			modelLogin.setSexo(resultSet.getString("sexo"));
			modelLogin.setFotouser(resultSet.getString("fotouser"));
			
			modelLogin.setCep(resultSet.getString("cep"));
			modelLogin.setLogradouro(resultSet.getString("logradouro"));
			modelLogin.setBairro(resultSet.getString("bairro"));
			modelLogin.setLocalidade(resultSet.getString("localidade"));
			modelLogin.setUf(resultSet.getString("uf"));
			modelLogin.setNumero(resultSet.getString("numero"));
		}
		
		return modelLogin;
		
	}
	
		public ModelLogin consultaUsuarioID(String id, Long userLogado) throws Exception {
				
				ModelLogin modelLogin = new ModelLogin();
				
				String sql = "select * from model_login where id = ? and useradmin is false and usuario_id = ?";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setLong(1, Long.parseLong(id));
				statement.setLong(2, userLogado);
				
				ResultSet resultSet = statement.executeQuery();
				
				while (resultSet.next()) /*se tem resultado*/ {
					
					modelLogin.setId(resultSet.getLong("id"));
					modelLogin.setEmail(resultSet.getString("email"));
					modelLogin.setLogin(resultSet.getString("login"));
					modelLogin.setSenha(resultSet.getString("senha"));
					modelLogin.setNome(resultSet.getString("nome"));
					modelLogin.setPerfil(resultSet.getString("perfil"));
					modelLogin.setSexo(resultSet.getString("sexo"));
					modelLogin.setFotouser(resultSet.getString("fotouser"));
					modelLogin.setExtensaofotouser(resultSet.getString("extensaofotouser"));
					
					modelLogin.setCep(resultSet.getString("cep"));
					modelLogin.setLogradouro(resultSet.getString("logradouro"));
					modelLogin.setBairro(resultSet.getString("bairro"));
					modelLogin.setLocalidade(resultSet.getString("localidade"));
					modelLogin.setUf(resultSet.getString("uf"));
					modelLogin.setNumero(resultSet.getString("numero"));
				}
				
				return modelLogin;
				
			}
	
	public boolean validaLogin(String login) throws Exception {
		
		String sql = "select count(1) > 0 as existe from model_login where upper(login) = upper('"+login+"')";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultSet = statement.executeQuery();
		
		/*if(resultSet.next()) {
			
			return resultSet.getBoolean("existe");
		}
		*/
		
		resultSet.next()/*Pra ele entrar nos resultados do sql e virificar*/;
		return resultSet.getBoolean("existe");
		
	}
	
	public void deletarUser(String idUser) throws Exception {
		
		String sql = "DELETE FROM model_login WHERE id = ? and useradmin is false";
		PreparedStatement deletar = connection.prepareStatement(sql);
		deletar.setLong(1, Long.parseLong(idUser));
		
		deletar.executeUpdate();
		connection.commit();
		
	}

}
