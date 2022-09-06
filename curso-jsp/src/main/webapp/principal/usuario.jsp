<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    
<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"></jsp:include>

  <body>
  <!-- Pre-loader start -->
  <jsp:include page="theme-loader.jsp"></jsp:include>
  <!-- Pre-loader end -->
  <div id="pcoded" class="pcoded">
      <div class="pcoded-overlay-box"></div>
      <div class="pcoded-container navbar-wrapper">
      
          <jsp:include page="navbar.jsp"></jsp:include>

          <div class="pcoded-main-container">
              <div class="pcoded-wrapper">

					<jsp:include page="navbarmainmenu.jsp"></jsp:include>
						<div class="pcoded-content">
                      <!-- Page-header start -->
                      <jsp:include page="page-header.jsp"></jsp:include>
                      <!-- Page-header end -->
                        <div class="pcoded-inner-content">
                            <!-- Main-body start -->
                            <div class="main-body">
                                <div class="page-wrapper">
                                    <!-- Page-body start -->
                                    <div class="page-body">

										<div class="row">
										<div class="col-sm-12">
											<!-- Cadastro de usu�rio -->
											<div class="card">
												<div class="card-header">
													<h3>Cadastro de usu�rio</h3>
												</div>
												<div class="card-block">
													<h4 class="sub-title">Formul�rio de cadastro</h4>
														<form class="form-material" action="<%=request.getContextPath() %>/ServletUsuarioController" method="post" id="formUser" autocomplete="off">
                                                            <input type="hidden" name="acao" id="acao" value="">
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="id" id="id" class="form-control" readonly="readonly" value="${modelLogin.id}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">ID:</label>
                                                            </div>
                                                              <div class="form-group form-default">
                                                                <input type="text" name="nome" id="nome" class="form-control" required="required" value="${modelLogin.nome}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Nome:</label>
                                                            </div>
                                                            <div class="form-group form-default">
                                                                <input type="email" name="email" id="email" class="form-control" required="required" autocomplete="off" value="${modelLogin.email}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Email:</label>
                                                            </div>
                                                            
                                                             <div class="form-group form-default">
                                                                <input type="text" name="login" id="login" class="form-control" required="required" autocomplete="off" value="${modelLogin.login}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Login</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default">
                                                                <input type="password" name="senha" id="senha" class="form-control" required="required" autocomplete="off" value="${modelLogin.senha}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Senha</label>
                                                            </div>

															<button type="button" class="btn btn-primary waves-effect waves-light" onclick="limparForm();">Novo</button>
															<button class="btn btn-success waves-effect waves-light">Salvar</button>
												            <button type="button" class="btn btn-info waves-effect waves-light" onclick="criaDeleteComAjax();">Excluir</button>
												            <!-- Button trigger modal -->
															<button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#exampleModalUsuario">Pesquisar</button>
                                                        </form>
														</div>
														</div>
														<span id="msg">${msg}</span>
														</div>
														
														<!-- Page-body end -->
												</div>
												<div id="styleSelector"></div>
											</div>
										</div>
									</div>
                </div>
            </div>
        </div>
    </div>
    </div>
    </div>
    
    
  <jsp:include page="javascriptfile.jsp"></jsp:include>
  
  <!-- Modal -->
<div class="modal fade" id="exampleModalUsuario" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Pesquisa de usu�rio</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <div class="input-group mb-3">
		  <input type="text" class="form-control" placeholder="Nome" aria-label="nome" id="nomeBusca" aria-describedby="basic-addon2">
		  <div class="input-group-append">
		    <button class="btn btn-success" type="button" onclick="buscarUsuario();">Button</button>
		  </div>
		</div>
		<table class="table">
  <thead>
    <tr>
      <th scope="col">ID</th>
      <th scope="col">Nome</th>
      <th scope="col">Ver</th>
    </tr>
  </thead>
  <tbody>
    
  </tbody>
</table>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
        
      </div>
    </div>
  </div>
</div>
  
  <script type="text/javascript">
  
  function buscarUsuario(){
	   var nome = document.getElementById('nomeBusca').value;
	   
	   if(nome !=null && nome !='' && nome.trim() !='') { /*Validando que tem que ter valor para buscar no banco de dados*/
			  var urlAction = document.getElementById('formUser').action;
			  var msg = document.querySelector("#msg");
			  
			  $.ajax({
				  
				  method:"get", //manda a��o para a servlet
				  url: urlAction, //pega a��o do form para saber qual servlet mandar
				  data: "nomeBusca=" + nome + "&acao=buscarUserAjax",
				  success: function (response){
					  
					  //msg.textContent = response;
					  alert(response); //pega o response write da servlet
				  }
				  
			  }).fail(function(xhr, status, errorThrown){
				 alert('Erro ao buscar usu�rio por nome: ' + xhr.responseText); 
			  });
	   }
	  
  }
  
  function criaDeleteComAjax(){
	  
	  if(confirm('Deseja realmente excluir os dados?')){
		  
		  var urlAction = document.getElementById('formUser').action;
		  var idUser = document.getElementById("id").value;
		  var msg = document.querySelector("#msg");
		  
		  $.ajax({
			  
			  method:"get", //manda a��o para a servlet
			  url: urlAction, //pega a��o do form para saber qual servlet mandar
			  data: "id=" + idUser + "&acao=deletarajax",
			  success: function (response){
				  
				  limparForm();
				  msg.textContent = response;
				  //alert(response); //pega o response write da servlet
			  }
			  
		  }).fail(function(xhr, status, errorThrown){
			 alert('Erro ao deletar usu�rio por id: ' + xhr.responseText); 
		  });
	  }
	  
  }
  
  function CriarDelete(){
	  
	  if(confirm('Deseja realmente excluir os dados?')){
		  
		  document.getElementById("formUser").method = 'get';
		  document.getElementById("acao").value = 'deletar';
		  document.getElementById("formUser").submit();	
	  }
	  

  }
  
  function limparForm() {
	  
	  var elementos = document.getElementById("formUser").elements; /*Retorna os elementos html dentro do form*/
	  var msg = document.querySelector("#msg");
	  
	  for(p = 0; p < elementos.length; p++){
		  
		  elementos[p].value = '';
	  }
	  
	  msg.textContent = '';
	
}
  
  
  
  
  </script>
</body>

</html>
    