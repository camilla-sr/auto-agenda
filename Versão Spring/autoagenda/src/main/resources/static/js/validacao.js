document.addEventListener('DOMContentLoaded', function() {
	const urlParams = new URLSearchParams(window.location.search);
	
	function limparUrl() {
			if (window.history.replaceState) {
				const novaUrl = window.location.protocol + "//" + window.location.host + window.location.pathname;
				window.history.replaceState({}, document.title, novaUrl);
			}
		}
	
	if (urlParams.get('sucesso') === 'true') {
	    Swal.fire({
	        title: 'Sucesso!',
	        text: 'Cadastro realizado com sucesso.',
	        icon: 'success',
	        confirmButtonText: 'OK'
		}).then(() => {
		    limparUrl();
		});
	}
	
	if (urlParams.get('erro') === 'true') {
	    Swal.fire({
	        title: 'Erro!',
	        text: 'Ocorreu um erro no cadastro.',
	        icon: 'error',
	        confirmButtonText: 'OK'
		}).then(() => {
		    limparUrl();
		});
	}
	
	if (urlParams.get('erroUsuario') === 'true') {
        Swal.fire({
            title: 'Erro!',
            text: 'Usuário já cadastrado.',
            icon: 'error',
            confirmButtonText: 'OK'
		}).then(() => {
		    limparUrl();
		});
    }
	
	
	if (urlParams.get('usuarioValido') === 'false') {
        Swal.fire({
            title: 'Erro!',
            text: 'Usuário/Senha incorreto.',
            icon: 'error',
            confirmButtonText: 'OK'
		}).then(() => {
		    limparUrl();
		});
    }
});