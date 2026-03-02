<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@page import="game.GameBean.GameState" %>
<%@page import="game.Cell" %>
<%@page import="java.util.List" %>
<jsp:useBean id="gameBean" scope="session" class="game.GameBean" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tic Tac Toe</title>
    <style>
        /* Estilos CSS anteriores */
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            background-color: #f4f4f4;
            color: #333;
        }
        h1 {
            color: #4CAF50;
        }
        h2 {
            color: #007bff;
        }
        table {
            border-collapse: collapse;
            margin: 20px auto;
            border: 5px solid #343a40; 
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        td {
            width: 80px;
            height: 80px;
            text-align: center;
            vertical-align: middle;
            font-size: 3em;
            border: 2px solid #6c757d;
            padding: 0;
            background-color: #fff;
            cursor: pointer;
        }
        td a {
            display: block;
            width: 100%;
            height: 100%;
        }
        td img {
            width: 100%;
            height: 100%;
            display: block;
        }
        td:not(:has(a)) {
            cursor: default;
        }
        form {
            margin-top: 20px;
        }
        input[type="submit"] {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1em;
            transition: background-color 0.3s ease;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <h1>Tic Tac Toe</h1>

    <%-- Muestra el turno actual si el juego no ha terminado --%>
    <c:if test="${gameBean.winner == null}">
        <c:choose>
            <c:when test="${gameBean.currentPlayer == game.GameBean.GamePlayer.HUMAN}">
                <h2>¡Es tu turno (Jugador X)!</h2>
            </c:when>
            <c:when test="${gameBean.currentPlayer == game.GameBean.GamePlayer.COMPUTER}">
                <h2>Turno de la Computadora (Jugador O)...</h2>
            </c:when>
        </c:choose>
    </c:if>

    <%-- Renderiza el tablero del juego --%>
    <table>
        <c:forEach var="lineNum" items="${gameBean.gridLines}">
            <tr>
                <c:forEach var="cell" items="${gameBean.getGridStatus(lineNum)}">
                    <td>
                        <c:choose>
                            <%-- ¡CAMBIO AQUÍ! Comparamos con el nombre del enum --%>
                            <c:when test="${cell.state.name() == 'X'}">
                                <img src="img/state_x.png" alt="X"/>
                            </c:when>
                            <c:when test="${cell.state.name() == 'O'}">
                                <img src="img/state_o.png" alt="O"/>
                            </c:when>
                            <c:otherwise>
                                <%-- Si la celda está vacía, se comporta diferente si hay ganador o no --%>
                                <c:if test="${gameBean.winner == null}">
                                    <%-- Si no hay ganador, la celda vacía es clicable --%>
                                    <a href="${pageContext.request.contextPath}/gameServlet?Line=${cell.line}&Col=${cell.col}">
                                        <img src="img/state_null.png" alt="null"/>
                                    </a>
                                </c:if>
                                <c:if test="${gameBean.winner!= null}">
                                    <%-- Si ya hay un ganador, la celda vacía no es clicable --%>
                                    <img src="img/state_null.png" alt="null"/>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>

    <%-- Muestra el mensaje de ganador o empate si el juego ha terminado --%>
    <c:if test="${gameBean.winner!= null}">
        <c:choose>
            <%-- ¡CAMBIO AQUÍ! Comparamos con el nombre del enum --%>
            <c:when test="${gameBean.winner.name() == 'X'}">
                <h2>¡Felicidades! ¡Ha ganado el Jugador X!</h2>
            </c:when>
            <c:when test="${gameBean.winner.name() == 'O'}">
                <h2>¡Ha ganado la Computadora (Jugador O)!</h2>
            </c:when>
            <c:otherwise> <%-- Si winner es GameState.NULL, significa que es un empate --%>
                <h2>¡Es un empate!</h2>
            </c:otherwise>
        </c:choose>
        <%-- Formulario para jugar de nuevo --%>
        <form action="${pageContext.request.contextPath}/EntryServlet" method="post">
            <input type="submit" name="Replay" value="Jugar de nuevo">
        </form>
    </c:if>

</body>
</html>