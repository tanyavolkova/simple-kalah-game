<%--@elvariable id="boardView" type="com.levi9.tvolkova.domain.BoardView"--%>
<%--@elvariable id="pit" type="com.levi9.tvolkova.domain.Pit"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <link rel="stylesheet" type="text/css" href="../../resources/css/main.css">
  <title>Kalah Game</title>
</head>
<body>
<c:if test="${boardView.gameOver}">
  <h2>Game over. ${boardView.resultMessage}</h2>
</c:if>

<label> <-- ${boardView.opponentName}</label>
<table>
  <tr>
    <td>${boardView.opponentKalah}</td>
    <td>${boardView.opponentsPits[5]}</td>
    <td>${boardView.opponentsPits[4]}</td>
    <td>${boardView.opponentsPits[3]}</td>
    <td>${boardView.opponentsPits[2]}</td>
    <td>${boardView.opponentsPits[1]}</td>
    <td>${boardView.opponentsPits[0]}</td>
    <%--<c:forEach items="${boardView.opponentsPits}" var="opponentPit" begin="5" end="0" step="1">
      <td>${opponentPit}</td>
    </c:forEach>--%>
    <td></td>
  </tr>
  <tr>
    <td></td>
    <c:forEach items="${boardView.pits}" var="pit">
      <c:choose>
        <c:when test="${boardView.yourTurn}">
          <td><a href="/${pit.id}">${pit.stones}</a></td>
        </c:when>
        <c:otherwise>
          <td>${pit.stones}</td>
        </c:otherwise>
      </c:choose>
    </c:forEach>
    <td>${boardView.kalah}</td>
  </tr>
</table>
<label> --> ${boardView.playerName}</label>
<c:if test="${not boardView.gameOver}">
  <c:choose>
    <c:when test="${boardView.yourTurn}">
      <h5>It's your turn now</h5>
    </c:when>
    <c:otherwise>
      <h5>It's your opponent turn now</h5>
    </c:otherwise>
  </c:choose>
</c:if>

<p>
  <a href="/state">Get State</a> (simulating client polling)
</p>
</body>
</html>
