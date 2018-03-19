function initDropoff() {
	
    $(document)
    	.ready(function() {
    		$('#dropoffReservationTable').DataTable();
    		$("#linkDropoff").click(loadUIReservationsForDropoff);	
    		$("#dropoffBtn").click(onLoadDropOffConfirmationModal);	
    		$("#dropoffConfModalConfirmBtn").click(dropoffConfirmed);	    		
    	});
}

function loadUIReservationsForDropoff() {
	hideClerkUIForNewPage();
	$("#dropoffReservation").css('visibility', 'visible');
	
	var jsonData = getDataReservationsForDropoff();	
	
	var table = $('#dropoffReservationTable').DataTable();

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

function onLoadDropOffConfirmationModal() {
	var table = $('#dropoffReservationTable').DataTable();
	var reservationId = $('#dropoffReservationIdTextInput').val();
	
	table.rows().every( function ( rowIdx, tableLoop, rowLoop ) {
	    var data = this.data();
	    if (reservationId == data[0]) {
			var reservation = data[5];	    	
			var tools = data[6];
			var totalDeposit = getTotalDeposit(tools);
			var totalRentalPrice = getTotalRentalPrice(reservation, tools);
	    	$('#dropoffConfModalLabelReservationId').text("Reservation ID: " + data[1]);
	    	$('#dropoffConfModalLabelCustomerName').text("Customer Name: " + data[1]);
	    	$('#dropoffConfModalLabeTotalDeposit').text("Total Deposit: " + totalDeposit);
	    	$('#dropoffConfModalLabelTotalRentalPrice').text("Total Rental Price: " + totalRentalPrice);
	    }
	} );	
}

function dropoffConfirmed() {
	var reservationId = $('#dropoffReservationIdTextInput').val();
	dropoff(reservationId, clerkUsername);
}	

