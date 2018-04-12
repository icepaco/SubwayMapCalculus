/**
 * 
 */
function sendData() {
	var nodeForm = $("form[name='inputSubwayNode']")[0];
	var postData = $(nodeForm).serializeArray();
	$.post("NodeCreatorServlet", postData, 
			function(result, status) {
				$('#responseForm').html(result);
			});
};
function computeData(){
	var nodeForm = $("form[name='computeNode']")[0];
	var postData = $(nodeForm).serializeArray();
	$.post("PathfinderServlet", postData, 
			function(result, status) {
				$('#responseForm').html(result);
			});	
};