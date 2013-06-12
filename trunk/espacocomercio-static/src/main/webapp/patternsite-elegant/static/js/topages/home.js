$(function() {
	
	loadCarrousel();
	loadListProducts();
	
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
			html += '<p>'+val.introduction+'<br>';
			html += '<span class="text-warning" style="font-size: 24px;">R$ '+convertMoeda(val.unityvalue)+'</span></p>';
			html += '<a href="../product/'+val.keyUrl+'.html" class="da-link">Saber mais</a>';
			html += '<div class="da-img">';
			html += '<img src="'+val.images.split('[COL]')[1]+'" alt="'+val.introduction+'" title="'+val.introduction+'" width="200">';
			html += '</div>';
			html += '</div>';
			
			i++;
		});
		
		html += '<nav class="da-arrows">';
		html += '<span class="da-arrows-prev"></span> <span class="da-arrows-next"></span>';
		html += '</nav>';
		html += '<nav class="da-dots">';
		html += '<span class="da-dots-current"></span><span class=""></span><span class=""></span><span class=""></span>';
		html += '</nav>';
		
		$('div#da-slider').html(html);
		
		$('#da-slider').cslider({
			autoplay	: true,
			interval : 6000
		});
	});
}

function loadListProducts() {
	$.getJSON('../data/home/products.json?tk='+new Date().getTime(), function(data) {
		
		var i=1;
		$.each(data, function(key, val) {
			var html = '<a href="../product/'+val.keyUrl+'.html" style="color: #444; line-height: 25px; font-size: 13px; font-family: PT Sans,Arial,Helvetica,sans-serif; text-shadow: none!important; text-decoration:none">';
			html += '<div class="rimg" style="text-align: center">';
			html += '<img src="'+val.images.split('[COL]')[1]+'" alt="'+val.introduction+'" title="'+val.introduction+'"><br>';
			html += '<img src="../static/img/detalhes.png" alt="clique e veja mais informações"/>';
			html += '</div>';
			html += '<div class="rdetails">';
			html += '<h5>'+val.name+'</h5>';
			html += '<p>'+val.introduction+'</p>';
			if (val.quantity > 0) {
				html += '<h4><span class="text-warning">R$ '+convertMoeda(val.unityvalue)+'</span></h4>';
			} else {
				html += '<h4><span class="text-warning">Produto indisponível</span></h4>';
			}
			html += '</div></a>';
			
			$('div#product'+i).html(html);
			
			i++;
		});
		
	});
}