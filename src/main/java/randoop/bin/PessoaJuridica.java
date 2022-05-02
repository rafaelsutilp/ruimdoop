package randoop.bin;

public class PessoaJuridica extends Pessoa{
    private String CNPJ;

    public PessoaJuridica(String nome, Endereco endereco, String cnpj){
        this.nome = nome;
        this.endereco = endereco;
        this.CNPJ = cnpj;
    }

    public String getCNPJ(){
        return this.CNPJ;
    }
    public void setCNPJ(String cnpj){
        this.CNPJ = cnpj;
    }
}
