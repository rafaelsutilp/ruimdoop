package randoop.bin;

public class Endereco {
    private String cidade;
    private String bairro;
    private String rua;
    private int numero;
    private long CEP;

    public Endereco(
        String cidade,
        String bairro,
        String rua,
        int numero,
        long CEP
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

    public long getCEP(){
        return this.CEP;
    }

    public void setCidade(String cidade){
        this.cidade = cidade;
    }

    public void setBairro(String bairro){
        this.bairro = bairro;
    }

    public void setRua(String rua){
        this.rua = rua;
    }

    public void setNumero(int numero){
        this.numero = numero;
    }

    public void setCEP(long CEP){
        this.CEP = CEP;
    }
}
