<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="planes">

    <h2>Plane Information</h2>


    <table class="table table-striped">
        <tr>
            <th>Reference</th>
            <td><b><c:out value="${plane.reference}"/></b></td>
        </tr>
        <tr>
            <th>Max of Seats</th>
            <td><c:out value="${plane.maxSeats}"/></td>
        </tr>
        <tr>
            <th>Description</th>
            <td><c:out value="${plane.description}"/></td>
        </tr>
        <tr>
            <th>Manufacturer</th>
            <td><c:out value="${plane.manufacter}"/></td>
        </tr>
        <tr>
            <th>Model</th>
            <td><c:out value="${plane.model}"/></td>
        </tr>
        <tr>
            <th>Number of Kilometers</th>
            <td><c:out value="${plane.numberOfKm}"/></td>
        </tr>
        <tr>
            <th>Max Distance</th>
            <td><c:out value="${plane.maxDistance}"/></td>
        </tr>
        <tr>
            <th>Last maintenance</th>
            <td><c:out value="${plane.lastMaintenance}"/></td>
        </tr>
    </table>
	<!-- 
    <spring:url value="{planeId}/edit" var="editUrl">
        <spring:param name="planeId" value="${plane.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit Plane</a>

    <spring:url value="{planeId}/plane/new" var="addUrl">
        <spring:param name="planeId" value="${plane.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Add New Plane</a>
	-->
    

</petclinic:layout>
