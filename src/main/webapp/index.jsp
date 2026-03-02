<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<jsp:useBean id="gameBean" scope="session" class="game.GameBean" />

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Tic Tac Toe</title>
    </head>
    <body>
        <h1>Tic Tac Toe</h1>

        <form action="EntryServlet" method="post">
    <label>¿Quién inicia?</label>
    <select name="player">
        <option value="X">Usuario (X)</option>
        <option value="O">Computador (O)</option>
    </select>
    <button type="submit">Jugar</button>
</form>

    </body>
</html>