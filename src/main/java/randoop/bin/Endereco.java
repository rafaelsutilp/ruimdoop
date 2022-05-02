package randoop.bin;

public class Endereco {
    private String cidade;
    private String bairro;
    private String rua;
    private int numero;
    private String CEP;

    public Endereco(
        String cidade,
        String bairro,
        String rua,
        int numero,
        String CEP
    ){
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.CEP = CEP;
    }
    
    public String getCidade(){
        return this.cidade;
    }

    public String getBairro(){
        return this.bairro;
    }

    public String getRua(String entrada){
        return this.rua;
    }

    public int getNumero(){
        return this.numero;
    }

    public String getCEP(){
        return this.CEP;
    }
}
