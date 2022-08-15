<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html lang="pt-br">
<html>
<head>
<meta charset="ISO-8859-1">
<title>Curso Jsp</title>
</head>
<body>
<h1>Bem vindo ao curso de JSP</h1>

<% 
out.print("seu sucesso garantido");

%>

<form action="ServletLogin" method="post">
<table>
<tr>
	<td>Login</td>
	<td><input name="login" type="text"></td>
</tr>

<tr>
	<td><label>Senha</label></td>
	<td><input name="senha" type="password"></td>
</tr>

<tr>
<td></td>
<td><input type="submit" value="Enviar"></td>
</tr>

</table>

</form>
<h4>${msg}</h4>
</body>
</html>