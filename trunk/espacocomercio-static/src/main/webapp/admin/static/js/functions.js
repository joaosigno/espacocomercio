$(function(){
    // tooltip helper
    $('[rel=tooltip]').tooltip();	
    $('[rel=tooltip-bottom]').tooltip({
        placement : 'bottom'
    });	
    $('[rel=tooltip-right]').tooltip({
        placement : 'right'
    });	
    $('[rel=tooltip-left]').tooltip({
        placement : 'left'
    });	
    // end tooltip helper
    
    
    // animate scroll, define class scroll will be activate this
    $(".scroll").click(function(e){
        e.preventDefault();
        $("html,body").animate({scrollTop: $(this.hash).offset().top-60}, 'slow');
    });
    // end animate scroll
    
    
    // control box
    // collapse a box
    $('.header-control [data-box=collapse]').click(function(){
        var collapse = $(this),
        box = collapse.parent().parent().parent();

        collapse.find('i').toggleClass('icofont-caret-up icofont-caret-down'); // change icon
        box.find('.box-body').slideToggle(); // toggle body box
    });

    // close a box
    $('.header-control [data-box=close]').click(function(){
        var close = $(this),
        box = close.parent().parent().parent(),
        data_anim = close.attr('data-hide'),
        animate = (data_anim == undefined || data_anim == '') ? 'fadeOut' : data_anim;

        box.addClass('animated '+animate);
        setTimeout(function(){
            box.hide()
        },1000);
    });
    // end control box
    
    
});

$(document).ready(function() {
    
});

function loadMenuAdmin() {
	$.getJSON('/ecommerce-web/admin/menu?tk='+new Date().getTime(), function(data) {
		if (data.status) {
			loadMenu('/ecommerce/admin/static/json/menu'+data.generic[0]+'.json');
			$('span#spanTitleEcommerce').text(data.generic[1]);
			$('span#spanEmailPerfil').text(data.generic[2]);
			$('a#aToLoja').attr('href', '/ecommerce/'+data.generic[3]);
		} else {
			errorForm(data)
		}
		
	});
}

function loadMenu(urlJson) {
	$.getJSON( urlJson,  function(data) {
		
		var html = 	'<aside class="side-left" style="top: 60px;">';
		html += 		'<ul class="sidebar">';
		
		$.each(data.menus, function(key, val) {
			html += generateItemMenu(key, val);
		});
		
		html += 		'</ul>';
		html +=		'</aside>';
				
		$('div#divAdminMenu').html(html);
		
		$('li#liMenu-home').attr('class', 'active');
	});
}

function generateItemMenu(key, val) {
	var html = '';
	
	html += '<li id="liMenu-'+val.id+'">';
	html += 	'<a href="'+val.url+'" title="'+key+'">';
	html += 		'<div class="helper-font-24">';
	html += 			'<i class="'+val.icon+'"></i>';
	html += 		'</div>';
	html +=			'<span class="sidebar-text">'+key+'</span>';
	html += 	'</a>';
	html += '</li>';
	
	return html;
}

function errorForm(data) {
	if (!data.status) {
		$.each(data.messageError, function(key, val) {
			if (key=='generic') {
				alert(val);
			} else if (key=='sessionInvalid') {
				location.href='/ecommerce/portal/login';
			} else {
				$('[name="'+key+'"]').parent().parent().attr('class', $('[name="'+key+'"]').parent().parent().attr('class')+" error");
				$('#spanError-'+key).text(val);
			}
		});
	}
}

function loadHome() {
	$.getJSON('/ecommerce-web/admin/home?tk='+new Date().getTime(), function(data) {
		if (data.status) {
			
			$('span#spanClientsTotal').text(data.generic[0]);
			$('span#spanOrdersTotal').text(data.generic[1]);
			$.getJSON('/gapi/espacocomercio.php?p='+data.generic[2], function(r) {
        		$('span#spanVisitsTotal').text(r.visits);
        		generateGrafics(r.pagesMonth, data.generic[3], data.generic[4]);
        	});
			$('div#newClientsCount').html(data.generic[5]);
			$('div#newOrdersCount').html(data.generic[6]);
			$('div#newPaymentsCount').html(data.generic[7]);
			
		} else {
			errorForm(data)
		}
		
	});
}

function generateGrafics(visitsByMonth, clientsByMonth, ordersByMonth) {
	$('#tab-stat > a[data-toggle="tab"]').on('shown', function(){
        if(sessionStorage.mode == 4){
            $('body,html').animate({
                scrollTop: 0
            }, 'slow');
        }
    });
    
    $("span[data-chart=peity-bar]").peity("bar");
    
    var months = new Array();
    
    var d1 = [];
    var i = 0;
    $.each(visitsByMonth, function(key, val) {
    	var m = convertMonth(val.month.toString().split(' ')[0]);
    	d1.push([m, val.pageview]);
    	
    	months[i] = m;
    	i++;
    });
    
    var d3 = [];
    i = 1;
    $.each(clientsByMonth, function(key, val) {
    	d3.push([months[i], val]);
    	i++;
    });
    
    var d2 = [];
    i = 1;
    $.each(ordersByMonth, function(key, val) {
    	d2.push([months[i], val]);
    	i++;
    });
    
    //d1 = [ ['jan', 231], ['feb', 243], ['mar', 323], ['apr', 352], ['maj', 354], ['jun', 467], ['jul', 429] ];
    //d2 = [ ['jan', 87], ['feb', 67], ['mar', 96], ['apr', 105], ['maj', 98], ['jun', 53], ['jul', 87] ];
    //d3 = [ ['jan', 34], ['feb', 27], ['mar', 46], ['apr', 65], ['maj', 47], ['jun', 79], ['jul', 95] ];
    
    var visitor = $("#visitor-stat"),
    order = $("#order-stat"),
    user = $("#user-stat"),
    
    data_visitor = [{
            data: d1,
            color: '#00A600'
        }],
    data_order = [{
            data: d2,
            color: '#2E8DEF'
        }],
    data_user = [{
            data: d3,
            color: '#DC572E'
        }],
     
    
    options_lines = {
        series: {
            lines: {
                show: true,
                fill: true
            },
            points: {
                show: true
            },
            hoverable: true
        },
        grid: {
            backgroundColor: '#FFFFFF',
            borderWidth: 1,
            borderColor: '#CDCDCD',
            hoverable: true
        },
        legend: {
            show: false
        },
        xaxis: {
            mode: "categories",
            tickLength: 0
        },
        yaxis: {
            autoscaleMargin: 2
        }

    };
    
    $.plot(visitor, data_visitor, options_lines);
    $.plot(order, data_order, options_lines);
    $.plot(user, data_user, options_lines);
    
    var previousPoint = null;
    $('#visitor-stat, #order-stat, #user-stat').bind("plothover", function (event, pos, item) {
        
        if (item) {
            if (previousPoint != item.dataIndex) {
                previousPoint = item.dataIndex;

                $("#tooltip").remove();
                var x = item.datapoint[0].toFixed(2),
                y = item.datapoint[1].toFixed(2);
                label = item.series.xaxis.ticks[item.datapoint[0]].label;
                
                showTooltip(item.pageX, item.pageY,
                label + " = " + y);
            }
        }
        else {
            $("#tooltip").remove();
            previousPoint = null;            
        }
        
    });
}

function showTooltip(x, y, contents) {
    $('<div id="tooltip" class="bg-black corner-all color-white">' + contents.replace('.00','') + '</div>').css( {
        position: 'absolute',
        display: 'none',
        top: y + 5,
        left: x + 5,
        border: '0px',
        padding: '2px 10px 2px 10px',
        opacity: 0.9,
        'font-size' : '11px'
    }).appendTo("body").fadeIn(200);
}

function convertMonth(num) {
	if (num==1) return "jan";
	if (num==2) return "fev";
	if (num==3) return "mar";
	if (num==4) return "abr";
	if (num==5) return "mai";
	if (num==6) return "jun";
	if (num==7) return "jul";
	if (num==8) return "ago";
	if (num==9) return "set";
	if (num==10) return "out";
	if (num==11) return "nov";
	if (num==12) return "dez";
}