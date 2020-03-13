<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="airports">

    <h2>Airport Information</h2>


    <table class="table table-striped">
        <tr>
            <th>Name</th>
            <td><b><c:out value="${airport.name}"/></b></td>
        </tr>
        <tr>
            <th>Max Number Of Planes</th>
            <td><c:out value="${airport.maxNumberOfPlanes}"/></td>
        </tr>
        <tr>
            <th>Max Number Of Clients</th>
            <td><c:out value="${airport.maxNumberOfClients}"/></td>
        </tr>
        <tr>
            <th>Latitude</th>
            <td><c:out value="${airport.latitude}"/></td>
        </tr>
         <tr>
            <th>Longitude</th>
            <td><c:out value="${airport.longitude}"/></td>
        </tr>
         <tr>
            <th>Code</th>
            <td><c:out value="${airport.code}"/></td>
        </tr>
         <tr>
            <th>City</th>
            <td><c:out value="${airport.city}"/></td>
        </tr>
    </table>
    
    <spring:url value="/airports/{airportId}/edit" var="airportEditUrl">
       <spring:param name="airportId" value="${airport.id}"/>
    </spring:url>
    <button class="btn btn-default" onclick="window.location.href='${fn:escapeXml(airportEditUrl)}'">Edit</button>

</petclinic:layout>
