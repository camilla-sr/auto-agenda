fetch('../assets/barra.html')
    .then(response => response.text())
    .then(data => {
        document.getElementById('barra').innerHTML = data;
    });


    //esse carai aqui não tá funfando ainda
const pageAtual = window.location.pathname.split('/').pop(); // "index.html"
    
document.querySelectorAll('.nav-link').forEach(link => {
    if (link.getAttribute('href') === pageAtual) {
        console.log(link)
        link.parentElement.classList.add('active');
    }
});