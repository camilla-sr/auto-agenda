document.addEventListener('DOMContentLoaded', function() {
    const inputSenha = document.getElementById('senha');
    const inputConfSenha = document.getElementById('confirmarSenha');
    const inputValido = document.getElementById('senhaValida');

    function verificarSenha() {
        const senha = inputSenha.value;
        const confirmacao = inputConfSenha.value;

        if (senha.length === 0 || confirmacao.length === 0) {
            inputConfSenha.classList.remove('is-invalid', 'is-valid');
            inputSenha.classList.remove('is-invalid', 'is-valid');
            inputValido.value = '';
            return false;
        }

        if (senha !== confirmacao) {
            inputConfSenha.classList.add('is-invalid');
            inputSenha.classList.add('is-invalid');
            inputConfSenha.classList.remove('is-valid');
            inputSenha.classList.remove('is-valid');
            inputValido.value = '';
            return false;
        } else {
            inputConfSenha.classList.remove('is-invalid');
            inputSenha.classList.remove('is-invalid');
            inputConfSenha.classList.add('is-valid');
            inputSenha.classList.add('is-valid');
            inputValido.value = senha;
            return true;
        }
    }

    inputConfSenha.addEventListener('input', verificarSenha);
    inputSenha.addEventListener('input', verificarSenha);
});
