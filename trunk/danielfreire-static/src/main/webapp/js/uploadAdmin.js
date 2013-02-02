function uploadSet(urlImg) {
	var position = $('#positionFile').val();
	
	$('#iposition'+position).attr('src', urlImg); 
	$('#resetUpload').click();
	
	var division = '[LIN]';
	var divisionCol = '[COL]';
	
	var images = $('#imagesForm').val();
	var image = new Array();
	
	if (images!=undefined && images!=null && images!='') {
		var image = images.split(division);
	}
	
	var finalValue = '';
	
	var isPositionExist = 0;
	if (image.length>0) {
		for (var e=0; e<image.length; e++) {
			var i = image[e].split(divisionCol);
			
			if (i[0]==position) {
				isPositionExist = 1;
			}
		}
	}
	
	if (isPositionExist == 0) {
		if (image.length>0) {
			finalValue = images + division + position + divisionCol + urlImg;
		} else {
			finalValue = position + divisionCol + urlImg;
		}
	} else {
		for (var e=0; e<image.length; e++) {
			var i = image[e].split('-');
			
			if (i[0]!=position) {
				if (finalValue!='') {
					finalValue = finalValue + division + image[e];
				} else {
					finalValue = image[e];
				}
			} else {
				if (finalValue!='') {
					finalValue = finalValue + division + position + divisionCol + urlImg;
				} else {
					finalValue = position + divisionCol + urlImg;
				}
			}
		}
	}
	
	$('#imagesForm').val(finalValue);
	
}