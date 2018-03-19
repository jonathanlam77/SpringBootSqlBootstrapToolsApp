function initViewProfile() {
	
    $(document)
    	.ready(function() {
    		$("#linkViewProfile").click(onLoadViewProfileModal);	
    	});
}

function onLoadViewProfileModal() {
	
	var jsonData = getDataReservationsAndToolsForGivenCustomer(customerUsername);
	
	var customer = getDataCustomer(customerUsername);
	
	$('#viewProfileModalEmail').text("Email: " + customer.email);
	$('#viewProfileModalFullName').text("Full Name: " + getFullName(customer));
	$('#viewProfileModalHomePhone').text("Home Phone: " + getHome(customer));
	$('#viewProfileModalWorkPhone').text("Work Phone: " + getWork(customer));
	$('#viewProfileModalCellPhone').text("Cell Phone: " + getCell(customer));
	$('#viewProfileModalAddress').text("Address: " + getAddress(customer));
	
	var table = $('#viewProfileReservationTable').DataTable();
	table.clear();
	for (var i = 0; i < jsonData.length; i++) {
		var r = jsonData[i].reservation;

		var tools = jsonData[i].tools;
		var toolsString = "";
		for (var c = 0; c < tools.length; c++) {
			var tool = tools[c];
			toolsString = toolsString + getToolShortDesc(tool) + ", ";
		}
		toolsString = toolsString.replace(/,\s*$/, "");
				
		var startDate = new Date(r.startDate);
		var endDate = new Date( r.endDate);
		var numDays = daydiff(startDate, endDate);
		
		var totalDeposit = getTotalDeposit(tools);
		var totalRentalPrice = getTotalRentalPrice(r, tools);
		
		table.row.add([r.reservationId, toolsString, r.startDate, r.endDate, r.pickupClerk, r.dropoffClerk, numDays, totalDeposit, totalRentalPrice]).draw();		
	}
}


