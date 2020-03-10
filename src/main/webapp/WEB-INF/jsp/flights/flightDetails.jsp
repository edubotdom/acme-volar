<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="flights">

    <h2>Flight Information</h2>


    <table class="table table-striped">
        <tr>
            <th>Reference</th>
            <td><b><c:out value="${flight.reference}"/></b></td>
        </tr>
        <tr>
            <th>Seats</th>
            <td><c:out value="${flight.seats}"/></td>
        </tr>
        <tr>
            <th>Price</th>
            <td><c:out value="${flight.price}"/></td>
        </tr>
        <tr>
            <th>Flight Status</th>
            <td><c:out value="${flight.flightStatus}"/></td>
        </tr>
         <tr>
            <th>Published</th>
            <td><c:out value="${flight.published}"/></td>
        </tr>
    </table>

</petclinic:layout>
