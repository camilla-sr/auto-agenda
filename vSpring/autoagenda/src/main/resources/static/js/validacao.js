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
	        text: 'Ocorreu um erro no processo.',
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
	
	if (urlParams.get('erroEditar') === 'true') {
        Swal.fire({
            title: 'Erro!',
            text: 'Erro ao editar usuário.',
            icon: 'error',
            confirmButtonText: 'OK'
		}).then(() => {
		    limparUrl();
		});
    }	
	
	if (urlParams.get('usuarioExiste') === 'false') {
        Swal.fire({
            title: 'Erro!',
            text: 'Usuário não existe',
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
	
	if (urlParams.get('editado') === 'true') {
        Swal.fire({
            title: 'Sucesso!',
            text: 'Edição concluída.',
            icon: 'success',
            confirmButtonText: 'OK'
		}).then(() => {
		    limparUrl();
		});
    }
	
	if (urlParams.get('apagar') === 'true') {
        Swal.fire({
            title: 'Sucesso!',
            text: 'Registro excluído.',
            icon: 'success',
            confirmButtonText: 'OK'
		}).then(() => {
		    limparUrl();
		});
    }
	
	if (urlParams.get('erroServico') === 'true') {
        Swal.fire({
            title: 'Erro!',
            text: 'Serviço já cadastrado.',
            icon: 'error',
            confirmButtonText: 'OK'
		}).then(() => {
		    limparUrl();
		});
    }
	
	if (urlParams.get('erroCPF') === 'true') {
        Swal.fire({
            title: 'Erro!',
            text: 'CPF já cadastrado.',
            icon: 'error',
            confirmButtonText: 'OK'
		}).then(() => {
		    limparUrl();
		});
    }
});