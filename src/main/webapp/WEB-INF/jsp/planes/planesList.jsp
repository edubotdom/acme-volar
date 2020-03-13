<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="planes">
    <h2>Planes</h2>

    <table id="planesTable" class="table table-striped">
        <thead>
        <tr>
            <th>Reference</th>
            <th>Max of Seats</th>
            <th>Description</th>
            <th>Manufacturer</th>
            <th>Model</th>
            <th>Number of Kilometers</th>
            <th>Max Distance</th>
            <th>Last maintenance</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${planes}" var="plane">
            <tr>
                <td>
                	<spring:url value="/planes/{planeId}" var="planeUrl">
                	    <spring:param name="planeId" value="${plane.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(planeUrl)}"><c:out value="${plane.reference}"/></a>
                </td>
                <td>
                    <c:out value="${plane.maxSeats}"/>
                </td>                
                <td>
                    <c:out value="${plane.description}"/>
                </td>
                <td>
                    <c:out value="${plane.manufacter}"/>
                </td>
                <td>
                    <c:out value="${plane.model}"/>
                </td>
                <td>
                    <c:out value="${plane.numberOfKm}"/>
                </td>
                <td>
                    <c:out value="${plane.maxDistance}"/>
                </td>
                <td>
                    <c:out value="${plane.lastMaintenance}"/>
                </td> 
            </tr>
        </c:forEach>
        </tbody>
    </table>

</petclinic:layout>
