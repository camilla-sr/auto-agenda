document.addEventListener('DOMContentLoaded', function() {

// CONTADOR DE CARACTERES

const obsTextarea = document.getElementById('observacao')
const obsCounter = document.getElementById('observacao-counter')
document.addEventListener('DOMContentLoaded', function () {
})

if (obsTextarea && obsCounter) {
  const updateCounter = () => {
    const length = obsTextarea.value.length
    const max = obsTextarea.maxLength || 200
    obsCounter.innerHTML = `<small>${length}/${max}</small>`
  }

  obsTextarea.addEventListener('input', updateCounter)
  updateCounter() // Atualiza na abertura da página ou ao editar
}

    // Hamburger menu toggle
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
	
	inputSenha.addEventListener('input', verificarSenha);
	inputConfSenha.addEventListener('input', verificarSenha);
});
const filtroStatus = document.getElementById('filtro-status');
filtroStatus.addEventListener('change', aplicarFiltros);

function aplicarFiltros() {
  const termoBusca = busca.value.toLowerCase();
  const statusSelecionado = filtroStatus.value;

  let found = false;

  linhas.forEach(function (linha) {
    if (linha.querySelector('td[colspan="6"]')) return;

    const textoLinha = linha.textContent.toLowerCase();
    const statusLinha = linha.querySelector('.status-badge')?.textContent.trim();

    const correspondeBusca = textoLinha.includes(termoBusca);
    const correspondeStatus = !statusSelecionado || statusLinha === statusSelecionado;

    if (correspondeBusca && correspondeStatus) {
      linha.style.display = '';
      found = true;
    } else {
      linha.style.display = 'none';
    }
  });

  if (noResultMessage) {
    noResultMessage.style.display = found ? 'none' : '';
  }
}

// Garante que o filtro de texto também usa aplicarFiltros
busca.addEventListener('input', aplicarFiltros);
