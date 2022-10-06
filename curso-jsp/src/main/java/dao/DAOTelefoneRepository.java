package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelTelefone;

public class DAOTelefoneRepository {
	
	private Connection connection;
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
	public DAOTelefoneRepository() {
		
		this.connection = SingleConnectionBanco.getConnection();
		
	}
	
	/*SELECT (consulta)*/
	public List<ModelTelefone> listFone (Long idUserPai) throws Exception{
		
		List<ModelTelefone> listTelefone = new ArrayList<ModelTelefone>();
		
		String sql = "select * from telefone where usuario_pai_id = ?";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, idUserPai);
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()) {
			
			ModelTelefone modelTelefone = new ModelTelefone();
			
			modelTelefone.setId(resultSet.getLong("id"));
			modelTelefone.setNumero(resultSet.getString("numero"));
			modelTelefone.setUsuario_pai_id(daoUsuarioRepository.consultaUsuarioID(resultSet.getLong("usuario_pai_id")));
			modelTelefone.setUsuario_cad_id(daoUsuarioRepository.consultaUsuarioID(resultSet.getLong("usuario_cad_id")));
			
			listTelefone.add(modelTelefone);
		}
		
		return listTelefone;
	}
	
	/*INSERT*/
	public void gravaTelefone (ModelTelefone modelTelefone) throws Exception {
		
		String sql = "insert into telefone (numero,usuario_pai_id,usuario_cad_id) values (?,?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, modelTelefone.getNumero());
		statement.setLong(2, modelTelefone.getUsuario_pai_id().getId());
		statement.setLong(3, modelTelefone.getUsuario_cad_id().getId());
		
		statement.execute();
		connection.commit();
	}
	
	/*DELETE*/
	public void deleteFone (Long id) throws Exception {
		
		String sql = "delete from telefone where id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setLong(1, id);
		statement.executeUpdate();
		connection.commit();
	}
	
	/*VERIFICA SE É DUPLICADO O TELEFONE*/
	public boolean existeFone(String fone, Long idUse) throws Exception {
		
			String sql = "select count(1) > 0 as existe from telefone where usuario_pai_id = ? and numero = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setLong(1, idUse);
			statement.setString(2, fone);
			
			ResultSet resultSet = statement.executeQuery();
			
			resultSet.next();
			
			return resultSet.getBoolean("existe");
	
	}

}
