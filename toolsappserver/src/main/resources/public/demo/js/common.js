function hideClerkUIForNewPage() {
    $("img").hide();
	$("#pickupReservation").css('visibility', 'hidden');    
	$("#dropoffReservation").css('visibility', 'hidden');    
}

function hideCustomerUIForNewPage() {
    $("img").hide();
}

/** 
 * [power-source]+[sub-option]+[sub-type]
 */
function getToolShortDesc(tool) {
	var powerSource = tool.powerSource;	
	if (powerSource.toLowerCase() == "manual") {
		return tool.subOption + " " + tool.subType;
	} else {
		return tool.powerSource + " " + tool.subOption + " " + tool.subType;
	}
}

/**
 * 10-1/2 in. W x 12 in. L 5.6 lb.  Cordless Hammer Drill 36.0V 5.0A 1600 RPM 3000 ft-lb by DEWALT
 * 
 * Manual Tool
 * 	[dimensions]+[weight]+[sub-option]+[sub-type]+ […other descriptors…]+[manufacturer]
 * 
 * Power Tool
 * 	[dimensions]+[weight]+[power-src]+[sub-option]+[sub-type]+[*-ratings]+[manufacturer]
 */
function getToolFullDesc(tool) {
	var powerSource = tool.powerSource;
	var type = tool.type;
	var subType = tool.subType;
	var desc = getToolShortDesc(tool);
	var fulldesc = tool.widthDiameter + " in. W x " + tool.length + " in. L " + desc;
	if (type.toLowerCase() == "powertool") {
		fulldesc = fulldesc + " " + tool.voltRating + "V " +  tool.ampRating + "A " + tool.maxRpmRating + " RPM"
	} 

	// TODO: add other tool properties by subtype
	if (subType == "saw") {
		fulldesc = fulldesc + " " + tool.bladeSize + " inches";
	}
	if (subType == "screwdriver") {
		fulldesc = fulldesc + " " + tool.screwSize + " inches";
	}
	if (subType == "wheelbarrow") {
		fulldesc = fulldesc + " " + tool.handleMaterial + " " + tool.binVolume + " cu ft. " + tool.binMaterial + " " + tool.wheelCount + " wheeled";
	}
	if (subType == "mixer") {
		fulldesc = fulldesc + " " + tool.handleMaterial + " " + tool.motorRating + " HP " + tool.drumSize + " cu ft.";
	}
	if (subType == "socket") {
		fulldesc = fulldesc + " " + tool.driveSize + " inches";
	}
	if (subType == "gun") {
		fulldesc = fulldesc + " " + tool.capacity + " nails/staples " + tool.gaugeRating + " G";
	}
	if (subType == "pruner") {
		fulldesc = fulldesc + " " + tool.handleMaterial + " " + tool.bladeMaterial + " " + tool.bladeLength + " inches";
	}
	if (subType == "striking") {
		fulldesc = fulldesc + " " + tool.handleMaterial + " " + tool.headWeight + " lb";
	}
	
	fulldesc = fulldesc + " by " + tool.manufacturer;
	return fulldesc;	
}

function getDeposit(tool) {
	var purchasePrice = tool.purchasePrice;
	var deposit = parseFloat(((purchasePrice)*0.4).toFixed(2));
	return deposit;
}

function getTotalDeposit(toolsArray) {
	var total = 0;
	for (var i = 0; i < toolsArray.length; i++) {
		var tool = toolsArray[i];
		var deposit = getDeposit(tool);
		total = total + deposit;
	}
	return total;
}

function getRentalPrice(tool) {
	var purchasePrice = tool.purchasePrice;
	var rentalPricePerDay = parseFloat(((purchasePrice)*0.15).toFixed(2));
	return rentalPricePerDay;
}

function getTotalRentalPrice(reservation, toolsArray) {
	var endDate = new Date(reservation.endDate);
	var startDate = new Date(reservation.startDate);
	var numDays = daydiff(startDate, endDate) + 1;

	var total = 0;	
	for (var i = 0; i < toolsArray.length; i++) {
		var tool = toolsArray[i];
		var rentalPricePerDay = getRentalPrice(tool);
		var rental = numDays * rentalPricePerDay;
		total += rental;
	}
	
	return total;
}

function daydiff(first, second) {
	if (first == second) {
		return 1;
	}
	var days = Math.round((second-first)/(1000*60*60*24));	
	if (days == 0) {
		return 1;
	}
	
    return days;
}

function getFullName(customer) {
	if (customer.midName) {
		return customer.firstName + " " + customer.midName + " " + customer.lastName;	
		
	} else {
		return customer.firstName + " " + customer.lastName;	
	}
}

function getAddress(customer) {
	return customer.street + ", " + customer.city + ", " + customer.state + " " + customer.zip;
}
	
function getCell(customer) {
	return getFormattedPhoneNumber(customer.cell);
}

function getHome(customer) {
	return getFormattedPhoneNumber(customer.home);
}

function getWork(customer) {
	return getFormattedPhoneNumber(customer.work);
}

function getPrimaryPhoneNum(customer) {
	if (customer.cell && customer.cell.primary) {
		return getCell(customer);
	}
	if (customer.home && customer.home.primary) {
		return getHome(customer);
	}
	if (customer.work && customer.work.primary) {
		return getWork(customer);
	}
}

function getFormattedPhoneNumber(phone) {
	if (phone) {
		var num = phone.number;
		
		if (phone.areaCode) {
			num = "(" + phone.areaCode + ") " + num; 
		}
		
		if (phone.extension && phone.extension != 0) {
			num = num + " x" + phone.extension;
		}
		
		return num;
		
	} else {
		return "";
	}
}
