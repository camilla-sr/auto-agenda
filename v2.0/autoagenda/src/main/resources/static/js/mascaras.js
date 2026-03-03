$(document).ready(function () {
  // ==============================
  // 1) Máscara SOMENTE em inputs
  // ==============================
  const $valInputs = $('input.valores');
  $valInputs.mask('#.##0,00', { reverse: true });

  // Normaliza valores pré-preenchidos (ex.: vindo da tabela com ponto)
  function normalizeToMask($el) {
    let v = ($el.val() || '').trim();
    if (!v) return;

    // Mantém se já estiver no padrão pt-BR (1.234,56)
    const isPtBr = /^\d{1,3}(\.\d{3})*,\d{2}$/.test(v) || /^\d+,\d{2}$/.test(v);
    // Converte se vier como 1234.56 (ponto decimal)
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

  // ==========================================
  // 2) Exibição na TABELA (sem "R$", padrão BR)
  //    - Usa vírgula como decimal e ponto em milhar
  //    - Ex.: 1250.12  ->  1.250,12
  // ==========================================
  function formatTablePricesBR() {
    const fmtBR = new Intl.NumberFormat('pt-BR', {
      minimumFractionDigits: 2,
      maximumFractionDigits: 2
    });

    function renderCell($td) {
      let t = ($td.text() || '').trim();
      if (!t) return;

      // Remove qualquer coisa que não seja dígito, ponto, vírgula ou sinal
      // (ex.: tira "R$ ", espaços, etc.)
      const cleaned = t.replace(/[^\d.,-]/g, '');

      // Já está em pt-BR?
      const isPtBr = /^\d{1,3}(\.\d{3})*,\d{2}$/.test(cleaned) || /^\d+,\d{2}$/.test(cleaned);
      if (isPtBr) return;

      // Converte textos tipo 1250.12, 1,250.12, 1000 -> número
      const num = parseFloat(cleaned.replace(/\./g, '').replace(',', '.'));
      if (!isNaN(num)) $td.text(fmtBR.format(num)); // -> 1.250,12
    }

    // 2.1) Se existir classe .preco-venda, formata direto
    const $cells = $('td.preco-venda');
    if ($cells.length) {
      $cells.each(function () { renderCell($(this)); });
      return;
    }

    // 2.2) Fallback: localizar a coluna pelo cabeçalho “Preço Venda”
    $('table').each(function () {
      const $table = $(this);
      let idx = -1;

      $table.find('thead th').each(function (i) {
        const h = $(this).text().trim().toLowerCase();
        if (
          h === 'preço venda' || h === 'preco venda' ||
          h === 'preço de venda' || h === 'preco de venda'
        ) { idx = i; return false; }
      });

      if (idx >= 0) {
        $table.find('tbody tr').each(function () {
          const $td = $(this).children('td').eq(idx);
          renderCell($td);
        });
      }
    });
  }

  // Formata a tabela assim que a página carrega
  formatTablePricesBR();

  // =======================================================
  // 3) Antes de enviar: vírgula -> ponto (para o backend)
  // =======================================================
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
        // Fallback caso a máscara não esteja disponível
        let sv = ($i.val() || '').replace(/\./g, '').replace(',', '.');
        if (sv) $i.val(sv);
      }
    });

    // Revalida/habilita botão se houver função global
    if (typeof window.verificarCampos === 'function') {
      try { window.verificarCampos(); } catch (e) { /* ignore */ }
    }
  });
});
