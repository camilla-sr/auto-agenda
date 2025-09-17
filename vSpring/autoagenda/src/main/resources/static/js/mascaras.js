$(document).ready(function () {
  // Aplica a máscara SOMENTE nos inputs numéricos
  const $valInputs = $('input.valores');
  $valInputs.mask('#.##0,00', { reverse: true });

  // Normaliza valores pré-preenchidos (ex.: vindo da tabela com ponto)
  function normalizeToMask($el) {
    let v = ($el.val() || '').trim();
    if (!v) return;

    // Se vier como 1234.56 (ponto decimal), converte para 1.234,56 antes da máscara reprocessar
    // Mantém se já estiver no padrão pt-BR (\d{1,3}(\.\d{3})*,\d{2})
    const isPtBr = /^\d{1,3}(\.\d{3})*,\d{2}$/.test(v) || /^\d+,\d{2}$/.test(v);
    const isDotDecimal = /^\d+(\.\d{1,2})$/.test(v) || /^\d+\.\d+$/.test(v);

    if (isDotDecimal && !isPtBr) {
      v = v.replace('.', ',');
      $el.val(v);
    }

    // Dispara para a jQuery Mask reformatar visualmente
    $el.trigger('input');
  }

  // Normaliza no carregamento (útil quando abre a tela em modo editar)
  $valInputs.each(function () { normalizeToMask($(this)); });

  // Caso um script preencha depois, ao focar re-normaliza
  $valInputs.on('focus', function () { normalizeToMask($(this)); });

  // Antes de enviar QUALQUER formulário, converte vírgula -> ponto para o backend
  $(document).on('submit', 'form', function () {
    $(this).find('input.valores').each(function () {
      const $i = $(this);
      // jQuery Mask: cleanVal = somente dígitos
      if (typeof $i.cleanVal === 'function') {
        const raw = $i.cleanVal(); // ex.: "123456" (para 1.234,56)
        if (raw === '') return;
        const num = (parseInt(raw, 10) || 0) / 100; // -> 1234.56
        // Envia em ponto fixo com 2 casas (compatível com BigDecimal no Spring)
        $i.val(num.toFixed(2));
      } else {
        // Fallback caso a máscara não esteja disponível por algum motivo
        let sv = ($i.val() || '').replace(/\./g, '').replace(',', '.');
        if (sv) $i.val(sv);
      }
    });

    // Tenta revalidar/habilitar botão se houver função de validação global
    if (typeof window.verificarCampos === 'function') {
      try { window.verificarCampos(); } catch (e) { /* ignore */ }
    }
  });
});
