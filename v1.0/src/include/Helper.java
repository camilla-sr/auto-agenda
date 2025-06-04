package include;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
    /*função para validar todas as entradas numéricas,
    para impedir que o sistema quebre caso a entrada não seja
    válida */
    public Integer isNumeric(String valor) {
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // Novos padrões de código para o formato das datas
    public String dataPadraoBanco(String data) {
        try {            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // aqui recebo a data no padrão informado pelo usuário      
            sdf.setLenient(false); // Não permite datas doidas como 32/13/2024
            Date formatoBanco = sdf.parse(data); //salva a data formatada numa variável do tipo data

            // Retorna a data formatada no padrão yyyy-MM-dd
            SimpleDateFormat formatoBancoSaida = new SimpleDateFormat("yyyy-MM-dd");
            return formatoBancoSaida.format(formatoBanco);
        } catch (ParseException e) {            
            return null; // Se a data for inválida, retorna null
        }
    }

    public String dataPadraoBR(String data) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //recebo a data no padrão do banco
            sdf.setLenient(false); // Não permite datas absurdas
            Date formatoBR = sdf.parse(data); //adiciona a data para a variável válida do tipo data

            // Converte para o formato dd/MM/yyyy
            SimpleDateFormat formatoBRSaída = new SimpleDateFormat("dd/MM/yyyy");
            return formatoBRSaída.format(formatoBR);
        } catch (ParseException e) {
            return null; // Se a data for inválida, retorna null
        }
    }

}
