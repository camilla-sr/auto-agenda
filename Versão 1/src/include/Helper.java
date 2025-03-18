package include;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
    /*fun��o para validar todas as entradas num�ricas,
    para impedir que o sistema quebre caso a entrada n�o seja
    v�lida */
    public Integer isNumeric(String valor) {
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // Novos padr�es de c�digo para o formato das datas
    public String dataPadraoBanco(String data) {
        try {            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // aqui recebo a data no padr�o informado pelo usu�rio      
            sdf.setLenient(false); // N�o permite datas doidas como 32/13/2024
            Date formatoBanco = sdf.parse(data); //salva a data formatada numa vari�vel do tipo data

            // Retorna a data formatada no padr�o yyyy-MM-dd
            SimpleDateFormat formatoBancoSaida = new SimpleDateFormat("yyyy-MM-dd");
            return formatoBancoSaida.format(formatoBanco);
        } catch (ParseException e) {            
            return null; // Se a data for inv�lida, retorna null
        }
    }

    public String dataPadraoBR(String data) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //recebo a data no padr�o do banco
            sdf.setLenient(false); // N�o permite datas absurdas
            Date formatoBR = sdf.parse(data); //adiciona a data para a vari�vel v�lida do tipo data

            // Converte para o formato dd/MM/yyyy
            SimpleDateFormat formatoBRSa�da = new SimpleDateFormat("dd/MM/yyyy");
            return formatoBRSa�da.format(formatoBR);
        } catch (ParseException e) {
            return null; // Se a data for inv�lida, retorna null
        }
    }

}
