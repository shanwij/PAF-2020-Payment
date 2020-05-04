$(document).ready(function()
		{
	if ($("#alertSuccess").text().trim() == "")
	{
		$("#alertSuccess").hide();
	}
		$("#alertError").hide();
		});
	// SAVE ============================================
	$(document).on("click", "#btnSave", function(event)
			{
// Clear alerts---------------------
		$("#alertSuccess").text("");
		$("#alertSuccess").hide();
		$("#alertError").text("");
		$("#alertError").hide();
// Form validation-------------------
		var status = validatePaymentForm();
		if (status != true)
		{
			$("#alertError").text(status);
			$("#alertError").show();
			return;
		}
// If valid------------------------
		$("#formItem").submit();
			});
// UPDATE==========================================
	$(document).on("click", ".btnUpdate", function(event)
			{
	$("#hidPaymentIDSave").val($(this).closest("tr").find('#hidPaymentIDUpdate').val());
	$("#cardHolderName").val($(this).closest("tr").find('td:eq(0)').text());
	$("#type").val($(this).closest("tr").find('td:eq(1)').text());
	$("#amount").val($(this).closest("tr").find('td:eq(2)').text());
	$("#date").val($(this).closest("tr").find('td:eq(3)').text());
	$("#creditCardNumber").val($(this).closest("tr").find('td:eq(4)').text());
	$("#cvc").val($(this).closest("tr").find('td:eq(5)').text());
			});
// CLIENTMODEL=========================================================================
	function validatePaymentForm()
	{
// Name
		if ($("#cardHolderName").val().trim() == "")
		{
			return "Insert Your Name.";
		}
// type
		if ($("#type").val().trim() == "")
		{
			return "Insert Card Type.";
		} 

// Amount-------------------------------
		if ($("#amount").val().trim() == "")
		{
			return "Insert Amount.";
		}
//date
		if ($("#date").val().trim() == "")
		{
			return "Insert Date.";
		}
// is numerical value
		var tmpCardNum = $("#creditCardNumber").val().trim();
		if (!$.isNumeric(tmpCardNum))
		{
			return "Insert a Credit Card Number";
		}
// convert to decimal Number
		$("#creditCardNumber").val(parseInt(tmpCardNum).toFixed(2));
// CvC------------------------
		if ($("#cvc").val().trim() == "")
		{
			return "Insert CvC Number.";
		}
		return true;
}