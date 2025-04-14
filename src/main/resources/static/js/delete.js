$(function() {
	$('.delete').on('click', function() {
		if (confirm("タスク内容：\n" + $(this).parents('.delete-button').find('input[name="content"]').val() + "\n※本当に削除してよろしいですか?")) {
			return true;
		}
		return false;
	});
});