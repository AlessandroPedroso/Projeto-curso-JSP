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
			
		
			String sql = "INSERT INTO model_login(login, senha, nome, email, usuario_id, perfil) VALUES (?, ?, ?, ?, ?, ?)";
	
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, objeto.getLogin());
			insert.setString(2, objeto.getSenha());
			insert.setString(3, objeto.getNome());
			insert.setString(4, objeto.getEmail());
			insert.setLong(5, userLogado);
			insert.setString(6, objeto.getPerfil());
			insert.execute();
			connection.commit();
			
		}else {
			
			String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=?, perfil=? WHERE id = " + objeto.getId() +"";
			PreparedStatement preparaSql = connection.prepareStatement(sql);
			preparaSql.setString(1, objeto.getLogin());
			preparaSql.setString(2, objeto.getSenha());
			preparaSql.setString(3, objeto.getNome());
			preparaSql.setString(4, objeto.getEmail());
			preparaSql.setString(5, objeto.getPerfil());
			preparaSql.executeUpdate();
			connection.commit();
			
		}
			return this.consultaUsuario(objeto.getLogin(), userLogado);
		
	}
	
	public List<ModelLogin> consultaUsuarioList(Long userLogado) throws Exception{
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "SELECT * FROM model_login where useradmin is false and usuario_id = " + userLogado;
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultSet.getString("email"));
			modelLogin.setId(resultSet.getLong("id"));
			modelLogin.setLogin(resultSet.getString("login"));
			modelLogin.setNome(resultSet.getString("nome"));
			//modelLogin.setSenha(resultSet.getString("senha"));
			
			retorno.add(modelLogin);
		}
		
		return retorno;
		
	}
	
	public List<ModelLogin> consultaUsuarioList(String nome, Long userLogado) throws Exception{
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "SELECT * FROM model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ?";
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
