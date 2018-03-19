function initAddTool(clerkUsername) {
	
    $(document)
    	.ready(function(){
    		$("input[name='toolType']").change(onTypeChange);	
    		$("#toolSubTypeDropdown").change(onSubTypeChange);	 
    		$("#toolPowerSourceDropdown").change(onPowerSourceChange);	 
    		$("#btnAddAccessory").click(onAddAccessory);	 
    		
    	});
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
		    hidePowerToolSection();
		    toolPowerSource.val("manual");
			break;
		case 'garden' :
		    $("<option />", {value: "digger", text: "Digger"}).appendTo(toolSubTypeDropdown);
		    $("<option />", {value: "pruner", text: "Pruner"}).appendTo(toolSubTypeDropdown);
		    $("<option />", {value: "rakes", text: "Rakes"}).appendTo(toolSubTypeDropdown);
		    $("<option />", {value: "wheelbarrow", text: "Wheelbarrows"}).appendTo(toolSubTypeDropdown);
		    $("<option />", {value: "striking", text: "Striking"}).appendTo(toolSubTypeDropdown);
		    hidePowerToolSection();
		    toolPowerSource.val("manual");
			break;
		case 'ladder' :
		    $("<option />", {value: "stepladder", text: "Straight"}).appendTo(toolSubTypeDropdown);
		    $("<option />", {value: "stepladder", text: "Step"}).appendTo(toolSubTypeDropdown);
		    hidePowerToolSection();
		    toolPowerSource.val("manual");
			break;
		case 'power' :
		    $("<option />", {value: "drill", text: "Drill"}).appendTo(toolSubTypeDropdown);
		    $("<option />", {value: "saw", text: "Saw"}).appendTo(toolSubTypeDropdown);
		    $("<option />", {value: "sander", text: "Sander"}).appendTo(toolSubTypeDropdown);
		    $("<option />", {value: "aircompressor", text: "Air-Compressor"}).appendTo(toolSubTypeDropdown);
		    $("<option />", {value: "mixer", text: "Mixer"}).appendTo(toolSubTypeDropdown);
		    $("<option />", {value: "generator", text: "Generator"}).appendTo(toolSubTypeDropdown);		    
		    showPowerToolSection();
		    toolPowerSource.val("electric");
			break;
		default:
	}	
	onSubTypeChange();
	onPowerSourceChange();
}

function onPowerSourceChange(event) {
	var powerSource = $("#toolPowerSourceDropdown").val();
	if (powerSource == "cordless") {
		$("#addToolCordlessToolSection").css('visibility', 'visible');
	} else {
		$("#addToolCordlessToolSection").css('visibility', 'hidden');
	}
}


function onSubTypeChange(event) {
	var subType = $("#toolSubTypeDropdown").val();
	
	var toolSubOption = $('#toolSubOptionDropdown');
	toolSubOption.empty();
	
	var additionalFieldsSection = $('#addToolAdditionalFields');
	
	// clear/remove all click event handlers
	$("#btnAddTool").off("click");
	
	switch (subType) {
		case 'screwdriver' :
		    $("<option />", {value: "phillips", text: "phillips"}).appendTo(toolSubOption);
		    $("<option />", {value: "hex", text: "hex"}).appendTo(toolSubOption);
		    $("<option />", {value: "torx", text: "torx"}).appendTo(toolSubOption);
		    $("<option />", {value: "slotted", text: "slotted"}).appendTo(toolSubOption);
		    additionalFieldsSection.html(
		    	'<div class="col-sm-12">' +
		    		'<label>Screw Size</label>' +
		    		'<input type="text" id="screwSize"></input>' +
		    	'</div>'
		    );		    
    		$("#btnAddTool").one("click", function(){
    			var tooldata = getGenericToolData();
    			tooldata.screwSize = $("#screwSize").val();
    			addTool(subType, tooldata);
			});
			break;
		case 'socket' :
		    $("<option />", {value: "deep", text: "deep"}).appendTo(toolSubOption);
		    $("<option />", {value: "standard", text: "standard"}).appendTo(toolSubOption);
		    additionalFieldsSection.html(
		    	'<div class="col-sm-12">' +
		    		'<label>Drive Size</label>' +
		    		'<input type="text" id="driveSize"></input>' +
		    	'</div>'
		    );		    
    		$("#btnAddTool").one("click", function(){
    			var tooldata = getGenericToolData();
    			tooldata.driveSize = $("#driveSize").val();
    			addTool(subType, tooldata);
			});
			break;
		case 'ratchet' :
		    $("<option />", {value: "adjustable", text: "adjustable"}).appendTo(toolSubOption);
		    $("<option />", {value: "fixed", text: "fixed"}).appendTo(toolSubOption);
			break;
		case 'pliers' :
		    $("<option />", {value: "needle nose", text: "needle nose"}).appendTo(toolSubOption);
		    $("<option />", {value: "cutting", text: "cutting"}).appendTo(toolSubOption);
		    $("<option />", {value: "crimper", text: "crimper"}).appendTo(toolSubOption);
			break;
		case 'gun' :
		    $("<option />", {value: "nail", text: "nail"}).appendTo(toolSubOption);
		    $("<option />", {value: "stapple", text: "stapple"}).appendTo(toolSubOption);
		    additionalFieldsSection.html(
		    	'<div class="col-sm-12">' +
		    		'<label>Capacity</label>' +
		    		'<input type="text" id="capacity"></input>' +
		    	'</div>' +
		    	'<div class="col-sm-12">' +
		    		'<label>Gauge Rating</label>' +
		    		'<input type="text" id="gaugeRating"></input>' +
	    		'</div>'		    
		    );		    
    		$("#btnAddTool").one("click", function(){
    			var tooldata = getGenericToolData();
    			tooldata.capacity = $("#capacity").val();
    			tooldata.gaugeRating = $("#gaugeRating").val();
    			addTool(subType, tooldata);
			});
			break;
		case 'hammer' :
		    $("<option />", {value: "claw", text: "claw"}).appendTo(toolSubOption);
		    $("<option />", {value: "sledge", text: "sledge"}).appendTo(toolSubOption);
		    $("<option />", {value: "framing", text: "framing"}).appendTo(toolSubOption);
			break;
		case 'digger' :
		    $("<option />", {value: "pointed shovel", text: "pointed shovel"}).appendTo(toolSubOption);
		    $("<option />", {value: "flat shovel", text: "flat shovel"}).appendTo(toolSubOption);
		    $("<option />", {value: "scoop shovel", text: "scoop shovel"}).appendTo(toolSubOption);
		    $("<option />", {value: "edger", text: "edger"}).appendTo(toolSubOption);
		    break;
		case 'pruner' :
		    $("<option />", {value: "sheer", text: "sheer"}).appendTo(toolSubOption);
		    $("<option />", {value: "loppers", text: "loppers"}).appendTo(toolSubOption);
		    $("<option />", {value: "hedge", text: "hedge"}).appendTo(toolSubOption);
		    additionalFieldsSection.html(
		    	'<div class="col-sm-12">' +
		    		'<label>Handle Material</label>' +
		    		'<input type="text" id="handleMaterial"></input>' +
		    	'</div>' +
		    	'<div class="col-sm-12">' +
		    		'<label>Blade Material</label>' +
		    		'<input type="text" id="bladeMaterial"></input>' +
	    		'</div>' +		    
		    	'<div class="col-sm-12">' +
		    		'<label>Blade Length</label>' +
		    		'<input type="text" id="bladeLength"></input>' +
	    		'</div>' 		    
		    );		    
    		$("#btnAddTool").one("click", function(){
    			var tooldata = getGenericToolData();
    			tooldata.handleMaterial = $("#handleMaterial").val();
    			tooldata.bladeMaterial = $("#bladeMaterial").val();
    			tooldata.bladeLength = $("#bladeLength").val();
    			addTool(subType, tooldata);
			});
			break;
		case 'rakes' :
		    $("<option />", {value: "leaf", text: "leaf"}).appendTo(toolSubOption);
		    $("<option />", {value: "landscaping", text: "landscaping"}).appendTo(toolSubOption);
		    $("<option />", {value: "rock", text: "rock"}).appendTo(toolSubOption);
			break;
		case 'wheelbarrow' :
		    $("<option />", {value: "1-wheel", text: "1-wheel"}).appendTo(toolSubOption);
		    $("<option />", {value: "2-wheel", text: "2-wheel"}).appendTo(toolSubOption);
		    additionalFieldsSection.html(
		    	'<div class="col-sm-12">' +
		    		'<label>Handle Material</label>' +
		    		'<input type="text" id="handleMaterial"></input>' +
		    	'</div>' +
		    	'<div class="col-sm-12">' +
		    		'<label>Bin Volume</label>' +
		    		'<input type="text" id="binVolume"></input>' +
	    		'</div>' +		    
		    	'<div class="col-sm-12">' +
		    		'<label>Bin Material</label>' +
		    		'<input type="text" id="binMaterial"></input>' +
	    		'</div>' +		    
		    	'<div class="col-sm-12">' +
		    		'<label>Wheel Count</label>' +
		    		'<input type="text" id="wheelCount"></input>' +
	    		'</div>' 		    	
		    );		    
    		$("#btnAddTool").one("click", function(){
    			var tooldata = getGenericToolData();
    			tooldata.handleMaterial = $("#handleMaterial").val();
    			tooldata.binVolume = $("#binVolume").val();
    			tooldata.binMaterial = $("#binMaterial").val();
    			tooldata.wheelCount = $("#wheelCount").val();
    			addTool(subType, tooldata);
			});
			break;
		case 'striking' :
		    $("<option />", {value: "bar pry", text: "bar pry"}).appendTo(toolSubOption);
		    $("<option />", {value: "rubber mallet", text: "rubber mallet"}).appendTo(toolSubOption);
		    $("<option />", {value: "tamper", text: "tamper"}).appendTo(toolSubOption);
		    $("<option />", {value: "pick axe", text: "pick axe"}).appendTo(toolSubOption);
		    $("<option />", {value: "single bit axe", text: "single bit axe"}).appendTo(toolSubOption);
		    additionalFieldsSection.html(
		    	'<div class="col-sm-12">' +
		    		'<label>Handle Material</label>' +
		    		'<input type="text" id="handleMaterial"></input>' +
		    	'</div>' +
		    	'<div class="col-sm-12">' +
		    		'<label>Head Weight</label>' +
		    		'<input type="text" id="headWeight"></input>' +
	    		'</div>'		    
		    );		    
    		$("#btnAddTool").one("click", function(){
    			var tooldata = getGenericToolData();
    			tooldata.handleMaterial = $("#handleMaterial").val();
    			tooldata.headWeight = $("#headWeight").val();
    			addTool(subType, tooldata);
			});
			break;
		case 'straightladder' :
		    $("<option />", {value: "rigid", text: "rigid"}).appendTo(toolSubOption);
		    $("<option />", {value: "telescoping", text: "telescoping"}).appendTo(toolSubOption);
			break;
		case 'stepladder' :
		    $("<option />", {value: "folding", text: "folding"}).appendTo(toolSubOption);
		    $("<option />", {value: "multi-position", text: "multi-position"}).appendTo(toolSubOption);
			break;
		case 'drill' :
		    $("<option />", {value: "driver", text: "driver"}).appendTo(toolSubOption);
		    $("<option />", {value: "hammer", text: "hammer"}).appendTo(toolSubOption);
			//private boolean adjustableClutch;
			//private int minTorqueRating;
			//private int maxTorqueRating;
			break;
		case 'saw' :
		    $("<option />", {value: "circular", text: "circular"}).appendTo(toolSubOption);
		    $("<option />", {value: "reciprocating", text: "reciprocating"}).appendTo(toolSubOption);
		    $("<option />", {value: "jig", text: "jig"}).appendTo(toolSubOption);		    
		    additionalFieldsSection.html(
		    	'<div class="col-sm-12">' +
		    		'<label>Blade Size</label>' +
		    		'<input type="text" id="bladeSize"></input>' +
		    	'</div>'
		    );		   
    		$("#btnAddTool").one("click", function(){
    			var tooldata = getGenericToolData();
    			tooldata.bladeSize = $("#bladeSize").val();
    			addTool(subType, tooldata);
			});
			break;
		case 'sander' :
		    $("<option />", {value: "finish", text: "finish"}).appendTo(toolSubOption);
		    $("<option />", {value: "sheet", text: "sheet"}).appendTo(toolSubOption);
		    $("<option />", {value: "belt", text: "belt"}).appendTo(toolSubOption);
		    $("<option />", {value: "random orbital", text: "random orbital"}).appendTo(toolSubOption);
		    break;
		case 'aircompressor' :
		    $("<option />", {value: "reciprocating", text: "reciprocating"}).appendTo(toolSubOption);
			break;
		case 'mixer' :
		    $("<option />", {value: "concrete", text: "concrete"}).appendTo(toolSubOption);
			//private int motorRating;
			//private double drumSize;
		    additionalFieldsSection.html(
		    	'<div class="col-sm-12">' +
		    		'<label>Motor Rating</label>' +
		    		'<input type="text" id="motorRating"></input>' +
		    	'</div>' +
		    	'<div class="col-sm-12">' +
		    		'<label>Drum Size</label>' +
		    		'<input type="text" id="drumSize"></input>' +
	    		'</div>'
		    );		    
    		$("#btnAddTool").one("click", function(){
    			var tooldata = getGenericToolData();
    			tooldata.motorRating = $("#motorRating").val();
    			tooldata.drumSize = $("#drumSize").val();
    			addTool(subType, tooldata);
			});
			break;
		case 'generator' :
		    $("<option />", {value: "electric", text: "electric"}).appendTo(toolSubOption);
			break;
		default:
	}	
		
}

function getGenericToolData() {
	var tooldata = {};
	tooldata.type = $("input[name='toolType']:checked").val();
	tooldata.subType = $("#toolSubTypeDropdown").val();
	tooldata.subOption = $("#toolSubOptionDropdown").val();
	tooldata.purchasePrice = $("#toolPurchasePrice").val();
	tooldata.powerSource = $("#toolPowerSourceDropdown").val();
	tooldata.widthDiameter = $("#toolWidth").val();
	tooldata.length = $("#toolLength").val();
	tooldata.manufacturer = $("#toolManufacturer").val();
	tooldata.material = $("#toolMaterial").val();
	
	if (tooldata.type == "power") {
		tooldata.ampRating = $("#ampRating").val();
		tooldata.voltRating = $("#voltRating").val();
		tooldata.maxRpmRating = $("#maxRpmRating").val();
		tooldata.minRpmRating = $("#minRpmRating").val();
		
		var accessories = new Array();
		var accessory = {};
		var i = 0;
		$('input', $('#addToolAccessorySection')).each(function () {
			if (i == 0) {
				 accessory.quantity = $(this).val();
				 i++;
			} else {
				accessory.description = $(this).val();
				accessories.push(accessory);
				accessory = {};
				i = 0;
			}
		});
		tooldata.accessories = accessories;
		
		if (tooldata.powerSource == "cordless") {
			var b = {};
			b.batteryType = $("#toolBatteryTypeDropdown").val();
			b.voltRating = $("#batteryVoltRating").val();
			b.quantity = $("#batteryQuantity").val();
			tooldata.battery = b;
		}
	}

	
	return tooldata;
}

function hidePowerToolSection() {
	$("#addToolPowerToolSection1").css('visibility', 'hidden');
	$("#addToolPowerToolSection2").css('visibility', 'hidden');	
	$("#addToolPowerToolSection3").css('visibility', 'hidden');	
}

function showPowerToolSection() {
	$("#addToolPowerToolSection1").css('visibility', 'visible');
	$("#addToolPowerToolSection2").css('visibility', 'visible');	
	$("#addToolPowerToolSection3").css('visibility', 'visible');	
}

function onAddAccessory() {
	$('#addToolAccessorySection').append(
		'<div class="row">' +
			'<div class="col-sm-4">' +
				'<label>Accessory Quantity</label>' +
				'<input type="text"></input>' + 
			'</div>' + 
			'<div class="col-sm-4">' + 
				'<label>Accessory Description</label>' +
				'<input type="text"></input>' + 
			'</div>' + 
		'</div>'
	);
}