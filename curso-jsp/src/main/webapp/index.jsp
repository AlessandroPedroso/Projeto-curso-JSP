<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<title>Curso Jsp</title>
<style type="text/css">
*{
    padding: 0;
    margin: 0;
    box-sizing: border-box;
}

html,body{
    height: 100%;
}
form{
width: 100%;
padding-top: 0px;
padding-left: 0%;
}

.msg{
font-size: 15px;
color: #856404;
background-color: #fff3cd;
border-color: #ffeeba
}
.center{
    max-width: 1200px;
    width: 40%;
    margin: 0 auto;
    margin-top:150px;
    padding: 0 2%;
    height: auto;
    background-color: white;
}
</style>
</head>
<body>

<div class=center>
<h5>Bem vindo ao curso de JSP</h5>
<form action="ServletLogin" method="post" class="needs-validation" novalidate>
<input type="hidden" value="<%= request.getParameter("url") %>" name="url" />


    <div class="form-group">
    
			<label>Login</label>
			<input name="login" type="text" class="form-control" placeholder="Login" required="required">

			<div class="invalid-feedback">
	          Por favor, informe seu login.
	        </div>
	        			<div class="valid-feedback">
				        Tudo certo!
				      </div>
	</div>
	<div class="form-group">
			<label>Senha</label>
			<input name="senha" type="password" class="form-control" placeholder="Senha" required="required">

			<div class="invalid-feedback">
	          Por favor, informe sua senha.
	        </div>
	        		<div class="valid-feedback">
			        Tudo certo!
			      </div>
	</div>
		<div class="form-group">
			<input type="submit" value="Acessar" class="btn btn-primary">
			</div>
<h5 class="alert alert-warning" role="alert">${msg}</h5>
</form>

</div>


<!-- JavaScript (Opcional) -->
    <!-- jQuery primeiro, depois Popper.js, depois Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    
<script type="text/javascript">
//Exemplo de JavaScript inicial para desativar envios de formulário, se houver campos inválidos.
(function() {
  'use strict';
  window.addEventListener('load', function() {
    // Pega todos os formulários que nós queremos aplicar estilos de validação Bootstrap personalizados.
    var forms = document.getElementsByClassName('needs-validation');
    // Faz um loop neles e evita o envio
    var validation = Array.prototype.filter.call(forms, function(form) {
      form.addEventListener('submit', function(event) {
        if (form.checkValidity() === false) {
          event.preventDefault();
          event.stopPropagation();
        }
        form.classList.add('was-validated');
      }, false);
    });
  }, false);
})();
</script>    
</body>
</html>
