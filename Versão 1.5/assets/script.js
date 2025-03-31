fetch('../assets/barra.html')
    .then(response => response.text())
    .then(data => {
        document.getElementById('barra').innerHTML = data;
    });