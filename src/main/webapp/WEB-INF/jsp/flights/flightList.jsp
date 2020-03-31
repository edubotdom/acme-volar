<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="flights">
	<h2>Flights</h2>

	<table id="flightsTable" class="table table-striped">
		<thead>
			<tr>
				<th>Reference</th>
				<th>Airline</th>
				<th>Departure Hour</th>
				<th>Arrival Hour</th>
				<th>Plane</th>
				<th>Price</th>
<sec:authorize access="hasAuthority('airline')||hasAuthority('client')">
				<th>Departure City</th>
				<th>Arrival City</th>
</sec:authorize>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${flights}" var="flight">
				<tr>
					<td><spring:url value="/flights/{flightId}" var="flightUrl">
							<spring:param name="flightId" value="${flight.id}" />
						</spring:url> <a href="${fn:escapeXml(flightUrl)}"><c:out value="${flight.reference}" /></a></td>
						
					<td><spring:url value="/airlines/{airlineId}" var="airlineUrl">
							<spring:param name="airlineId" value="${flight.airline.id}" />
						</spring:url> <a href="${fn:escapeXml(airlineUrl)}"><c:out value="${flight.airline.name}" /></a></td>

					<td><c:out value="${flight.departDate}" /></td>
							
					<td><c:out value="${flight.landDate}" /></td>
					
					<td><spring:url value="/planes/{planeId}" var="planeUrl">
							<spring:param name="planeId" value="${flight.plane.id}" />
						</spring:url> <a href="${fn:escapeXml(planeUrl)}"><c:out value="${flight.plane.model}" /></a></td>
					
					<td><c:out value="${flight.price}" /></td>

					<sec:authorize access="hasAuthority('airline')||hasAuthority('client')">					
					<td><spring:url value="/airports/{airportId}" var="departesUrl">
							<spring:param name="airportId" value="${flight.departes.airport.id}" />
					</spring:url> <a href="${fn:escapeXml(departesUrl)}"><c:out value="${flight.departes.airport.city}" /></a></td>
						
					<td><spring:url value="/airports/{airportId}" var="landsUrl">
							<spring:param name="airportId" value="${flight.lands.airport.id}" />
					</spring:url> <a href="${fn:escapeXml(landsUrl)}"><c:out value="${flight.lands.airport.city}" /></a></td>
					</sec:authorize>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<sec:authorize access="hasAuthority('airline')">
			<button class="btn btn-default" onclick="window.location.href='/flights/new'">New</button>
	</sec:authorize>

</petclinic:layout>
