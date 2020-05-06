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
		var type = ($("#hidPaymentIDSave").val() == "") ? "POST" : "PUT";
		
		$.ajax(
				{
					url : "PaymentAPI",
					type : type,
					data : $("#formPayment").serialize(),
					dataType : "text",
					complete : function(response, status)
				 {
						onPaymentSaveComplete(response.responseText, status);
				 }
		});
	});
	
// UPDATE==========================================
	$(document).on("click", ".btnUpdate", function(event)
	{
		$("#hidPaymentIDSave").val($(this).closest("tr").find('#hidPaymentIDUpdate').val());
		$("#cardHolderName").val($(this).closest("tr").find('td:eq(0)').text());
		$("#amount").val($(this).closest("tr").find('td:eq(1)').text());
		$("#creditCardNumber").val($(this).closest("tr").find('td:eq(2)').text());
		$("#year").val($(this).closest("tr").find('td:eq(3)').text());
		$("#month").val($(this).closest("tr").find('td:eq(4)').text());
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
		
		// Amount-------------------------------
		var tmpAmount = $("#amount").val().trim();
		if (!$.isNumeric(tmpAmount))
		{
			return "Insert a Amount";
		}
		
		// convert to decimal Number
		$("#amount").val(parseInt(tmpAmount).toFixed(0));
		
		// is numerical value
		var crdLengh = $("#creditCardNumber").val().length;
		var tmpCardNum = $("#creditCardNumber").val().trim();
		if (!$.isNumeric(tmpCardNum) )
		{
			return "Insert a valid Credit Card Number";
		}
		if (crdLengh !== 16)
		{
			return "Check card number again";
		}
		// convert to decimal Number
		$("#creditCardNumber").val(parseInt(tmpCardNum).toFixed(0));
		
		//date
		var regYear = /^2020|2021|2022|2023|2024|2025|2026|2027|2028|2029|2030|2031|2032|2033$/;
		var tmpYear = $("#year").val().trim();
		if (!$.isNumeric(tmpYear))
		{
			return "Insert a Expire Year";
		}
		if(!regYear.test(tmpYear))
		{
			return "Insert a Valid year";
		}
		// convert to decimal Number
		$("#year").val(parseInt(tmpYear).toFixed(0));
		
		//month		
		var regMonth = /^1|2|3|4|5|6|7|8|9|10|11|12$/;
		var tmpMonth = $("#month").val().trim();
		if (!$.isNumeric(tmpMonth))
		{
			return "Insert a Expire month";
		}
		if (!regMonth.test(tmpMonth))
		{
			return "Insert a valid month";
		}
		// convert to decimal Number
		$("#month").val(parseInt(tmpMonth).toFixed(0));
		
		// CvC------------------------
		var regCVV = /^[0-9]{3,3}$/;
		var tmpCvc = $("#cvc").val().trim();
		if (!$.isNumeric(tmpCvc))
		{
			return "Insert a CvC";
		}
		if (!regCVV.test(tmpCvc))
		{
			return "Insert valid cvc";
		}
		// convert to decimal Number
		$("#cvc").val(parseInt(tmpCvc).toFixed(0));
		return true;
}

function onPaymentSaveComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divPaymentGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error")
	{
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else
	{
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
		$("#hidPaymentIDSave").val("");
		$("#formPayment")[0].reset();
}



$(document).on("click", ".btnRemove", function(event)
		{
			$.ajax(
					{
						url : "PaymentAPI",
						type : "DELETE",
						data : "paymentID=" + $(this).data("paymentid"),
						dataType : "text",
						complete : function(response, status)
						{
							onPaymentDeleteComplete(response.responseText, status);
						}
				});
		});
		


function onPaymentDeleteComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divPaymentGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error")
	{
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else
	{
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}
