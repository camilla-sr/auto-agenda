fetch('../assets/barra.html')
    .then(response => response.text())
    .then(data => {
        document.getElementById('barra').innerHTML = data;
    });


document.addEventListener('DOMContentLoaded', function() {
	    const inputSenha = document.getElementById('senha');
	    const inputConfSenha = document.getElementById('confirmarSenha');
	    const mensagem = document.getElementById('mensagem');
	    
	    // Função para verificar se senhas coincidem e mostrar retorno em tempo real
	    function verificarSenha() {
	        const senha = inputSenha.value;
	        const confirmacao = inputConfSenha.value;
	        
	        if (confirmacao.length > 0) {
	            if (senha !== confirmacao) {
	                inputConfSenha.classList.add('is-invalid');
	                mensagem.style.display = 'block';
	                return false;
	            } else {
	                inputConfSenha.classList.remove('is-invalid');
	                inputConfSenha.classList.add('is-valid');
	                mensagem.style.display = 'none';
	                return true;
	            }
	        } else {
	            inputConfSenha.classList.remove('is-invalid', 'is-valid');
	            mensagem.style.display = 'none';
	            return false;
	        }
	    }
	    
	    // Fica observando o tempo todo
	    inputConfSenha.addEventListener('input', verificarSenha);
	    
	    // Observa quando o campo de senha é alterado
	    inputSenha.addEventListener('input', function() {
	        if (inputConfSenha.value.length > 0) {
	            verificarSenha();
	        }
	    });
	});