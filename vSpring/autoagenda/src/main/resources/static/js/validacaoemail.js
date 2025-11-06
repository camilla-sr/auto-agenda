/**
 * Validação de E-mail - Centro Automotivo JJ
 * Módulo de validação avançada para endereços de e-mail
 * Versão: 1.0
 */

class ValidadorEmail {
    constructor() {
        this.regexEmail = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
        
        // Domínios inválidos/commonmente usados para testes
        this.dominiosInvalidos = [
            'example.com',
            'test.com',
            'domain.com',
            'email.com',
            'fake.com',
            'invalid.com'
        ];
        
        // Configurações
        this.config = {
            permitirPlus: true,
            validarDominio: true,
            validarMX: false, // Validação de registro MX (requer backend)
            timeout: 5000
        };
    }

    /**
     * Valida a sintaxe básica do e-mail
     * @param {string} email - E-mail a ser validado
     * @returns {boolean} - True se a sintaxe é válida
     */
    validarSintaxe(email) {
        if (!email || typeof email !== 'string') {
            return false;
        }

        // Remove espaços em branco
        email = email.trim();
        
        // Verifica comprimento básico
        if (email.length < 5 || email.length > 254) {
            return false;
        }

        // Aplica a regex principal
        return this.regexEmail.test(email);
    }

    /**
     * Valida a estrutura do e-mail (parte local e domínio)
     * @param {string} email - E-mail a ser validado
     * @returns {Object} - Resultado da validação com detalhes
     */
    validarEstrutura(email) {
        const resultado = {
            valido: false,
            erro: null,
            partes: {
                local: null,
                dominio: null,
                subdominios: []
            }
        };

        if (!this.validarSintaxe(email)) {
            resultado.erro = 'Sintaxe do e-mail inválida';
            return resultado;
        }

        try {
            const [localPart, domainPart] = email.split('@');
            
            // Valida parte local (antes do @)
            if (!this.validarParteLocal(localPart)) {
                resultado.erro = 'Parte local do e-mail inválida';
                return resultado;
            }

            // Valida domínio (após o @)
            const validacaoDominio = this.validarDominio(domainPart);
            if (!validacaoDominio.valido) {
                resultado.erro = validacaoDominio.erro;
                return resultado;
            }

            resultado.valido = true;
            resultado.partes.local = localPart;
            resultado.partes.dominio = domainPart;
            resultado.partes.subdominios = domainPart.split('.').slice(0, -1);

        } catch (error) {
            resultado.erro = 'Erro na análise do e-mail';
        }

        return resultado;
    }

    /**
     * Valida a parte local do e-mail (antes do @)
     * @param {string} localPart - Parte local do e-mail
     * @returns {boolean} - True se a parte local é válida
     */
    validarParteLocal(localPart) {
        if (!localPart || localPart.length > 64) {
            return false;
        }

        // Não pode começar ou terminar com ponto
        if (localPart.startsWith('.') || localPart.endsWith('.')) {
            return false;
        }

        // Não pode ter dois pontos consecutivos
        if (localPart.includes('..')) {
            return false;
        }

        // Caracteres permitidos (mais restritivo que a regex principal)
        const localRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+$/;
        if (!localRegex.test(localPart)) {
            return false;
        }

        return true;
    }

    /**
     * Valida o domínio do e-mail
     * @param {string} dominio - Domínio a ser validado
     * @returns {Object} - Resultado da validação do domínio
     */
    validarDominio(dominio) {
        const resultado = {
            valido: false,
            erro: null
        };

        if (!dominio || dominio.length > 253) {
            resultado.erro = 'Domínio muito longo ou vazio';
            return resultado;
        }

        // Divide o domínio em partes
        const partes = dominio.split('.');
        
        // Deve ter pelo menos 2 partes (ex: dominio.com)
        if (partes.length < 2) {
            resultado.erro = 'Domínio deve ter pelo menos duas partes';
            return resultado;
        }

        // Valida cada parte do domínio
        for (let parte of partes) {
            if (parte.length > 63) {
                resultado.erro = 'Parte do domínio muito longa';
                return resultado;
            }

            // Cada parte deve começar e terminar com alfanumérico
            if (!/^[a-zA-Z0-9]/.test(parte) || !/[a-zA-Z0-9]$/.test(parte)) {
                resultado.erro = 'Parte do domínio deve começar e terminar com letra ou número';
                return resultado;
            }

            // Caracteres permitidos no domínio
            if (!/^[a-zA-Z0-9-]+$/.test(parte)) {
                resultado.erro = 'Domínio contém caracteres inválidos';
                return resultado;
            }
        }

        // Valida TLD (última parte)
        const tld = partes[partes.length - 1];
        if (tld.length < 2) {
            resultado.erro = 'TLD muito curto';
            return resultado;
        }

        // TLD deve conter apenas letras
        if (!/^[a-zA-Z]+$/.test(tld)) {
            resultado.erro = 'TLD deve conter apenas letras';
            return resultado;
        }

        // Verifica domínios inválidos/commonmente usados para testes
        if (this.config.validarDominio) {
            const dominioLower = dominio.toLowerCase();
            if (this.dominiosInvalidos.some(invalido => dominioLower.includes(invalido))) {
                resultado.erro = 'Domínio não é válido para cadastro';
                return resultado;
            }
        }

        resultado.valido = true;
        return resultado;
    }

    /**
     * Validação completa do e-mail com diferentes níveis de rigor
     * @param {string} email - E-mail a ser validado
     * @param {string} nivel - Nível de validação: 'basico', 'medio', 'rigoroso'
     * @returns {Object} - Resultado completo da validação
     */
    validarEmail(email, nivel = 'medio') {
        const resultado = {
            valido: false,
            email: email,
            nivel: nivel,
            erros: [],
            avisos: [],
            sugestoes: [],
            detalhes: {}
        };

        // Validação básica de tipo e comprimento
        if (!email || typeof email !== 'string') {
            resultado.erros.push('E-mail não fornecido ou tipo inválido');
            return resultado;
        }

        email = email.trim();
        resultado.email = email;

        // Validação de sintaxe
        if (!this.validarSintaxe(email)) {
            resultado.erros.push('Sintaxe do e-mail inválida');
            return resultado;
        }

        // Validação de estrutura
        const estrutura = this.validarEstrutura(email);
        if (!estrutura.valido) {
            resultado.erros.push(estrutura.erro);
            return resultado;
        }

        resultado.detalhes = estrutura.partes;

        // Validações adicionais baseadas no nível
        if (nivel === 'medio' || nivel === 'rigoroso') {
            this.validacoesAdicionais(email, resultado);
        }

        // Se não há erros, o e-mail é válido
        resultado.valido = resultado.erros.length === 0;
        
        return resultado;
    }

    /**
     * Validações adicionais para níveis médio e rigoroso
     * @param {string} email - E-mail a ser validado
     * @param {Object} resultado - Objeto de resultado para acumular erros/avisos
     */
    validacoesAdicionais(email, resultado) {
        const emailLower = email.toLowerCase();
        
        // Verifica e-mails temporários/comuns
        const dominiosTemporarios = [
            'tempmail', '10minutemail', 'guerrillamail', 'mailinator',
            'throwaway', 'fake', 'trashmail', 'yopmail'
        ];
        
        if (dominiosTemporarios.some(dom => emailLower.includes(dom))) {
            resultado.avisos.push('Domínio comumente usado para e-mails temporários');
        }

        // Verifica formato com sinal de +
        if (email.includes('+') && !this.config.permitirPlus) {
            resultado.avisos.push('E-mail contém sinal de "+" (plus addressing)');
        }

        // Verifica domínios de provedores gratuitos comuns
        const provedoresGratuitos = ['gmail.com', 'hotmail.com', 'outlook.com', 'yahoo.com'];
        if (provedoresGratuitos.some(prov => emailLower.endsWith('@' + prov))) {
            resultado.sugestoes.push('Para melhor delivery, considere usar e-mail corporativo');
        }

        // Verifica se é um e-mail de teste
        if (emailLower.includes('test') || emailLower.includes('example')) {
            resultado.avisos.push('E-mail parece ser de teste');
        }
    }

    /**
     * Validação em tempo real para campos de formulário
     * @param {string} email - E-mail a ser validado
     * @returns {Object} - Resultado simplificado para UI
     */
    validarParaFormulario(email) {
        const validacao = this.validarEmail(email, 'medio');
        
        return {
            valido: validacao.valido,
            mensagem: validacao.erros.length > 0 ? validacao.erros[0] : 
                     validacao.avisos.length > 0 ? validacao.avisos[0] : 'E-mail válido',
            tipo: validacao.erros.length > 0 ? 'erro' : 
                  validacao.avisos.length > 0 ? 'aviso' : 'sucesso',
            detalhes: validacao.detalhes
        };
    }

    /**
     * Formata o e-mail para exibição (protege dados sensíveis)
     * @param {string} email - E-mail a ser formatado
     * @returns {string} - E-mail formatado
     */
    formatarExibicao(email) {
        if (!this.validarSintaxe(email)) {
            return email;
        }

        const [local, dominio] = email.split('@');
        
        // Protege parte do e-mail para exibição
        if (local.length <= 2) {
            return `${local}@${dominio}`;
        }
        
        const parteVisivel = local.substring(0, 2);
        const parteOculta = '*'.repeat(local.length - 2);
        
        return `${parteVisivel}${parteOculta}@${dominio}`;
    }

    /**
     * Configura o validador
     * @param {Object} configuracoes - Novas configurações
     */
    configurar(configuracoes) {
        this.config = { ...this.config, ...configuracoes };
    }

    /**
     * Adiciona domínios à lista de inválidos
     * @param {Array} dominios - Array de domínios a serem bloqueados
     */
    adicionarDominiosInvalidos(dominios) {
        this.dominiosInvalidos = [...new Set([...this.dominiosInvalidos, ...dominios])];
    }
}

// Instância global para uso direto
const validadorEmail = new ValidadorEmail();

// Export para módulos
if (typeof module !== 'undefined' && module.exports) {
    module.exports = { ValidadorEmail, validadorEmail };
}

// Exemplo de uso:
/*
// Uso básico
const resultado = validadorEmail.validarEmail('usuario@exemplo.com');
console.log(resultado.valido); // true ou false

// Uso em formulário
const validacaoUI = validadorEmail.validarParaFormulario('teste@exemplo.com');
console.log(validacaoUI.mensagem);

// Configurar
validadorEmail.configurar({
    permitirPlus: false,
    validarDominio: true
});
*/