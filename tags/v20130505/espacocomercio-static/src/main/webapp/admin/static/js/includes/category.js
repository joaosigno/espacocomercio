function loadCategory() {
	setMenuActive('category');
	loading('contentAll');
	$.getJSON('/ecommerce-web/admin/category?tk='+new Date().getTime(), function(data) {
		if (data.status) {
			$('#contentAll').load('includes/category.html', function() {
				$('#box-tab-Category').css('margin-left', '10px');
				loadGrid('/ecommerce-web/admin/product/category/consult', 'grid', 'formEdit', '/ecommerce-web/admin/product/category/remove');
				$('span#spanCategoryTotal').text(data.generic[0]);
				$('span#spanTitleEcommerce').text(data.generic[1]);
				activePlugins();
			});
		} else {
			errorForm(data);
			$('#contentAll').html('');
		}
	});
}

function cadastrocategoria(data) {
	alert("Categoria criada com sucesso.", true);
	$('button[type="reset"]').click();
}
