package include;

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

    public String dataPadraoBanco(String data) {
        String[] dataPartes = data.split("/"); //separo o dado obtido no input
        if (dataPartes.length == 3) {
            return dataPartes[2] + "-" + dataPartes[1] + "-" + dataPartes[0]; // yyyy/MM/dd
        } else {
            System.out.println("Formato de data inválido");
            return null;
        }
    }

    public String dataPadraoBR(String data) {
        String[] dataPartes = data.split("-"); //separo o dado obtido no input
        if (dataPartes.length == 3) {
            return dataPartes[2] + "/" + dataPartes[1] + "/" + dataPartes[0];
        } else {
            System.out.println("Formato de data inválido");
            return null;
        }
    }
}
