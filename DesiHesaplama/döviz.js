const api = 'https://finans.truncgil.com/today.json';
 
function  getdata(api) {
    fetch(api).then(res=>res.json()).then(data =>{
        console.log(data.rates);
        const USD = data.rates.USD;
        const EUR = data.rates.EUR;
        setData(USD,EUR)
    })
    .catch((err) => console.warn(err));
}
function setData(USD,EUR)
{
    const dolar = document.getElementById('#usd');
    const euro = document.getElementById('#eur');
    dolar.textContent=USD;
    euro.textContent=EUR;

}
getdata(api);
setInterval(()=>
{
getdata(api);

},30000)

