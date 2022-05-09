package randoop.bin;

public class Conta {
    private PessoaFisica cliente;
    private double saldo;
    private short agencia;
    private boolean poupanca;


    public Conta(PessoaFisica cliente, short agencia, boolean poupanca){
        this.cliente = cliente;
        this.saldo = 100;
        this.agencia = agencia;
        this.poupanca = poupanca;
    }

    public boolean Sacar(double valor){
        if(valor < 0) return false;
        if(this.saldo < valor) return false;
        this.saldo -= valor;
        return true;
    }

    public boolean Depositar(double valor){
        if(valor < 0) return false;
        this.saldo += valor;
        return true;
    }

    public boolean Transferir(double valor, Conta destino){
        if(valor < 0) return false;
        if(this.saldo < valor) return false;
        this.saldo -= valor;
        return destino.Depositar(valor);
    }

    public double getSaldo(){
        return this.saldo;
    }

    public boolean getPoupanca(){
        return this.poupanca;
    }

    public short getAgencia(){
        return this.agencia;
    }

    public void setAgencia(short agencia){
        this.agencia = agencia;
    }

    public void setPoupanca(boolean poupanca){
        this.poupanca = poupanca;
    }
}
