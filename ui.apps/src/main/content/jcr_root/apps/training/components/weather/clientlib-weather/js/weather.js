(function(){

    function loadWeather(component){
    const pagePath = window.location.pathname.replace(".html", "");

    fetch(pagePath+ "/jcr:content" +".json")
        .then(res=> res.json())
        .then(data => {
            const weatherInfo = data.current_weather;

            component.innerHTML = "Temp: " + weatherInfo.temperature + "Degrees</br>" + "Wind: " +  weatherInfo.windspeed + "Miles/H";
        })
        .catch(err => {
        component.innerHTML= "Weather API not working currently";
        console.error(err);
        })
    }
    document.addEventListener("DOMContentLoaded", function(){
    document.querySelectorAll(".weather-component").forEach(loadWeather);
    });

})();
