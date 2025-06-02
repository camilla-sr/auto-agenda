fetch('../assets/barra.html')
    .then(response => response.text())
    .then(data => {
        document.getElementById('barra').innerHTML = data;
    });


document.addEventListener('DOMContentLoaded', function() {
    const inputSenha = document.getElementById('senha');
    const inputConfSenha = document.getElementById('confirmarSenha');
    const mensagem = document.getElementById('mensagem');

    function verificarSenha() {
        const senha = inputSenha.value;
        const confirmacao = inputConfSenha.value;

        if (confirmacao.length > 0) {
            if (senha !== confirmacao) {
                inputConfSenha.classList.add('is-invalid');
                inputSenha.classList.add('is-invalid');
                mensagem.style.display = 'block';
                return false;
            } else {
                inputConfSenha.classList.remove('is-invalid');
                inputSenha.classList.remove('is-invalid');

                inputConfSenha.classList.add('is-valid');
                inputSenha.classList.add('is-valid');
                mensagem.style.display = 'none';
                return true;
            }
        } else {
            inputConfSenha.classList.remove('is-invalid', 'is-valid');
            inputSenha.classList.remove('is-invalid', 'is-valid');
            mensagem.style.display = 'none';
            return false;
        }
    }

    inputConfSenha.addEventListener('input', verificarSenha);
    inputSenha.addEventListener('input', function() {
        if (inputConfSenha.value.length > 0) {
            verificarSenha();
        }
    });
});