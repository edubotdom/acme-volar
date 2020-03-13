<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="flights">
	<jsp:body>
    <h2>
        Register a flight!
    </h2>
    <form:form modelAttribute="flight" class="form-horizontal" id="add-flight-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Reference" name="reference" />
            <petclinic:inputField label="Seats" name="seats" />
            <petclinic:inputField label="Price" name="price" />
            <petclinic:inputField label="Published" name="published" />
            <petclinic:selectField label="Status" name="flightStatus" size="3" names="${estados}"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                        <!--<button class="btn btn-default" type="submit">Persist flight</button>-->
             <c:choose>
                    <c:when test="${flight['new']}">
                        <button class="btn btn-default" type="submit">Register flight</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update flight</button>
                    </c:otherwise>
            </c:choose>
            </div>

        </div>
    </form:form>
   	</jsp:body>
</petclinic:layout>
