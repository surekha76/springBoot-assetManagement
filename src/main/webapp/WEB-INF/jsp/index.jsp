<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/webjars/bootstrap/5.3.0/css/bootstrap.min.css">
<script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<meta charset="ISO-8859-1">
<title>Asset management</title>
<style type="text/css">
.sidebar {
	position: fixed;
	top: 0;
	bottom: 0;
	left: 0;
	z-index: 100;
	padding: 20px;
	background-color: #f1f1f1;
}
.main {
	margin-left: 250px;
}
@media ( max-width : 992px) {
	.sidebar {
		position: static;
		height: auto;
		margin-top: 20px;
	}
	.main {
		margin-left: 0;
	}
}
#sidebar a{
	color: black;
}
#sidebar a:hover{
	color: blue;
}
</style>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<nav id="sidebar" class="col-md-3 col-lg-2 d-md-block sidebar">
				<div class="position-sticky">
					<ul class="nav flex-column">
						<li class="nav-item"><a class="nav-link"
							href="${pageContext.request.contextPath}/employee">Employee</a></li>
						<li class="nav-item"><a class="nav-link"
							href="${pageContext.request.contextPath}/vendor">vendor</a></li>
						<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/asset">Asset</a>
						</li>
						<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/assignAsset">Assign Asset</a>
						</li>
					</ul>
				</div>
			</nav>
			<main id="main-content" class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
			</main>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#sidebar a[href="#"]').addClass('active');
			
			$('#main-content').load('/employee');
			
			$('#sidebarCollapse').on('click', function() {
				$('#sidebar').toggleClass('active');
			});

			$('#sidebar a').on('click', function(event) {
				event.preventDefault(); 
				var targetURL = $(this).attr('href');
				$('#main-content').load(targetURL);
			});
		});
	</script>
</body>
</html>