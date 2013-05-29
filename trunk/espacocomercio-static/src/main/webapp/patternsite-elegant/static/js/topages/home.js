$(function() {
	
	loadCarrousel();
	
});

function loadCarrousel() {
	$.getJSON('../data/home/topproducts.json?tk='+new Date().getTime(), function(data) {
		var html = '';
		
		var i=0;
		$.each(data, function(key, val) {
			
			if (i==0) {
				html += '<div class="da-slide da-slide-fromright da-slide-current">';
			} else {
				html += '<div class="da-slide da-slide-toleft">';
			}
			html += '<h2>'+val.name+'</h2>';
			html += '<p>'+val.introduction+'</p>';
			html += '<a href="../product/'+val.keyUrl+'.html" class="da-link">Saber mais</a>';
			html += '<div class="da-img">';
			html += '<img src="'+val.images.split('[COL]')[1]+'" alt="'+val.introduction+'" title="'+val.introduction+'" width="200">';
			html += '</div>';
			html += '</div>';
			
			i++;
		});
		
		$('div#da-slider').html(html);
		
		$('#da-slider').cslider({
			autoplay	: true,
			interval : 6000
		});
	});
}