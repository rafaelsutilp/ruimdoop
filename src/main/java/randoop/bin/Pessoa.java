package randoop.bin;

abstract public class Pessoa {
    protected String nome;
    protected Endereco endereco;

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome, Endereco endereco){
        this.nome = nome;
        this.endereco = endereco;
    }
}
