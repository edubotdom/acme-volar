<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="clients">

    <h2>Client Information</h2>


    <table class="table table-striped">
        <tr>
            <th>Name</th>
            <td><b><c:out value="${client.name}"/></b></td>
        </tr>
        <tr>
            <th>Identification</th>
            <td><c:out value="${client.identification}"/></td>
        </tr>
        <tr>
            <th>Birth Date</th>
            <td><c:out value="${client.birthDate}"/></td>
        </tr>
        <tr>
            <th>Email</th>
            <td><c:out value="${client.email}"/></td>
        </tr>
        <tr>
            <th>Phone</th>
            <td><c:out value="${client.phone}"/></td>
        </tr>
        <tr>
            <th>Creation Date</th>
            <td><c:out value="${client.creationDate}"/></td>
        </tr>
    </table>

</petclinic:layout>
