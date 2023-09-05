<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/webjars/bootstrap/5.3.0/css/bootstrap.min.css">
<script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<style type="text/css">
.modal-body form input[type="text"] {
    text-transform: lowercase;
}
</style>
</head>
<body>
	<div class="container">
		<button type="button" class="btn btn-primary mt-3 mb-3"
			id="addEmployee">Add Employee</button>
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th>No</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Department</th>
					<th>Phone Number</th>
					<th>Email</th>
					<th>Status</th>
					<th>Update</th>
					<th>Delete</th>
				</tr>
			</thead>
			<tbody id="employeeTableBody"></tbody>
		</table>
		<div class="modal fade" id="addEmployeeModal" tabindex="-1"
			aria-labelledby="addEmployeeModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="addEmployeeModalLabel">Add
							Employee</h5>
						<h5 class="modal-title" id="updateEmployeeModalLabel"
							style="display: none;">Update Employee</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<div class="modal-body">
							<form id="employeeForm">
								<input style="display: none;" type="text" class="form-control"
									id="id" name="id">
								<div class="row mb-3">
									<div class="col-md-6">
										<label for="firstName" class="form-label">First Name</label> <input
											type="text" class="form-control" id="firstName"
											name="firstName" required>
									</div>
									<div class="col-md-6">
										<label for="lastName" class="form-label">Last Name</label> <input
											type="text" class="form-control" id="lastName"
											name="lastName" required>
									</div>
								</div>
								<div class="row mb-3">
									<div class="col-md-6">
										<label for="department" class="form-label">Department</label>
										<input type="text" class="form-control" id="department"
											name="department" required>
									</div>
									<div class="col-md-6">
										<label for="phoneNo" class="form-label">Phone Number</label> <input
											type="text" class="form-control" id="phoneNo" name="phoneNo"
											required>
									</div>
								</div>
								<div class="row mb-3">
									<div class="col-md-6">
										<label for="email" class="form-label">Email</label> <input
											type="email" class="form-control" id="email" name="email"
											required>
									</div>
									<div class="col-md-6">
										<label class="form-label">Status</label>
										<div class="form-check">
											<input class="form-check-input" type="radio" name="status"
												id="status1" value="Active" checked> <label
												class="form-check-label" for="status1"> Active </label>
										</div>
										<div class="form-check">
											<input class="form-check-input" type="radio" name="status"
												id="status2" value="Inactive"> <label
												class="form-check-label" for="status2"> Inactive </label>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary"
							id="addEmployeeButton">Add</button>
						<button type="button" class="btn btn-primary"
							id="updateEmployeeButton" style="display: none;">Update</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
		$(document).ready(function() {
			fetchEmployees();
		});
		function redirectToTarget() {
			window.location.assign("employee.jsp");
		}
		$("#addEmployee").on("click", function() {
			$("#employeeForm")[0].reset();
			$("#addEmployeeButton").show();
			$("#updateEmployeeButton").hide();
			$("#addEmployeeModalLabel").show();
			$("#updateEmployeeModalLabel").hide();
			$("#addEmployeeModal").modal("show");
		});
		function validateForm() {
			var firstName = document.getElementById('firstName').value;
			var lastName = document.getElementById('lastName').value;
			var department = document.getElementById('department').value;
			var phoneNo = document.getElementById('phoneNo').value;
			var email = document.getElementById('email').value;
			var status = document.querySelector('input[name="status"]:checked').value;
			var phoneRegex = /^[6789]\d{9}$/;
			var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
			
			if (firstName.trim() === '') {
				alert('First Name is required.');
				return false;
			} else if (lastName.trim() === '') {
				alert('Last Name is required.');
				return false;
			} else if (department.trim() === '') {
				alert('Department is required.');
				return false;
			}else if (phoneNo.trim() === '') {
				alert('Phone Number is required.');
				return false;
			}else if (!phoneRegex.test(phoneNo)) {
				alert('Enter valid Phone Number.');
				return false;
			}else if (email.trim() === '') {
				alert('Email is required.');
				return false;
			}else if (!emailRegex.test(email)) {
				alert('Enter Valid Email.');
				return false;
			}else if (status.trim() === '') {
				alert('status is required.');
				return false;
			} else {
				return true;
			}
		}
		function fetchEmployees() {
			var index = 1;
			$.ajax({
				url : "/getAllEmployee",
				type : "GET",
				success : function(data) {
					console.log("employee data",data.length);
					var tbody = $("#employeeTableBody");
					tbody.empty();
					if(data.length!=0){
						data.forEach(function(employee) {
							console.log("employee length",employee.length);
							var row = "<tr data_id=" + employee.id + ">" +
					          "<td style='display:none;'>" + employee.id + "</td>" +
					          "<td>" + index + "</td>" +
					          "<td>" + employee.firstName + "</td>" +
					          "<td>" + employee.lastName + "</td>" +
					          "<td>" + employee.department + "</td>" +
					          "<td>" + employee.phoneNo + "</td>" +
					          "<td>" + employee.email + "</td>" +
					          "<td>" + employee.status + "</td>" +
					          "<td>" + "<button onclick='updateEmployee(" + employee.id + ")'>View</button>" + "</td>" +
					          "<td>" + "<button onclick='deleteEmployee(" + employee.id + ")'>Delete</button>" + "</td>" +
					          "</tr>";
							tbody.append(row);
							index++;
						});	
					}else{
						console.log("no data");
						var row = "<tr> <td colspan='9' style='text-align:center'>No Data Available</td> </tr>";
						tbody.append(row);	
					}
				},
				error : function(error) {
					console.log(error);
					alert("Error fetching employees");
				}
			});
		}
		$("#addEmployeeButton").on("click", function() {
			var validate = validateForm();
			var formData = new FormData($("#employeeForm")[0]);
			var employee = {};
			formData.forEach(function(value, key) {
				employee[key] = value;
			});
			console.log(employee);
			if (validate) {
				$.ajax({
					url : "/saveEmployee",
					method : "POST",
					contentType : "application/json",
					data : JSON.stringify(employee),
					success : function(response) {
						console.log(response);
						$("#addEmployeeModal").modal("hide");
						fetchEmployees();
						alert(response);
					},
					error : function(error) {
						$("#addEmployeeModal").modal("hide");
						alert(error);
						console.log(error);
					}
				});
			} else {
				alert(nullField);
			}
		});
		function deleteEmployee(id) {
			console.log(id);
			$.ajax({
				url : "/deleteEmployee?id=" + id,
				type : "POST",
				success : function(data) {
					fetchEmployees();
					alert(data);
				},
				error : function(error) {
					alert(error);
				}
			});
		}
		function updateEmployee(id) {
			console.log(id);
			//var row = $("td:contains(" + id + ")").closest("tr");
			var row = $("tr[data_id='" + id + "']");
			var id = row.find("td:eq(0)").text();
			var firstName = row.find("td:eq(2)").text();
			var lastName = row.find("td:eq(3)").text();
			var department = row.find("td:eq(4)").text();
			var phoneNo = row.find("td:eq(5)").text();
			var email = row.find("td:eq(6)").text();
			var status = row.find("td:eq(7)").text();
			console.log(status)
			$("#id").val(id);
			$("#firstName").val(firstName);
			$("#lastName").val(lastName);
			$("#department").val(department);
			$("#phoneNo").val(phoneNo);
			$("#email").val(email);
			$('input[name="status"][value="'+status+'"]').prop('checked', true);

			$("#addEmployeeButton").hide();
			$("#updateEmployeeButton").show();
			$("#addEmployeeModalLabel").hide();
			$("#updateEmployeeModalLabel").show();
			$("#addEmployeeModal").modal("show");
		}
		$("#updateEmployeeButton").on("click", function() {
			var validate = validateForm();
			var formData = new FormData($("#employeeForm")[0]);
			var employee = {};
			formData.forEach(function(value, key) {
				employee[key] = value;
			});
			console.log("update");
			console.log(employee);
			if (validate) {
				$.ajax({
					url : "/updateEmployee",
					method : "POST",
					contentType : "application/json",
					data : JSON.stringify(employee),
					success : function(data) {
						console.log(data);
						$("#addEmployeeModal").modal("hide");
						fetchEmployees();
						document.getElementById("employeeForm").reset();
						alert(data);
					},
					error : function(error) {
						$("#addEmployeeModal").modal("hide");
						console.log(error);
						alert(error);
					}
				});
			}
		});
	</script>
</body>
</html>