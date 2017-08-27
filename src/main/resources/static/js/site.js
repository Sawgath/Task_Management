function setPicker(){
    $( "#projectStartTime" ).datepicker(
        { dateFormat: 'yy-mm-dd',
            onSelect: function(d,i) {
                if (d !== i.lastVal) {
                    
                }
            }
        });
    $( "#projectEndTime" ).datepicker(
            { dateFormat: 'yy-mm-dd',
                onSelect: function(d,i) {
                    if (d !== i.lastVal) {
                        
                    }
                }
        });
};

function getRequestParam(p){
    return (window.location.search.match(new RegExp('[?&]' + p + '=([^&]+)')) || [, null])[1];
};

function setInitialDate(){
    var requestDate = getRequestParam('date');
    if(requestDate == null){
        requestDate = new Date();
    }else{
        requestDate = formatDate(requestDate);
    }
    $('#projectStartTime').datepicker('setDate', requestDate);
    $('#projectEndTime').datepicker('setDate', requestDate);
};


function formatDate(input) {
    var dateFormat = 'yyyy-mm-dd';
    var parts = input.match(/(\d+)/g),
        i = 0, fmt = {};
    dateFormat.replace(/(yyyy|dd|mm)/g, function(part) { fmt[part] = i++; });

    return new Date(parts[fmt['yyyy']], parts[fmt['mm']]-1, parts[fmt['dd']]);
};

function setSession(val){
	
	var dat=val;
	$.ajax({
        type: "POST",
        url: "http://localhost:8080/movielist",
        data: {name : dat},
        dataType: "json",
        timeout: 30,
        success: function (response) {
        	window.location.href = "http://localhost:8080/recommend/"+dat; 
            
        },
        error: function (e) {
        	
            console.log("ERROR : ", e);
            

        }
    });
    
}

$(document).ready(function(){
    setPicker();
    setInitialDate();
});