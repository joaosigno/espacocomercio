function loadFinance() {
	setMenuActive('finance');
	loading('contentAll');
	$('#contentAll').load('includes/finance.html', function() {
		activePlugins();
	});
}