function setPicker(){
    $( "#taskStartTime" ).datepicker(
        { dateFormat: 'yy-mm-dd',
            onSelect: function(d,i) {
                if (d !== i.lastVal) {
                    
                }
            }
        });
    $( "#taskEndTime" ).datepicker(
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
    $('#taskStartTime').datepicker('setDate', requestDate);
    $('#taskEndTime').datepicker('setDate', requestDate);
};


function formatDate(input) {
    var dateFormat = 'yyyy-mm-dd';
    var parts = input.match(/(\d+)/g),
        i = 0, fmt = {};
    dateFormat.replace(/(yyyy|dd|mm)/g, function(part) { fmt[part] = i++; });

    return new Date(parts[fmt['yyyy']], parts[fmt['mm']]-1, parts[fmt['dd']]);
};


$(document).ready(function(){
    setPicker();
    setInitialDate();
});