package dao;

public class teste {

	public static void main(String[] args) {
		
		Double cadastros = 19.0;
		
		Double porpagina = 5.0;
		
		Double pagina = cadastros / porpagina;
		
		Double resto = pagina % 2; //resto da divisão
		
		System.out.println("resto: " + resto);
		
		if(resto > 0) {
			
			pagina ++;
			
		}
		
		System.out.println(pagina.intValue());
		
	}

}
