$('#paymentForm').on("submit",function(event){
    event.preventDefault();
    let orderId = $('#orderId').val();
    let amount =  $('#amount').val();
    doPayment(orderId,amount);
});

function doPayment(orderId,amount){
    var options = {
        "key": "rzp_test_ZC6ooPiUUScGpO",
        "amount": amount*100,
        "currency": "INR",
        "name": "Seller Buyer",
        "description": "Sales",
        "order_id": orderId,
        "handler": function (response){
            console.log(response);
            alert(response.razorpay_payment_id);
            alert(response.razorpay_order_id);
            alert(response.razorpay_signature)
        },
        "prefill": {
            "name": "",
            "email": "",
            "contact": ""
        },
        "notes": {
            "address": ""
        },
        "theme": {
            "color": "#3399cc"
        }
    };
    var rzp1 = new Razorpay(options);
    rzp1.on('payment.failed', function (response){
            console.log(response);
            alert(response.error.code);
            alert(response.error.description);
            alert(response.error.source);
            alert(response.error.step);
            alert(response.error.reason);
            alert(response.error.metadata.order_id);
            alert(response.error.metadata.payment_id);
    });
    rzp1.open();

}