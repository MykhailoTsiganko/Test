
$('#searchButton').click(function(){
    console.log("hi")
    var openWeatherAPIKey = "33132812ca0f9e4b588b781107d511fe";    
    var url = "http://api.openweathermap.org/data/2.5/weather?";
    var location = $("#location").val(); 
    if(location&&location!='') {
        location = location.trim();
        var qwery = url + "q=" + location + "&units=metric"+"&lang=ua" +"&appid="+openWeatherAPIKey;
        console.log(qwery);
        var result = $.getJSON(qwery);
        result.success(function(){
            displayWeather(result.responseText);
        });

        result.fail(function(){
            console.log(result.responseText);
        })

        
    } else {
        console.log("fail  ")
    }

    
})

function displayWeather(result) {
    var wData = JSON.parse(result);
    $('.data').remove();
    $('body').append("<div class='data'></div>");
    $('.data').append
    .append("<div class='prop'>" + wData.main.temp + "</div>")
    .append("<div class='prop'>" + "<img src='http://openweathermap.org/img/w/" + wData.weather[0].icon + ".png' title='image decription'>")
    .append("<div class='prop'>" + wData.name + " " +wData.sys.country+ "</div>")
    .append("<div class='prop'>" );
    
}