function upload(urlImg) {
	var position = $('#positionFile', top.document).val();
	
	$('#iposition'+position, top.document).attr('src', urlImg); 
	$('#resetUpload', top.document).click();
	
	var division = '[LIN]';
	
	var images = $('#imagesForm', top.document).val();
	var image = new Array();
	
	if (images!=undefined && images!=null && images!='') {
		var image = images.split(division);
	}
	
	var finalValue = '';
	
	var isPositionExist = 0;
	if (image.length>0) {
		for (var e=0; e<image.length; e++) {
			var i = image[e].split('-');
			
			if (i[0]==position) {
				isPositionExist = 1;
			}
		}
	}
	
	if (isPositionExist == 0) {
		if (image.length>0) {
			finalValue = images + division + position + "-" + urlImg;
		} else {
			finalValue = position+"-"+urlImg;
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
					finalValue = finalValue + division + position + "-" + urlImg;
				} else {
					finalValue = position+"-"+urlImg;
				}
			}
		}
	}
	
	$('#imagesForm', top.document).val(finalValue);
	
}