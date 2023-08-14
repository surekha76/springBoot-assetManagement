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
		<button type="button" class="btn btn-primary mt-3 mb-3" id="addAsset">Add
			Asset</button>
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th>No</th>
					<th>Asset Name</th>
					<th>Category</th>
					<th>Asset Type</th>
					<th>Model Number</th>
					<th>Serial Number</th>
					<th>Purchase Price</th>
					<th>Purchase Date</th>
					<th>Warranty Date</th>
					<th>Purchase Type</th>
					<th>Vendor Name</th>
					<th>Status</th>
					<th>View</th>
					<th>Delete</th>
				</tr>
			</thead>
			<tbody id="assetTableBody"></tbody>
		</table>
		<div class="modal fade" id="addAssetModal" tabindex="-1"
			aria-labelledby="addAssetModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="addAssetModalLabel">Add Asset</h5>
						<h5 class="modal-title" id="updateAssetModalLabel"
							style="display: none;">Update Asset</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<div class="modal-body">
							<form id="assetForm">
								<input style="display: none;" type="text" class="form-control"
									id="assetId" name="assetId">
								<div class="row mb-3">
									<div class="col-md-6">
										<label for="assetName" class="form-label">Asset Name</label> <input
											type="text" class="form-control" id="assetName"
											name="assetName" required>
									</div>
									<div class="col-md-6">
										<label for="category">Category</label> <select
											class="form-select" id="category" name="category" required>
											<option value="" selected disabled>Select category</option>
											<option value="hardware">Hardware</option>
											<option value="software">Software</option>
										</select>
									</div>
								</div>
								<div class="row mb-3">
									<div class="col-md-6">
										<label for="assetType">Asset Type</label> <select class="form-select"
											id="assetType" name="assetType" required>
											<option value="" selected disabled>Select asset type</option>
											<option value="laptop">Laptop</option>
											<option value="mobile">Mobile</option>
											<option value="server">Server</option>
										</select>
									</div>
									<div class="col-md-6">
										<label for="modelNo" class="form-label">Model Number</label> <input
											type="text" class="form-control" id="modelNo" name="modelNo"
											required>
									</div>
								</div>
								<div class="row mb-3">
									<div class="col-md-6">
										<label for="serialNo" class="form-label">Serial Number</label>
										<input type="text" class="form-control" id="serialNo"
											name="serialNo" required>
									</div>
									<div class="col-md-6">
										<label for="purchasePrice" class="form-label">Purchase
											Price</label> <input type="text" class="form-control"
											id="purchasePrice" name="purchasePrice" required>
									</div>
								</div>
								<div class="row mb-3">
									<div class="col-md-6">
										<label for="purchaseDate" class="form-label">Purchase
											Date</label> <input type="date" class="form-control"
											id="purchaseDate" name="purchaseDate" onchange="updateWarrantyDateMin()" required>
									</div>
									<div class="col-md-6">
										<label for="warrantyDate" class="form-label">Warranty
											Date</label> <input type="date" class="form-control"
											id="warrantyDate" name="warrantyDate" min=""  required>
									</div>
								</div>
								<div class="row mb-3">
									<div class="col-md-6">
										<label for="purchaseType">Purchase Type</label> <select
											class="form-select" id="purchaseType" name="purchaseType"
											required>
											<option value="" selected disabled>Select Purchase
												Type</option>
											<option value="owned">Owned</option>
											<option value="rental">Rental</option>
											<option value="lease">Lease</option>
										</select>
									</div>
									<div class="col-md-6">
										<label for="vendorId">Vendor</label> <select
											class="form-select" id="vendorId" name="vendorId" required>
										</select>
									</div>
								</div>
								<!-- <div class="row mb-3">
									<div class="col-md-6">
										<label class="form-label">Status</label>
										<div class="form-check">
											<input class="form-check-input" type="radio" name="status"
												id="status1" value="Assigned"> <label
												class="form-check-label" for="status1"> Assigned </label>
										</div>
										<div class="form-check">
											<input class="form-check-input" type="radio" name="status"
												id="status2" value="Unassigned" checked> <label
												class="form-check-label" for="status2"> Unassigned </label>
										</div>
									</div>
								</div> -->
							</form>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" id="addAssetButton">Add</button>
						<button type="button" class="btn btn-primary"
							id="updateAssetButton" style="display: none;">Update</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
		$(document).ready(function() {
			fetchAsset();
			fetchVendorDetails();
			
		});
		$("#addAsset").on("click", function() {
			$("#assetForm")[0].reset();
			$("#addAssetButton").show();
			$("#updateAssetButton").hide();
			$("#addAssetModalLabel").show();
			$("#updateAssetModalLabel").hide();
			$("#addAssetModal").modal("show");
		});
		function validateForm() {
			var assetName = document.getElementById('assetName').value;
			var category = document.getElementById('category').value;
			var assetType = document.getElementById('assetType').value;
			var modelNo = document.getElementById('modelNo').value;
			var serialNo = document.getElementById('serialNo').value;
			var purchasePrice = document.getElementById('purchasePrice').value;
			var purchaseDate = document.getElementById('purchaseDate').value;
			var warrantyDate = document.getElementById('warrantyDate').value;
			var purchaseType = document.getElementById('purchaseType').value;
			var vendorId = document.getElementById('vendorId').value;
			//var status = document.querySelector('input[name="status"]:checked').value;
			if (assetName.trim() === '') {
				alert('Asset Name is required.');
				return false;
			} else if (category.trim() === '') {
				alert('category is required.');
				return false;
			} else if (assetType.trim() === '') {
				alert('Asset Type is required.');
				return false;
			} else if (modelNo.trim() === '') {
				alert('Model Number is required.');
				return false;
			}else if (serialNo.trim() === '') {
				alert('Serial Number is required.');
				return false;
			} else if (purchasePrice.trim() === '') {
				alert('Purchase Price is required.');
				return false;
			} else if (purchaseDate.trim() === '') {
				alert('Purchase Date is required.');
				return false;
			} else if (warrantyDate.trim() === '') {
				alert('Warranty Date is required.');
				return false;
			} else if (purchaseType.trim() === '') {
				alert('Purchase Type is required.');
				return false;
			} else if (vendorId.trim() === '') {
				alert('Vendor Id is required.');
				return false;
			} else {
				return true;
			}
		}
		function fetchVendorDetails(){
			var index = 1;
			$.ajax({
				url : "/getAllVendor",
				type : "GET",
				success : function(data) {
					console.log(data);
					var dropdown = $("#vendorId");
				      dropdown.empty();
				      dropdown.append("<option value=''>Select a Vendor</option>");
				      data.forEach(function (vendor) {
				        dropdown.append("<option value='" + vendor.vendorId + "'>" + vendor.vendorName + "</option>");
				      });
				},
				error : function(error) {
					console.log(error);
					alert("Error in fetching asset details");
				}
			});
		}
		function fetchAsset() {
			var index = 1;
			$.ajax({
				url : "/getAllAsset",
				type : "GET",
				success : function(data) {
					console.log(data);
					var tbody = $("#assetTableBody");
					tbody.empty();
					data.forEach(function(asset) {
						console.log(asset);
						if(asset==""){
							var row = "<tr data_id=" + asset.assetId + ">"
							+ "<td style='display:none;'>" + asset.assetId + "</td>"
							+ "<td>" + index + "</td>"
							+ "<td>" + asset.assetName + "</td>"
							+ "<td>" + asset.category + "</td>"
							+ "<td>" + asset.assetType + "</td>"
							+ "<td>" + asset.modelNo + "</td>"
							+ "<td>" + asset.serialNo + "</td>"
							+ "<td>" + asset.purchasePrice + "</td>"
							+ "<td>" + asset.purchaseDate + "</td>"
							+ "<td>" + asset.warrantyDate + "</td>"
							+ "<td>" + asset.purchaseType + "</td>"
							+ "<td style='display:none;'>" + asset.vendor.vendorId + "</td>"
							+ "<td>" + asset.vendor.vendorName + "</td>"
							+ "<td>" + asset.status + "</td>"
							+ "<td>" + "<button onclick='updateAsset(" + asset.assetId + ")'>View</button>" + "</td>"
							+ "<td>" + "<button onclick='deleteAsset(" + asset.assetId + ")'>Delete</button>" + "</td>"
							+ "</tr>";
							tbody.append(row);
							index++;
						}else{
							var row = "<tr> <td colspan='14' style='text-align:center'>No Data Available</td> </tr>";
							tbody.append(row);
						}
					});
				},
				error : function(error) {
					console.log(error);
					alert("Error fetching assets");
				}
			});
		}
		$("#addAssetButton").on("click", function() {
			console.log('add asset');
			var validate = validateForm();
			var formData = new FormData($("#assetForm")[0]);
			var asset = {};
			var vendorId = document.getElementById('vendorId').value;
			formData.forEach(function(value, key) {
				asset[key] = value;
			});
			console.log(asset);
			if (validate) {
				$.ajax({
					url : "/saveAsset?vendorId="+vendorId,
					method : "POST",
					contentType : "application/json",
					data : JSON.stringify(asset),
					success : function(response) {
						console.log(response);
						$("#addAssetModal").modal("hide");
						fetchAsset();
						alert(response);
					},
					error : function(error) {
						$("#addAssetModal").modal("hide");
						alert(error);
						console.log(error);
					}
				});
			}
		});
		$("#updateAssetButton").on("click", function() {
			var validate = validateForm();
			var formData = new FormData($("#assetForm")[0]);
			var vendorId = document.getElementById('vendorId').value;
			var asset = {};
			formData.forEach(function(value, key) {
				asset[key] = value;
			});
			console.log("update");
			console.log(asset);
			if (validate) {
				$.ajax({
					url : "/updateAsset?vendorId="+vendorId,
					method : "POST",
					contentType : "application/json",
					data : JSON.stringify(asset),
					success : function(data) {
						console.log(data);
						$("#addAssetModal").modal("hide");
						fetchAsset();
						document.getElementById("assetForm").reset();
						alert(data);
					},
					error : function(error) {
						$("#addAssetModal").modal("hide");
						console.log(error);
						alert(error);
					}
				});
			}
		});
		function deleteAsset(id) {
			console.log(id);
			$.ajax({
				url : "/deleteAsset?id=" + id,
				type : "POST",
				success : function(data) {
					fetchAsset();
					alert(data);
				},
				error : function(error) {
					console.log(error);
					alert(error);
				}
			});
		}
		function updateAsset(id) {
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
			//$('input[name="status"][value="' + status + '"]').prop('checked',true);

			$("#addAssetButton").hide();
			$("#updateAssetButton").show();
			$("#addAssetModalLabel").hide();
			$("#updateAssetModalLabel").show();
			$("#addAssetModal").modal("show"); 
		}
		function updateWarrantyDateMin() {
		    var purchaseDate = new Date($("#purchaseDate").val());
		    if (!isNaN(purchaseDate)) {
		    	$("#warrantyDate").val("");
		        var warrantyDate = new Date(purchaseDate);
		        warrantyDate.setFullYear(warrantyDate.getFullYear() + 1);
		        var year = warrantyDate.getFullYear();
		        var month = String(warrantyDate.getMonth() + 1).padStart(2, '0');
			    var day = String(warrantyDate.getDate()).padStart(2, '0');
			    var warrantyDateFormatted = year + "-" + month + "-" + day;
		        console.log(warrantyDateFormatted);
		        $("#warrantyDate").prop("min", warrantyDateFormatted);
		        $("#warrantyDate").prop("disabled", false);
		    } else {
		    	//$("#warrantyDate").val("");
		        $("#warrantyDate").prop("min", "");
		        //$("#warrantyDate").prop("disabled", true);
		    }
		}
	</script>
</body>
</html>