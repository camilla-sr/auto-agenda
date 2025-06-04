fetch('../assets/barra.html')
    .then(response => response.text())
    .then(data => {
        document.getElementById('barra').innerHTML = data;
    });

    document.addEventListener("DOMContentLoaded", function () {
        const senha = document.getElementById('senha');
        const confSenha = document.getElementById('confSenha');
        const feedback = document.getElementById('senhaFeedback');
    
        function validarSenhas() {
            if (confSenha.value === "") {
                confSenha.classList.remove("is-invalid", "is-valid");
                feedback.classList.add("d-none");
                return;
            }
    
            if (senha.value === confSenha.value) {
                confSenha.classList.remove("is-invalid");
                confSenha.classList.add("is-valid");
                feedback.classList.add("d-none");
            } else {
                confSenha.classList.remove("is-valid");
                confSenha.classList.add("is-invalid");
                feedback.classList.remove("d-none");
            }
        }
    
        senha.addEventListener('input', validarSenhas);
        confSenha.addEventListener('input', validarSenhas);
    });