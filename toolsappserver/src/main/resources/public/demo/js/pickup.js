function initPickup() {
	
    $(document)
    	.ready(function() {
    		$('#pickupReservationTable').DataTable();
    		$("#linkPickup").click(loadUIReservationsForPickup);	
    		$("#pickupBtn").click(onLoadPickUpConfirmationModal);	
    		$("#pickupConfModalConfirmBtn").click(pickupConfirmed);	
    	});
}

function loadUIReservationsForPickup() {
	hideClerkUIForNewPage();
	$("#pickupReservation").css('visibility', 'visible');
	
	var jsonData = getDataReservationsForPickup();

	var table = $('#pickupReservationTable').DataTable();
	table.clear();
	for (var i = 0; i < jsonData.length; i++) {
		var r = jsonData[i].reservation;
		var tools = jsonData[i].tools;
		var customer = getDataCustomer(r.customer);
		table.row.add([r.reservationId, customer.firstName + " " + customer.lastName, customer.username, r.startDate, r.endDate, r, tools, customer]).draw();		
	}
	
	table.on('click', 'tr', function () {
		var data = table.row( this ).data();
		var reservation = data[5];
		var tools = data[6];
		var customer = data[7];
		var totalDeposit = getTotalDeposit(tools);
		var totalRentalPrice = getTotalRentalPrice(reservation, tools);

		$('#reservationModal').modal('show');
		$('#reservationModalId').text("Reservation ID: #" + reservation.reservationId);
		$('#reservationModalCustomerName').text("Customer Name: " + customer.firstName + " " + customer.lastName);
		$('#reservationModalTotalDeposit').text("Total Deposit: " + totalDeposit);
		$('#reservationModalTotalRentalPrice').text("Total Rental Price: " + totalRentalPrice);

		var toolsHtml = "";
		for (var c = 0; c < tools.length; c++) {
			var tool = tools[c];
			toolsHtml = toolsHtml + "<div class='row'><div class='col-lg-12'><label>" + tool.subType + "</label></div></div>";
		}
		$('#reservationModalTools').html(toolsHtml);
	} );

}

function onLoadPickUpConfirmationModal() {
	var table = $('#pickupReservationTable').DataTable();
	var reservationId = $('#pickupReservationIdTextInput').val();
	
	table.rows().every( function ( rowIdx, tableLoop, rowLoop ) {
	    var data = this.data();
	    if (reservationId == data[0]) {
			var reservation = data[5];	    	
			var tools = data[6];
			var totalDeposit = getTotalDeposit(tools);
			var totalRentalPrice = getTotalRentalPrice(reservation, tools);
	    	$('#pickupConfModalLabelReservationId').text("Reservation ID: " + data[1]);
	    	$('#pickupConfModalLabelCustomerName').text("Customer Name: " + data[1]);
	    	$('#pickupConfModalLabeTotalDeposit').text("Total Deposit: " + totalDeposit);
	    	$('#pickupConfModalLabelTotalRentalPrice').text("Total Rental Price: " + totalRentalPrice);
	    }
	} );	
}

function pickupConfirmed() {
	var reservationId = $('#pickupReservationIdTextInput').val();
	pickup(reservationId, clerkUsername);
}	

