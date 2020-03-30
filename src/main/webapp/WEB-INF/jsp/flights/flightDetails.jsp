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
			<th>Reference</th>
			<td><spring:url value="/flights/{flightId}" var="flightUrl">
					<spring:param name="flightId" value="${flight.id}" />
				</spring:url> <a href="${fn:escapeXml(flightUrl)}"><c:out value="${flight.reference}" /></a></td>
		</tr>
		<tr>
			<th>Seats</th>
			<td><c:out value="${flight.seats}" /></td>
		</tr>
		<tr>
			<th>Published</th>
			<td><c:out value="${flight.published}" /></td>
		</tr>
		<tr>
			<th>Airline</th>
			<td><spring:url value="/airlines/{airlineId}" var="airlineUrl">
					<spring:param name="airlineId" value="${flight.airline.id}" />
				</spring:url> <a href="${fn:escapeXml(airlineUrl)}"><c:out value="${flight.airline.name}" /></a></td>
		</tr>
		<tr>
			<th>Depart airport</th>
			<td><spring:url value="/airports/{airportId}" var="departAirportURL">
					<spring:param name="airportId" value="${flight.departes.airport.id}" />
				</spring:url> <sec:authorize access="hasAuthority('airline')||hasAuthority('client')">
					<a href="${fn:escapeXml(departAirportURL)}"><c:out value="${flight.departes.airport.name}" /></a>
				</sec:authorize> <sec:authorize access="!(hasAuthority('airline')||hasAuthority('client'))">
					<c:out value="${flight.departes.airport.name}" />
				</sec:authorize></td>
		</tr>
		<tr>
			<th>Depart runway</th>
			<td><spring:url value="/airports/{airportId}/runways" var="departesUrl">
					<spring:param name="airportId" value="${flight.departes.airport.id}" />
				</spring:url> <sec:authorize access="hasAuthority('airline')||hasAuthority('client')">
					<a href="${fn:escapeXml(departesUrl)}"><c:out value="${flight.departes.name}" /></a>
				</sec:authorize> <sec:authorize access="!(hasAuthority('airline')||hasAuthority('client'))">
					<c:out value="${flight.departes.name}" />
				</sec:authorize></td>
		</tr>
		<tr>
			<th>Depart Date</th>
			<td><c:out value="${flight.departDate}" /></td>
		</tr>
		<tr>
			<th>Landing airport</th>
			<td><spring:url value="/airports/{airportId}" var="landAirportUrl">
					<spring:param name="airportId" value="${flight.lands.airport.id}" />
				</spring:url> <sec:authorize access="hasAuthority('airline')||hasAuthority('client')">
					<a href="${fn:escapeXml(landAirportUrl)}"><c:out value="${flight.lands.airport.name}" /></a>
				</sec:authorize> <sec:authorize access="!(hasAuthority('airline')||hasAuthority('client'))">
					<c:out value="${flight.lands.airport.name}" />
				</sec:authorize></td>
		</tr>
		<tr>
			<th>Landing runway</th>
			<td><spring:url value="/airports/{airportId}/runways" var="landsUrl">
					<spring:param name="airportId" value="${flight.lands.airport.id}" />
				</spring:url> <sec:authorize access="hasAuthority('airline')||hasAuthority('client')">
					<a href="${fn:escapeXml(landsUrl)}"><c:out value="${flight.lands.name}" /></a>
				</sec:authorize> <sec:authorize access="!(hasAuthority('airline')||hasAuthority('client'))">
					<c:out value="${flight.lands.name}" />
				</sec:authorize></td>
		</tr>
		<tr>
			<th>Land Date</th>
			<td><c:out value="${flight.landDate}" /></td>
		</tr>
		<tr>
			<th>Plane</th>
			<td><spring:url value="/planes/{planeId}" var="planeUrl">
					<spring:param name="planeId" value="${flight.plane.id}" />
				</spring:url> <a href="${fn:escapeXml(planeUrl)}"><c:out value="${flight.plane.model}" /></a></td>
		</tr>
		<tr>
			<th>Price</th>
			<td><c:out value="${flight.price}" /></td>
		</tr>

	</table>
	<c:if test="${flight.published==false}">
		<sec:authorize access="hasAuthority('airline')">
			<spring:url value="{flightId}/edit" var="editUrl">
				<spring:param name="flightId" value="${flight.id}" />
			</spring:url>
			<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit Flight</a>
		</sec:authorize>
	</c:if>
</petclinic:layout>

