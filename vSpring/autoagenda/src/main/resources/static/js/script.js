document.addEventListener('DOMContentLoaded', function() {
    
    // ========== NAVBAR ACTIVE STATE ==========
    const currentPath = window.location.pathname;
    const navLinks = document.querySelectorAll('.nav-link, .dropdown-item');

    navLinks.forEach(link => {
        const href = link.getAttribute('href');
        if (!href || href === '#') return;
        
        // Match exato ou início da rota (para sub-rotas como /funcionarios/editar)
        if (currentPath === href || 
            (href !== '/' && currentPath.startsWith(href))) {
            link.classList.add('active');
            
            // Se for dropdown-item, também marca o dropdown-toggle pai
            if (link.classList.contains('dropdown-item')) {
                const dropdown = link.closest('.dropdown');
                if (dropdown) {
                    const toggle = dropdown.querySelector('.dropdown-toggle');
                    if (toggle) toggle.classList.add('active');
                }
            }
        }
    });

    // ========== HAMBURGER MENU TOGGLE ==========
    const hamburger = document.getElementById('hamburger-menu');
    const navMenu = document.getElementById('nav-menu');

    if (hamburger && navMenu) {
        hamburger.addEventListener('click', function() {
            hamburger.classList.toggle('active');
            navMenu.classList.toggle('active');

            // Update ARIA attribute
            const isExpanded = hamburger.getAttribute('aria-expanded') === 'true';
            hamburger.setAttribute('aria-expanded', !isExpanded);
        });
    }

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
	
	// CORREÇÃO: Só adiciona listeners se os elementos existirem
	if (inputSenha && inputConfSenha) {
		inputSenha.addEventListener('input', verificarSenha);
		inputConfSenha.addEventListener('input', verificarSenha);
	}
});
