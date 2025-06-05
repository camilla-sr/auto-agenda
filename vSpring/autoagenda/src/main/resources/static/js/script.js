document.addEventListener('DOMContentLoaded', function() {
	document.querySelectorAll('.cpf-mask').forEach(function(element) {
        let cpf = element.textContent.trim();
        element.textContent = formatarCPF(cpf);
    });
    
    function formatarCPF(cpf) {
        if (!cpf) return '';
        cpf = cpf.replace(/\D/g, '');
        if (cpf.length !== 11) return cpf;
        return cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, "$1.$2.$3-$4");
    }
	
    let inputSenha = document.getElementById('senha');
    let inputConfSenha = document.getElementById('confirmarSenha');
    let inputValido = document.getElementById('senhaValida');

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
	
	inputSenha.addEventListener('input', verificarSenha);
	inputConfSenha.addEventListener('input', verificarSenha);
});
