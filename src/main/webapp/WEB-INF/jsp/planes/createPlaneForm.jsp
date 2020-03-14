<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="planes">

	<jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#lastMaintenance").datepicker({dateFormat: 'yyyy-mm-dd'});
            });
        </script>
    </jsp:attribute>

	<jsp:body>
    <h2>
        Register a Plane!
    </h2>
    <form:form modelAttribute="plane" class="form-horizontal" id="add-plane-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Reference" name="reference" />
            <petclinic:inputField label="Max seats" name="maxSeats" />
            <petclinic:inputField label="Description" name="description" />
            <petclinic:inputField label="Manufacter" name="manufacter" />
            <petclinic:inputField label="Model" name="model" />
            <petclinic:inputField label="Number of Km" name="numberOfKm" />
            <petclinic:inputField label="Max distance" name="maxDistance" />
            <petclinic:inputField label="Last Maintenance" name="lastMaintenance"/>
            
            
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
             <c:choose>
                    <c:when test="${plane['new']}">
                        <button class="btn btn-default" type="submit">Register plane</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update plane</button>
                    </c:otherwise>
            </c:choose>
            </div>

        </div>
    </form:form>
   	</jsp:body>
</petclinic:layout>
