function getDataCustomer(customerId) {
    var jsonData;
    $.ajax({
        type: "GET",
        cache: false,
        async: false,
        url: "/customer/" + customerId,
    }).done(function (result) {
    	jsonData = result;
        return jsonData;
    }).fail(function (xhr, result, status) {
        alert('getDataCustomer ' + xhr.statusText + ' ' + xhr.responseText + ' ' + xhr.status);
    });
    return jsonData;
}

function getDataReservationsAndToolsForGivenCustomer(customerId) {
    var jsonData;
    $.ajax({
        type: "GET",
        cache: false,
        async: false,
        url: "/reservation/tool/?customer=" + customerId,
    }).done(function (result) {
    	jsonData = result;
        return jsonData;
    }).fail(function (xhr, result, status) {
        alert('getDataReservationsAndToolsForGivenCustomer ' + xhr.statusText + ' ' + xhr.responseText + ' ' + xhr.status);
    });
    return jsonData;
}


function getDataReservationsForPickup() {
    var jsonData;
    $.ajax({
        type: "GET",
        cache: false,
        async: false,
        url: "/reservation/tool/?pickup=true",
    }).done(function (result) {
    	jsonData = result;
        return jsonData;
    }).fail(function (xhr, result, status) {
        alert('getReservationsForPickup ' + xhr.statusText + ' ' + xhr.responseText + ' ' + xhr.status);
    });
    return jsonData;
}

function getDataReservationsForDropoff() {
    var jsonData;
    $.ajax({
        type: "GET",
        cache: false,
        async: false,
        url: "/reservation/tool/?dropoff=true",
    }).done(function (result) {
    	jsonData = result;
        return jsonData;
    }).fail(function (xhr, result, status) {
        alert('getDataReservationsForDropoff ' + xhr.statusText + ' ' + xhr.responseText + ' ' + xhr.status);
    });
    return jsonData;
}

function addTool(toolSubType, toolData) {
	$.post("/tool/" + toolSubType + "/", 
			"data=" + JSON.stringify(toolData))
   	.done(function() {
   	    alert( "Tool successfully added." );
    })
    .fail(function() {
      alert( "Error" );
    })
    .always(function() {
    });
}

function pickup(reservation_id, clerk) {
	$.post("/reservation/" + reservation_id + "/pickupclerk/" +  clerk + "/")
   	.done(function() {
   	    alert( "Tool successfully picked up." );
    })
    .fail(function() {
      alert( "Error" );
    })
    .always(function() {
    });
}

function dropoff(reservation_id, clerk) {
	$.post("/reservation/" + reservation_id + "/dropoffclerk/" +  clerk + "/")
   	.done(function() {
   	    alert( "Tool successfully dropped off." );
    })
    .fail(function() {
      alert( "Error" );
    })
    .always(function() {
    });
}

function getDataSearchToolsForRent(type, subType, subOptionKeyword, powerSource, startDate, endDate) {
	
	var params = {type:type, subType:subType, subOptionKeyword:subOptionKeyword, powerSource:powerSource, startDate:startDate, endDate:endDate};00000
	var queryString = $.param(params);
	var url = "/tool/?" + queryString;
	
    var jsonData;
    $.ajax({
        type: "GET",
        cache: false,
        async: false,
        url: url,
    }).done(function (result) {
    	jsonData = result;
        return jsonData;
    }).fail(function (xhr, result, status) {
        alert('getDataSearchToolsForRent ' + xhr.statusText + ' ' + xhr.responseText + ' ' + xhr.status);
    });
    return jsonData;
}

function reserveTools(customer, startDate, endDate, toolIds) {
	
	var reservationData = {};
	reservationData.reservation = {
		customer: customer,
		startDate: startDate,
		endDate: endDate
	}
	reservationData.toolIDs = toolIds;
	
	$.post("/reservation/tool/", 
			"data=" + JSON.stringify(reservationData))
   	.done(function() {
   	    alert( "Tools reserved." );
    })
    .fail(function() {
      alert( "Error" );
    })
    .always(function() {
    });
}

function getDataClerkReport() {
	
    var jsonData;
    $.ajax({
        type: "GET",
        cache: false,
        async: false,
        url: "/clerk/report/",
    }).done(function (result) {
    	jsonData = result;
        return jsonData;
    }).fail(function (xhr, result, status) {
        alert('getDataClerkReport ' + xhr.statusText + ' ' + xhr.responseText + ' ' + xhr.status);
    });
    return jsonData;
}

function getDataCustomerReport() {
	
    var jsonData;
    $.ajax({
        type: "GET",
        cache: false,
        async: false,
        url: "/customer/report/",
    }).done(function (result) {
    	jsonData = result;
        return jsonData;
    }).fail(function (xhr, result, status) {
        alert('getDataCustomerReport ' + xhr.statusText + ' ' + xhr.responseText + ' ' + xhr.status);
    });
    return jsonData;	
}

function getDataToolReport() {

    var jsonData;
    $.ajax({
        type: "GET",
        cache: false,
        async: false,
        url: "/tool/report/",
    }).done(function (result) {
    	jsonData = result;
        return jsonData;
    }).fail(function (xhr, result, status) {
        alert('getDataToolReport ' + xhr.statusText + ' ' + xhr.responseText + ' ' + xhr.status);
    });
    return jsonData;
}