<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="clients">

	<jsp:attribute name="customScript">
        <script>
									$(function() {
										$("#birthDate").datepicker({
											dateFormat : 'yy/mm/dd'
										});
									});
								</script>
    </jsp:attribute>
	<jsp:body>

    <h2>
        Register as a client!
    </h2>
    <form:form modelAttribute="client" class="form-horizontal" id="add-client-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Name" name="name" />
            <petclinic:inputField label="Identification" name="identification" />
            <petclinic:inputField label="Birth Date" name="birthDate" />
            <petclinic:inputField label="Email" name="email" />
            <petclinic:inputField label="Phone" name="phone" />
            <petclinic:inputField label="Username" name="user.username" />
            <petclinic:inputField label="Password" name="user.password" />
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                        <button class="btn btn-default" type="submit">Register</button>
            </div>
        </div>
    </form:form>
   	</jsp:body>
</petclinic:layout>
