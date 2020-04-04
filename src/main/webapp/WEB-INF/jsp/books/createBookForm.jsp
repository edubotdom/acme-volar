<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<petclinic:layout pageName="book">

	<jsp:attribute name="customScript">
        <script>
									function submitFunction() {
										var quantity = $("#quantity")[0].value;
										var price = $
										{
											flight.price
										}
										var totalPrice = quantity * price;
										var submit = confirm("The price would be " + totalPrice + ". Confirm?");
										if (submit == true) {
											$("#submit").submit();
										}
									};
								</script>
        
    </jsp:attribute>

	<jsp:body>
	<c:choose>
    <c:when test="${book['new']}">
		<h2>
			Book this flight!
		</h2>
	</c:when>
    </c:choose>
    
    
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
    
    <form:form modelAttribute="book" class="form-horizontal" id="add-book-form">
        <div class="form-group has-feedback">
        <sec:authorize access="hasAuthority('client')">
            <petclinic:inputField label="Quantity" name="quantity" />
        </sec:authorize>
            <input type="hidden" name="price" value="0">
<!--           <input type="hidden" name="bookStatusType" value="${book.bookStatusType}">
            <input type="hidden" name="client" value="${book.client.id}">
            <input type="hidden" name="flight" value="${book.flight.id}">  --> 
            <input type="hidden" name="moment" value="${book.moment}">
              
        </div>
        
        <sec:authorize access="hasAuthority('airline')">
       		<div class="control-group">
       			<input type="hidden" name="quantity" value="${book.quantity}">
       			Quantity: <c:out value="${book.quantity}" />
            	<petclinic:selectField label="Type" name="bookStatusType" size="2" names="${bookStatusTypes}" />
			</div>
        </sec:authorize>
        
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
             <c:choose>
                    <c:when test="${book['new']}">
                        <!-- <button class="btn btn-default" onclick="submitFunction()">Book Flight</button> -->
                        <button class="btn btn-default" type="submit" id="submit">Book Flight</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit" id="submit">Update Book</button>
                    </c:otherwise>
            </c:choose>
            </div>

        </div>
    </form:form>
   	</jsp:body>
</petclinic:layout>
