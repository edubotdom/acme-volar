<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="airports">
    <h2>Airports</h2>

    <table id="airportsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Max Number Of Planes</th>
            <th>Max Number Of Clients</th>
            <th>Latitude</th>
            <th>Longitude</th>
            <th>Code</th>
            <th>City</th>
            <th>Delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${airports}" var="airport">
            <tr>
                <td>
                	<spring:url value="/airports/{airportId}" var="airportUrl">
                	    <spring:param name="airportId" value="${airport.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(airportUrl)}"><c:out value="${airport.name}"/></a>
                </td>               
                <td>
                    <c:out value="${airport.maxNumberOfPlanes}"/>
                </td>
                <td>
                    <c:out value="${airport.maxNumberOfClients}"/>
                </td>
                <td>
                    <c:out value="${airport.latitude}"/>
                </td>
                <td>
                    <c:out value="${airport.longitude}"/>
                </td>
                <td>
                    <c:out value="${airport.code}"/>
                </td>
                <td>
                    <c:out value="${airport.city}"/>
                </td>
                <td>
                    <spring:url value="/airports/{airportId}/delete" var="airportDeleteUrl">
                	    <spring:param name="airportId" value="${airport.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(airportDeleteUrl)}"><c:out value="Delete"/></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <button class="btn btn-default" onclick="window.location.href='/airports/new'">New</button>

</petclinic:layout>
