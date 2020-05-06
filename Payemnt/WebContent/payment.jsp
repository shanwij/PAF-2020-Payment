<%@page import ="com.Payment" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment details</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/payment.js"></script>
</head>
<body>
<div class="container">
<div class="row">
	<div class="col-6">

 		<h1 class="m-3">Payment details</h1>

		 <form id="formPayment" name="formPayment" method="post" action="payment.jsp">
		
			Card Holder Name:
			<input id="cardHolderName" name="cardHolderName" placeholder="Ex - Nadeers max" type="text" class="form-control form-control-sm">
			<br> Amount:
			<Input id="amount" name="amount" type="text" placeholder="Ex - 2500" class="form-control form-control-sm">
			<br> Credit Card Number:
			<Input id="creditCardNumber" name="creditCardNumber" placeholder="Ex - 1234 5678 9012 3456" type="text" class="form-control form-control-sm">
			<br> Year:
			<Input id="year" name="year" type="text" placeholder="YYYY" class="form-control form-control-sm">
			<br> Month:
			
			<select id="month" name="month" class="form-control" >
					 <option value="0">--Select Month--</option>
					 <option value="1">01</option>
					 <option value="2">02</option>
					 <option value="3">03</option>
					 <option value="4">04</option>
					  <option value="5">05</option>
					 <option value="6">06</option>
					 <option value="7">07</option>
					  <option value="8">08</option>
					 <option value="9">09</option>
					 <option value="10">10</option>
					 <option value="11">11</option>
					 <option value="12">12</option>
					</select>
			<br>
			<!-- Input id="month" name="month" type="text" placeholder="MM" class="form-control form-control-sm"> -->
		 	<br> CVC:
			<Input id="cvc" name="cvc" type="text" placeholder="Ex - 123" class="form-control form-control-sm">
			<br>
			<input type="button" id="btnSave" value="Save" name="btnSave" class="btn btn-primary">
			<input type="hidden" id="hidPaymentIDSave" value="" name="hidPaymentIDSave">
	</form>
			<div id="alertSuccess" class="alert alert-success">
			</div>
		 	<div id="alertError" class="alert alert-danger"></div>
		 	<br>
		 	<br>
		 	<div id = "divPaymentGrid" class="container" >
		 	<%
		 		Payment PayObj = new Payment();
		 		out.print(PayObj.ViewPayment());
		 	%>
		 	</div>
			
	</div>
</div>

<br>

</div>

</body>
</html>