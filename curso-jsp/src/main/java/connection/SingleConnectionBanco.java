package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {
	
	private static String banco = "jdbc:postgresql://localhost:5433/curso-jsp?autoReconnect=true";
	private static String user = "postgres";
	private static String senha = "admin";
	private static Connection connection = null;
	
	static {
		conectar();
	}
	
	public SingleConnectionBanco() { /*quando tiver uma instancia vai conectar*/
		conectar();
	}
	
	private static void conectar() {
		
		try {
			
			if(connection == null) {
				Class.forName("org.postgresql.Driver"); /*carrega o driver de conexão do banco*/
				connection = DriverManager.getConnection(banco, user, senha);
				connection.setAutoCommit(false); /*para não efetuar alteracoes no banco sem nosso comando*/
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();/*mostrar qual quer erro no momento de conectar*/
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}
}
