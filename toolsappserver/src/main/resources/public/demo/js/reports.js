function initReports() {
	
    $(document)
    	.ready(function() {
    		$("#linkClerkReport").click(onLoadClerkReportModal);	
    		$("#linkCustomerReport").click(onLoadCustomerReportModal);	
    		$("#linkToolReport").click(onLoadToolReportModal);	
    	});
}

function onLoadClerkReportModal() {
	
	var jsonData = getDataClerkReport();
	//	[{"clerk":{"username":"c2","email":"j@gmail.com","password":"jl","firstName":"jon","midName":null,"lastName":"lam","employeeNumber":"123","dateOfHire":"2017-03-30"},"numPickups":1,"numDropoffs":0,"total":1}]

	var table = $('#clerkReportTable').DataTable();
	table.clear();
	for (var i = 0; i < jsonData.length; i++) {
		var item = jsonData[i];
		var clerk = item.clerk;
		table.row.add([clerk.username, clerk.firstName, clerk.midName, clerk.lastName, clerk.email, clerk.dateOfHire, item.numPickups, item.numDropoffs, item.total, item]).draw();		
	}
}

function onLoadCustomerReportModal() {
	var jsonData = getDataCustomerReport();
	// [{"customer":{"username":"jlam1","email":"rick@gmail.com","password":"123abc","firstName":"jon","midName":"dave","lastName":"lam","cell":null,"home":{"phoneNumberId":9053788888,"areaCode":905,"number":3788888,"extension":0,"primary":true},"work":null,"street":"100 forest rd","city":"toronto","state":"on","zip":"90210","creditCard":{"number":5019888177776666,"name":"jon","month":1,"year":1977,"cvc":123}},"totalReservations":1,"totalToolsRented":1}]

	var table = $('#customerReportTable').DataTable();
	table.clear();
	for (var i = 0; i < jsonData.length; i++) {
		var item = jsonData[i];
		var customer = item.customer;		
		table.row.add([customer.username, customer.firstName, customer.midName, customer.lastName, customer.email, getPrimaryPhoneNum(customer), item.totalReservations, item.totalToolsRented]).draw();		
	}
}

function onLoadToolReportModal() {
	var jsonData = getDataToolReport();
	// [{"tool":{"toolNumber":1,"type":"PowerTool","subType":"Saw","subOption":"jig","purchasePrice":20.4,"powerSource":"electric","widthDiameter":16.0,"length":10.0,"manufacturer":"rona","material":"plastic","ampRating":50.0,"voltRating":5.0,"maxRpmRating":4,"minRpmRating":5,"battery":{"toolNumber":0,"batteryType":"LiIo","voltRating":99.0,"quantity":3},"accessories":[{"toolNumber":1,"description":"abc","quantity":4},{"toolNumber":1,"description":"axc","quantity":2}],"bladeSize":15.5},"rentalProfit":27.54,"totalCost":20.4,"totalProfit":7.14,"available":true},{"tool":{"toolNumber":2,"type":"manual","subType":"Screwdriver","subOption":"hand","purchasePrice":110.5,"powerSource":"manual","widthDiameter":15.65,"length":1.23,"manufacturer":"dwalt","material":"wood","screwSize":7.69},"rentalProfit":149.17,"totalCost":110.5,"totalProfit":38.67,"available":true},{"tool":{"toolNumber":3,"type":"manual","subType":"Screwdriver","subOption":"hand","purchasePrice":110.5,"powerSource":"manual","widthDiameter":15.65,"length":1.23,"manufacturer":"dwalt","material":"wood","screwSize":7.69},"rentalProfit":0.0,"totalCost":110.5,"totalProfit":-110.5,"available":true},{"tool":{"toolNumber":4,"type":"manual","subType":"Screwdriver","subOption":"hand","purchasePrice":110.5,"powerSource":"manual","widthDiameter":15.65,"length":1.23,"manufacturer":"dwalt","material":"wood","screwSize":7.69},"rentalProfit":165932.32,"totalCost":110.5,"totalProfit":165711.32,"available":true},{"tool":{"toolNumber":5,"type":"manual","subType":"Screwdriver","subOption":"hand","purchasePrice":110.5,"powerSource":"manual","widthDiameter":15.65,"length":1.23,"manufacturer":"dwalt","material":"wood","screwSize":7.69},"rentalProfit":0.0,"totalCost":110.5,"totalProfit":-110.5,"available":true},{"tool":{"toolNumber":6,"type":"manual","subType":"Screwdriver","subOption":"hand","purchasePrice":110.5,"powerSource":"manual","widthDiameter":15.65,"length":1.23,"manufacturer":"dwalt","material":"wood","screwSize":7.69},"rentalProfit":149.17,"totalCost":110.5,"totalProfit":38.67,"available":true}]
	
	var table = $('#toolReportTable').DataTable();
	table.clear();
	for (var i = 0; i < jsonData.length; i++) {
		var item = jsonData[i];
		var tool = item.tool;
		table.row.add([tool.toolNumber, getToolShortDesc(tool),"$" + item.rentalProfit,"$" + item.totalCost,"$" + item.totalProfit]).draw();				
	}
}	

