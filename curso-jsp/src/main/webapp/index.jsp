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

<input name="nome" placeholder="Nome">
<input name="idade" placeholder="Idade">

<input type="submit" value="Enviar">

</form>
</body>
</html>