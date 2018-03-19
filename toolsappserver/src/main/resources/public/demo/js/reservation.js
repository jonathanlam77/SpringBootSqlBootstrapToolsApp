function initReservations() {
	
    $(document)
		.ready(function() {
			$("#linkCheckToolAvail").click(loadUICheckReservations);	
			$("#linkMakeReservation").click(loadUIMakeReservations);	
			$("#btnAddToolToReservation").click(onAddToolToReservation);	
			$("#btnSearch").click(onExecuteSearch);	
			$("#btnCalcTotal").click(onReserveToolsConfirmation);	
			$("#btnReserveTools").click(onReserveTools);	
			$("input[name='toolType']").change(onTypeChange);	
    		$("#toolSubTypeDropdown").change(onSubTypeChange);	 
    		$("#toolPowerSourceDropdown").change(onPowerSourceChange);	 
    	    $('#availToolsForRentTable').DataTable( {
    	        'columnDefs': [{
    	            'targets': 0,
    	            'searchable':false,
    	            'orderable':false,
    	            'className': 'dt-body-center',
    	            'render': function (data, type, full, meta){
    	            	var toolNumber = full[0];
    	            	var output = '<input type="checkbox" value="' + full[0] + '"/>';
    	                return output;
    	            }
    	         }],
    	        order: [[ 1, 'asc' ]]
    	    } );
		});
	
}

function loadUICheckReservations() {
	hideCustomerUIForNewPage();
	$("#searchReservation").css('visibility', 'visible');
	$("#toolsAddedToReservation").css('visibility', 'hidden');
	
}

function loadUIMakeReservations() {
	hideCustomerUIForNewPage();
	$("#searchReservation").css('visibility', 'visible');
	$("#toolsAddedToReservation").css('visibility', 'visible');
}

function onExecuteSearch() {
	var type = $("input[name='toolType']:checked").val();
	var subType = $("#toolSubTypeDropdown").val();
	var subOption = $("#searchSubOption").val();
	var powerSource = $("#toolPowerSourceDropdown").val();
	var startDate = $("#searchStartDate").val();
	var endDate = $("#searchEndDate").val();

	var patt = new RegExp("^[0-9]{4}\-[0-9]{2}\-[0-9]{2}$");
	if (!startDate) {
		var res = patt.test(startDate);
		if (!res) {
			alert("Start date has incorrect format");
			return;
		}
	}
	if (!endDate) {
		var res = patt.test(endDate);
		if (!res) {
			alert("End date has incorrect format");
			return;
		}
	}
	
	var jsonData = getDataSearchToolsForRent(type, subType, subOption, powerSource, startDate, endDate);	
	
	var table = $('#availToolsForRentTable').DataTable();	
	table.clear();
	for (var i = 0; i < jsonData.length; i++) {
		var tool = jsonData[i];
		if (tool) {
			table.row.add([tool.toolNumber, tool.toolNumber, getToolShortDesc(tool), getRentalPrice(tool), getDeposit(tool), tool]).draw();		
		}
	}	
	
	table.on('click', 'td', function () {
		
		if (this.lastChild.type == "checkbox") {
			return;
		}

		var data = table.row(this).data();
		var tool = data[5];
		// {"toolNumber":11,"type":"electric","subType":"Saw","subOption":"jig","purchasePrice":20.4,"powerSource":"electric","widthDiameter":16.0,"length":10.0,"manufacturer":"rona","material":"plastic","ampRating":50.0,"voltRating":5.0,"maxRpmRating":4,"minRpmRating":5,"battery":{"toolNumber":0,"batteryType":"LiIo","voltRating":99.0,"quantity":3},"accessories":[{"toolNumber":11,"description":"my test accessory","quantity":4},{"toolNumber":11,"description":"ta","quantity":1}],"bladeSize":15.5}
		
		$('#additionalToolDetailsModal').modal('show');
		$('#additionalToolDetailsModalToolId').text("Tool ID: " + tool.toolNumber);
		$('#additionalToolDetailsModalToolType').text("Tool Type: " + tool.type);
		$('#additionalToolDetailsModalShortDesc').text("Short Description: " + getToolShortDesc(tool));
		$('#additionalToolDetailsModalFullDesc').text("Full Description: " + getToolFullDesc(tool));
		$('#additionalToolDetailsModalDepositPrice').text("Deposit Price: $" + getDeposit(tool));
		$('#additionalToolDetailsModalRentalPrice').text("Rental Price: $" + getRentalPrice(tool));
		
		// "accessories":[{"toolNumber":11,"description":"my test accessory","quantity":4},{"toolNumber":11,"description":"ta","quantity":1}]
		var accessoryString = "";
		for (var i = 0; i < tool.accessories.length; i++) {
			var a = tool.accessories[i];
			accessoryString = a.quantity + " " + a.description + ","
		}
		accessoryString = accessoryString.replace(/,\s*$/, "");
		
		$('#additionalToolDetailsModalAccessories').text("Accessories: " + accessoryString);
	} );
	
}

function onAddToolToReservation() {
	var availToolsForRentTable = $('#availToolsForRentTable').DataTable();	
	var addedTools = {};
	availToolsForRentTable.$('input[type="checkbox"]').each(function(){
        if(this.checked){
          addedTools[this.value] = this.value;
        }
    });     
    
	var addedToolsTable = $('#addedToolsTable').DataTable();	
	addedToolsTable.clear();
	availToolsForRentTable.rows().every( function ( rowIdx, tableLoop, rowLoop ) {
	    var data = this.data();
	    if (addedTools.hasOwnProperty(data[0])) {
	    	var tool = data[5];
	    	addedToolsTable.row.add([tool.toolNumber, getToolShortDesc(tool), getRentalPrice(tool), getDeposit(tool), tool]).draw();		
	    }
	} );	
    
}

function onReserveToolsConfirmation() {
	
	var addedToolsTable = $('#addedToolsTable').DataTable();	
	var reservationConfirmationToolsTable = $('#reservationConfirmationToolsTable').DataTable();
	reservationConfirmationToolsTable.clear();
	var totalDepositPrice = 0;
	var totalRentalPrice = 0;
	
	addedToolsTable.rows().every( function ( rowIdx, tableLoop, rowLoop ) {
	    var data = this.data();
    	var tool = data[4];
    	var depositPrice = getDeposit(tool);
    	var rentalPrice = getRentalPrice(tool);
    	
    	totalDepositPrice = totalDepositPrice + depositPrice;
    	totalRentalPrice = totalRentalPrice + rentalPrice;
    	
    	reservationConfirmationToolsTable.row.add([tool.toolNumber, getToolShortDesc(tool), rentalPrice, depositPrice]).draw();		
	} );	
	
	var start = $('#searchStartDate').val();
	var end = $('#searchEndDate').val();
	var startDate = new Date(start);
	var endDate = new Date(end);
	var numDays = daydiff(startDate, endDate);
	
	$('#reservationConfirmationModalReservationDates').text("Reservation Dates: " + start + " - " + end);
	$('#reservationConfirmationModalNumberDays').text("Number of Days Rented: " + numDays);
	$('#reservationConfirmationModalTotalDepositPrice').text("Total Deposit Price: $" + totalDepositPrice);
	$('#reservationConfirmationModalTotalRentalPrice').text("Total Rental Price: $" + totalRentalPrice);
}

function onReserveTools() {
	var start = $('#searchStartDate').val();
	var end = $('#searchEndDate').val();
	var reservationConfirmationToolsTable = $('#reservationConfirmationToolsTable').DataTable();
	var toolIds = new Array();
	reservationConfirmationToolsTable.rows().every( function ( rowIdx, tableLoop, rowLoop ) {
	    var data = this.data();
		toolIds.push(data[0]);
	});
	reserveTools(customerUsername, start, end, toolIds);
}

function onTypeChange(toolSubOptionDropdown) {
	var toolType = $(this).val();

	var toolSubTypeDropdown = $('#toolSubTypeDropdown');	
	toolSubTypeDropdown.empty();
	
	var toolPowerSource = $('#toolPowerSourceDropdown');
	
	switch (toolType) {
		case 'hand' :
		    $("<option />", {value: "screwdriver", text: "Screwdriver"}).appendTo(toolSubTypeDropdown);
		    $("<option />", {value: "socket", text: "Socket"}).appendTo(toolSubTypeDropdown);
		    $("<option />", {value: "ratchet", text: "Ratchet"}).appendTo(toolSubTypeDropdown);
		    $("<option />", {value: "pliers", text: "Pliers"}).appendTo(toolSubTypeDropdown);
		    $("<option />", {value: "gun", text: "Gun"}).appendTo(toolSubTypeDropdown);
		    $("<option />", {value: "hammer", text: "Hammer"}).appendTo(toolSubTypeDropdown);
		    toolPowerSource.val("manual");
			break;
		case 'garden' :
		    $("<option />", {value: "digger", text: "Digger"}).appendTo(toolSubTypeDropdown);
		    $("<option />", {value: "pruner", text: "Pruner"}).appendTo(toolSubTypeDropdown);
		    $("<option />", {value: "rakes", text: "Rakes"}).appendTo(toolSubTypeDropdown);
		    $("<option />", {value: "wheelbarrow", text: "Wheelbarrows"}).appendTo(toolSubTypeDropdown);
		    $("<option />", {value: "striking", text: "Striking"}).appendTo(toolSubTypeDropdown);
		    toolPowerSource.val("manual");
			break;
		case 'ladder' :
		    $("<option />", {value: "stepladder", text: "Straight"}).appendTo(toolSubTypeDropdown);
		    $("<option />", {value: "stepladder", text: "Step"}).appendTo(toolSubTypeDropdown);
		    toolPowerSource.val("manual");
			break;
		case 'power' :
		    $("<option />", {value: "drill", text: "Drill"}).appendTo(toolSubTypeDropdown);
		    $("<option />", {value: "saw", text: "Saw"}).appendTo(toolSubTypeDropdown);
		    $("<option />", {value: "sander", text: "Sander"}).appendTo(toolSubTypeDropdown);
		    $("<option />", {value: "aircompressor", text: "Air-Compressor"}).appendTo(toolSubTypeDropdown);
		    $("<option />", {value: "mixer", text: "Mixer"}).appendTo(toolSubTypeDropdown);
		    $("<option />", {value: "generator", text: "Generator"}).appendTo(toolSubTypeDropdown);		    
		    toolPowerSource.val("electric");
			break;
		default:
	}	
	onSubTypeChange();
	onPowerSourceChange();
}

function onPowerSourceChange(event) {
	var powerSource = $("#toolPowerSourceDropdown").val();
}


function onSubTypeChange(event) {
	var subType = $("#toolSubTypeDropdown").val();
}

