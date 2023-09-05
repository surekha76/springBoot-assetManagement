<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<!-- Include Bootstrap CSS from WebJars -->
<link rel="stylesheet"
	href="/webjars/bootstrap/5.3.0/css/bootstrap.min.css">
<!-- Include jQuery from WebJars -->
<script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<style>
.modal-body form #vendorName, #email, #address {
    text-transform: lowercase;
}
</style>
</head>
<body>
	<div class="container">
		<button type="button" class="btn btn-primary mt-3 mb-3"
			id="addVendor">Add Vendor</button>
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th>No</th>
					<th>Vendor Name</th>
					<th>Phone Number</th>
					<th>Email</th>
					<th>Location</th>
					<th>GSTIN Number</th>
					<th>Update</th>
					<th>Delete</th>
				</tr>
			</thead>
			<tbody id="vendorTableBody"></tbody>
		</table>
		<div class="modal fade" id="addVendorModal" tabindex="-1"
			aria-labelledby="addVendorModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="addVendorModalLabel">Add
							Vendor</h5>
						<h5 class="modal-title" id="updateVendorModalLabel"
							style="display: none;">Update Vendor</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<div class="modal-body">
							<form id="vendorForm">
								<input style="display: none;" type="text" class="form-control"
									id="vendorId" name="vendorId">
								<div class="row mb-3">
									<div class="col-md-6">
										<label for="name" class="form-label">Vendor Name</label> <input
											type="text" class="form-control" id="vendorName"
											name="vendorName" required>
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
										<label for="phoneNo" class="form-label">Location</label> <input
											type="text" class="form-control" id="address" name="address"
											required>
									</div>
								</div>
								<div class="row mb-3">
									<div class="col-md-6">
										<label for="gst" class="form-label">GSTIN Number</label> <input
											type="text" class="form-control" id="gstinNumber" name="gstinNumber"
											required>
									</div>
								</div>
							</form>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary"
							id="addVendorButton">Add</button>
						<button type="button" class="btn btn-primary"
							id="updateVendorButton" style="display: none;">Update</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
		$(document).ready(function() {
			fetchVendors();
		});
		$("#addVendor").on("click", function() {
			console.log('modal open');
			$("#vendorForm")[0].reset();
			$("#addVendorButton").show();
			$("#updateVendorutton").hide();
			$("#addVendorModalLabel").show();
			$("#updateVendorModalLabel").hide();
			$("#addVendorModal").modal("show");
		});
		function validateForm() {
			var vendorName = document.getElementById('vendorName').value;
			var phoneNo = document.getElementById('phoneNo').value;
			var email = document.getElementById('email').value;
			var address = document.getElementById('address').value;
			var gstin = document.getElementById('gstinNumber').value;
			var phoneRegex = /^[6789]\d{9}$/;
			var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
			var gstinRegex = /^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}[0-9A-Z]{1}[Z]{1}$/;
			
			if (vendorName.trim() === '') {
				alert('Vendor Name is required.');
				return false;
			} else if (phoneNo.trim() === '') {
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
			}else if (address.trim() === '') {
				alert('Vendor Address is required.');
				return false;
			} else if (gstin.trim() === '') {
				alert('GSTIN Number is required.');
				return false;
			}else if (!gstinRegex.test(gstin)) {
				alert('Enter Valid GSTIN Number.');
				return false;
			} else {
				return true;
			}
		}
		function fetchVendors() {
			var index = 1;
			$.ajax({
				url : "/getAllVendor",
				type : "GET",
				success : function(data) {
					console.log(data);
					var tbody = $("#vendorTableBody");
					tbody.empty();
					if(data.length!=0){
						data.forEach(function(vendor) {
							var row = "<tr data_id=" + vendor.vendorId + ">" +
					          "<td style='display:none;'>" + vendor.vendorId + "</td>" +
					          "<td>" + index + "</td>" +
					          "<td>" + vendor.vendorName + "</td>" +
					          "<td>" + vendor.phoneNo + "</td>" +
					          "<td>" + vendor.email + "</td>" +
					          "<td>" + vendor.address + "</td>" +
					          "<td>" + vendor.gstinNumber + "</td>" +
					          "<td>" + "<button onclick='updateVendor(" + vendor.vendorId + ")'>View</button>" + "</td>" +
					          "<td>" + "<button onclick='deleteVendor(" + vendor.vendorId + ")'>Delete</button>" + "</td>" +
					          "</tr>";
							tbody.append(row);
							index++;	
						}); 	
					}else{
						var row = "<tr> <td colspan='8' style='text-align:center'>No Data Available</td> </tr>";
						tbody.append(row);
					}
				},
				error : function(error) {
					console.log(error);
					alert("Error fetching employees");
				}
			});
		}
		$("#addVendorButton").on("click", function() {
			var validate = validateForm();
			var formData = new FormData($("#vendorForm")[0]);
			var vendor = {};
			formData.forEach(function(value, key) {
				vendor[key] = value;
			});
			console.log(vendor);
			if (validate) {
				$.ajax({
					url : "/saveVendor",
					method : "POST",
					contentType : "application/json",
					data : JSON.stringify(vendor),
					success : function(response) {
						console.log(response);
						$("#addVendorModal").modal("hide");
						fetchVendors();
						alert(response);
					},
					error : function(error) {
						$("#addVendorModal").modal("hide");
						alert(error);
						console.log(error);
					}
				});
			} else {
				alert(nullField);
			}
		});
		function deleteVendor(id) {
			console.log(id);
			$.ajax({
				url : "/deleteVendor?id=" + id,
				type : "POST",
				success : function(data) {
					fetchVendors();
					alert(data);
				},
				error : function(error) {
					alert(error);
				}
			});
		}
		function updateVendor(id) {
			console.log(id);
			//var row = $("td:contains(" + id + ")").closest("tr");
			var row = $("tr[data_id='" + id + "']");
			var vendorId = row.find("td:eq(0)").text();
			var vendorName = row.find("td:eq(2)").text();
			var phoneNo = row.find("td:eq(3)").text();
			var email = row.find("td:eq(4)").text();
			var address = row.find("td:eq(5)").text();
			var gstinNumber = row.find("td:eq(6)").text();
			console.log(status)
			$("#vendorId").val(vendorId);
			$("#vendorName").val(vendorName);
			$("#phoneNo").val(phoneNo);
			$("#email").val(email);
			$("#address").val(address);
			$("#gstinNumber").val(gstinNumber);

			$("#addVendorButton").hide();
			$("#updateVendorButton").show();
			$("#addVendorModalLabel").hide();
			$("#updateVendorModalLabel").show();
			$("#addVendorModal").modal("show");
		}
		$("#updateVendorButton").on("click", function() {
			var validate = validateForm();
			var formData = new FormData($("#vendorForm")[0]);
			var vendor = {};
			formData.forEach(function(value, key) {
				vendor[key] = value;
			});
			console.log("Update",vendor);
			if (validate) {
				$.ajax({
					url : "/updateVendor",
					method : "POST",
					contentType : "application/json",
					data : JSON.stringify(vendor),
					success : function(data) {
						console.log(data);
						$("#addVendorModal").modal("hide");
						fetchVendors();
						document.getElementById("vendorForm").reset();
						alert(data);
					},
					error : function(error) {
						$("#addVendorModal").modal("hide");
						console.log(error);
						alert(error);
					}
				});
			}
		});
	</script>
</body>
</html>