<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="/webjars/bootstrap/5.3.0/css/bootstrap.min.css">
<script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="/webjars/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>

</head>
<body>
	<div class="container">
		<button type="button" class="btn btn-primary mt-3 mb-3" id="addAssignAsset">Assign
			Asset</button>
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th>No</th>
					<th>Asset Name</th>
					<th>Assigned To</th>
					<th>Assigning Date</th>
					<th>Assigned By</th>
					<th>View</th>
					<th>Delete</th>
				</tr>
			</thead>
			<tbody id="assignAssetTableBody"></tbody>
		</table>
		<div class="modal fade" id="assignAssetModal" tabindex="-1"
			aria-labelledby="assignAssetModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="assignAssetModalLabel">Assign Asset</h5>
						<h5 class="modal-title" id="reassignAssetModalLabel"
							style="display: none;">Reassign Asset</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<div class="modal-body">
							<form id="assignAssetForm">
								<!-- <input style="display: none;" type="text" class="form-control"
									id="assetId" name="assetId"> -->
								<div class="row mb-3">
									<div class="col-md-6">
										<label for="assetName" class="form-label">Asset Name</label> <select
											class="form-select" id="assetId" name="assetId" required>
										</select>
									</div>
									<div class="col-md-6">
										<label for="category">Assigned To</label>
										<select
											class="form-select" id="employeeId" name="employeeId" required>
										</select>
									</div>
								</div>
								<div class="row mb-3">
									<div class="col-md-6">
										<label for="purchaseDate" class="form-label">Assigned BY</label> 
										<input type="text" class="form-control"
											id="assignedBy" name="assignedBy" required>
									</div>
									<div class="col-md-6">
										<label for="warrantyDate" class="form-label">Assigned
											Date</label> <input type="date" class="form-control"
											id="assignedDate" name="assignedDate"  required>
									</div>
								</div>
							</form>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" id="assignButton">Assign</button>
						<button type="button" class="btn btn-primary"
							id="reassignButton" style="display: none;">Reassign</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
		var employee;
		$(document).ready(function() {
			fetchAssignedAsset();
			//fetchAssetDetails();
			fetchEmployeeDetails();
		});
		$("#addAssignAsset").on("click", function() {
			$("#assignAssetForm")[0].reset();
			$("#addAssignAssetButton").show();
			$("#updateAssignAssetButton").hide();
			$("#assignAssetModalLabel").show();
			$("#reassignAssetModalLabel").hide();
			$("#assignAssetModal").modal("show");
		});
		function validateForm() {
			var assetId = document.getElementById('assetId').value;
			var employeeId = document.getElementById('employeeId').value;
			var assignedBy = document.getElementById('assignedBy').value;
			var assignedDate = document.getElementById('assignedDate').value;
			if (assetId.trim() === '') {
				alert('Asset is required.');
				return false;
			} else if (employeeId.trim() === '') {
				alert('Assigned To is required.');
				return false;
			} else if (assignedBy.trim() === '') {
				alert('Assigned By is required.');
				return false;
			} else if (assignedDate.trim() === '') {
				alert('Assigned Date is required.');
				return false;
			}else {
				return true;
			}
		}
		function fetchEmployeeDetails(){
			$.ajax({
				url : "/getAllEmployee",
				type : "GET",
				success : function(data) {
					console.log(data);
					var dropdown = $("#employeeId");
				      dropdown.empty();
				      dropdown.append("<option value=''>Select a Employee</option>");
				      data.forEach(function (employee) {
				        dropdown.append("<option value='" + employee.id + "'>" + employee.firstName + "</option>");
				      });
				},
				error : function(error) {
					console.log(error);
					alert("Error in fetching employee details");
				}
			});
		}
/* 		function fetchAssetDetails(){
			$.ajax({
				url : "/getAllAsset",
				type : "GET",
				success : function(data) {
					console.log(data);
					var dropdown = $("#assetId");
				      dropdown.empty();
				      dropdown.append("<option value=''>Select a Asset</option>");
				      data.forEach(function (asset) {
				    	if(asset.status=="Unassigned"){
				    		dropdown.append("<option value='" + asset.assetId + "'>" + asset.assetName + "</option>");	
				    	}
				      });
				},
				error : function(error) {
					console.log(error);
					alert("Error in fetching asset details");
				}
			});
		} */
		function fetchAssignedAsset() {
			var index = 1;
			$.ajax({
				url : "/getAllAsset",
				type : "GET",
				success : function(data) {
					var tbody = $("#assignAssetTableBody");
					tbody.empty();
					data.forEach(function(asset) {
						console.log(asset);
						if(asset.status=="Assigned"){
							var row = "<tr data_id=" + asset.assetId + ">"
							+ "<td style='display:none;'>" + asset.assetId + "</td>"
							+ "<td>" + index + "</td>"
							+ "<td>" + asset.assetName + "</td>"
							+ "<td style='display:none;'>" + asset.employee.id + "</td>"
							+ "<td>" + asset.employee.firstName + "</td>"
							+ "<td>" + asset.assignedBy + "</td>"
							+ "<td>" + asset.assignedDate + "</td>"
							+ "<td>" + "<button onclick='updateAssignAsset(" + asset.assetId + ")'>View</button>" + "</td>"
							+ "<td>" + "<button onclick='deleteAssignAsset(" + asset.assetId + ")'>Delete</button>" + "</td>"
							+ "</tr>";
							tbody.append(row);
							index++;	
						}else{
							var row = "<tr> <td colspan='7' style='text-align:center'>No Data Available</td> </tr>";
							tbody.append(row);
						}
						var dropdown = $("#assetId");
					      dropdown.empty();
					      dropdown.append("<option value=''>Select a Asset</option>");
					      data.forEach(function (asset) {
					    	if(asset.status=="Unassigned"){
					    		dropdown.append("<option value='" + asset.assetId + "'>" + asset.assetName + "</option>");	
					    	}
					      });
					});
				},
				error : function(error) {
					console.log(error);
					alert("Error fetching assets");
				}
			});
		}
		$("#assignButton").on("click", function() {
			console.log('add Assign asset');
			var validate = validateForm();
			var formData = new FormData($("#assignAssetForm")[0]);
			var assignAsset = {};
			var employeeId = document.getElementById('employeeId').value;
			formData.forEach(function(value, key) {
				assignAsset[key] = value;
			});
			console.log(assignAsset);
			if (validate) {
				$.ajax({
					url : "/AssignAsset?employeeId="+employeeId,
					method : "POST",
					contentType : "application/json",
					data : JSON.stringify(assignAsset),
					success : function(response) {
						console.log(response);
						$("#addAssignAssetModal").modal("hide");
						fetchAssignedAsset();
						alert(response);
					},
					error : function(error) {
						$("#addAssignAssetModal").modal("hide");
						alert(error);
						console.log(error);
					}
				});
			}
		});
		$("#updateAssignAssetButton").on("click", function() {
			var validate = validateForm();
			var formData = new FormData($("#assignAssetForm")[0]);
			var employeeId = document.getElementById('employeeId').value;
			var assignAsset = {};
			formData.forEach(function(value, key) {
				assignAsset[key] = value;
			});
			console.log("update");
			console.log(assignAsset);
			if (validate) {
				$.ajax({
					url : "/updateAsset?employeeID="+employeeID,
					method : "POST",
					contentType : "application/json",
					data : JSON.stringify(asset),
					success : function(data) {
						console.log(data);
						$("#assignAssetModal").modal("hide");
						fetchAssignedAsset();
						document.getElementById("assignAssetForm").reset();
						alert(data);
					},
					error : function(error) {
						$("#assignAssetModal").modal("hide");
						console.log(error);
						alert(error);
					}
				});
			}
		});
		function deleteAssignAsset(id) {
			console.log(id);
			$.ajax({
				url : "/unassignAsset?id=" + id,
				type : "POST",
				success : function(data) {
					fetchAssignedAsset();
					alert(data);
				},
				error : function(error) {
					console.log(error);
					alert(error);
				}
			});
		}
		function updateAssignAsset(id) {
			console.log("update");
			var row = $("tr[data_id='" + id + "']");
			var assetId = row.find("td:eq(0)").text();			
			var assetName = row.find("td:eq(2)").text();
			var category = row.find("td:eq(3)").text();
			var assetType = row.find("td:eq(4)").text();
			var modelNo = row.find("td:eq(5)").text();
			var serialNo = row.find("td:eq(6)").text();
			var purchasePrice = row.find("td:eq(7)").text();	
			var purchaseDate = row.find("td:eq(8)").text();
			var warrantyDate = row.find("td:eq(9)").text();
			var purchaseType = row.find("td:eq(10)").text();
			var vendorId = row.find("td:eq(11)").text();
			var status = row.find("td:eq(13)").text();
			
			console.log(status);
			$("#assetId").val(assetId);
			$("#assetName").val(assetName);
			$("#category").val(category);
			$("#assetType").val(assetType);
			$("#modelNo").val(modelNo);
			$("#serialNo").val(serialNo);
			$("#purchasePrice").val(purchasePrice);
			$("#purchaseDate").val(purchaseDate);
			$("#warrantyDate").val(warrantyDate);
			$("#purchaseType").val(purchaseType);
			$("#vendorId").val(vendorId);
			$('input[name="status"][value="' + status + '"]').prop('checked',
					true);

			$("#addAssignAssetButton").hide();
			$("#updateAssignAssetButton").show();
			$("#assignAssetModalLabel").hide();
			$("#updateAssignAssetModalLabel").show();
			$("#assignAssetModal").modal("show"); 
		}
	</script>
</body>
</html>