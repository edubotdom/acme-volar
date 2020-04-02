<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ attribute name="name" required="true" rtexprvalue="true" description="Name of the active menu: home, owners, vets or error"%>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#main-navbar">
				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">

				<sec:authorize access="hasAuthority('airline')">
					<petclinic:menuItem active="${name eq 'clients'}" url="/clients" title="List clients">
						<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
						<span>Clients</span>
					</petclinic:menuItem>
				</sec:authorize>
				
<%--				<sec:authorize access="!hasAuthority('airline')"> --%>
					<petclinic:menuItem active="${name eq 'airlines'}" url="/airlines" title="List airlines">
						<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
						<span>Airlines</span>
					</petclinic:menuItem>
<%--				</sec:authorize> --%>

				<sec:authorize access="hasAnyAuthority('client','airline')">
					<petclinic:menuItem active="${name eq 'airports'}" url="/airports" title="Airports">
						<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
						<span>Airports</span>
					</petclinic:menuItem>
				</sec:authorize>
				

<%--				<sec:authorize access="!hasAuthority('airline')"> --%>
					<petclinic:menuItem active="${name eq 'flights'}" url="/flights" title="List flights">
						<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
						<span>Flights</span>
					</petclinic:menuItem>
<%--				</sec:authorize> --%>
				
				<sec:authorize access="hasAuthority('airline')">
					<petclinic:menuItem active="${name eq 'clients'}" url="/my_flights" title="List clients">
						<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
						<span>My Flights</span>
					</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('airline')">
					<petclinic:menuItem active="${name eq 'clients'}" url="/my_planes" title="List clients">
						<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
						<span>My Planes</span>
					</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('airline')">
					<petclinic:menuItem active="${name eq 'airline'}" url="/books/airline" title="List books">
						<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
						<span>My Books</span>
					</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('client')">
					<petclinic:menuItem active="${name eq 'client'}" url="/books/client" title="List books">
						<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
						<span>My Books</span>
					</petclinic:menuItem>
				</sec:authorize>
				
			</ul>

			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Login</a></li>
					<li><a href="<c:url value="/clients/new" />">Register as a client</a></li>
					<li><a href="<c:url value="/airlines/new" />">Register as an airline</a></li>
				</sec:authorize>
				
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"> <span
							class="glyphicon glyphicon-user"></span> <strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"> </span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											<p class="text-left">
												<a href="<c:url value="/logout" />" class="btn btn-primary btn-block btn-sm">Logout</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>
							<!-- 							
                            <li> 
								<div class="navbar-login navbar-login-session">
									<div class="row">
										<div class="col-lg-12">
											<p>
												<a href="#" class="btn btn-primary btn-block">My Profile</a>
												<a href="#" class="btn btn-danger btn-block">Change
													Password</a>
											</p>
										</div>
									</div>
								</div>
							</li>
-->
						</ul></li>
				</sec:authorize>
			</ul>
		</div>



	</div>
</nav>
