<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="flights">

	<h2>Flight Information</h2>


	<table class="table table-striped">
	
		<tr>
			<th>Airline</th>
			<td><b><c:out value="${flight.airline.name}" /></b></td>
		</tr>
		<tr>
			<th>Reference</th>
			<td><b><c:out value="${flight.reference}" /></b></td>
		</tr>
		<tr>
			<th>Seats</th>
			<td><c:out value="${flight.seats}" /></td>
		</tr>
		<tr>
			<th>Price</th>
			<td><c:out value="${flight.price}" /></td>
		</tr>
		<tr>
			<th>Flight Status</th>
			<td><c:out value="${flight.flightStatus}" /></td>
		</tr>
		<tr>
			<th>Published</th>
			<td><c:out value="${flight.published}" /></td>
		</tr>
		<tr>
			<th>Plane</th>
			<td><c:out value="${flight.plane.reference}" /></td>
		</tr>
		<tr>
			<th>Departes</th>
			<td><c:out value="${flight.departes.name}" /></td>
		</tr>
		<tr>
			<th>Lands</th>
			<td><c:out value="${flight.lands.name}" /></td>
		</tr>
		
		
	</table>

	<sec:authorize access="hasAuthority('airline')">
		<spring:url value="{flightId}/edit" var="editUrl">
			<spring:param name="flightId" value="${flight.id}" />
		</spring:url>
		<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit Flight</a>
	</sec:authorize>

</petclinic:layout>
