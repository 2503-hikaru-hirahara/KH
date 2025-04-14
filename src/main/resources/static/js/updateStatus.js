$(function() {
	$('.update-states').on('click', function() {
		if (confirm("※本当に変更してよろしいですか?")) {
			return true;
		}
		return false;
	});
});