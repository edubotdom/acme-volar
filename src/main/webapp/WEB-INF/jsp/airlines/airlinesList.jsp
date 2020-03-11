<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="airlines">
    <h2>Airlines</h2>

    <table id="airlinesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
            <th style="width: 200px;">Identification</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${airlines}" var="airline">
            <tr>
                <td>
                    <spring:url value="/airlines/{airlineId}" var="airlineUrl">
                        <spring:param name="airlineId" value="${airline.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(airlineUrl)}"><c:out value="${airline.name}"/></a>
                </td>
                <td>
                    <c:out value="${airline.identification}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
