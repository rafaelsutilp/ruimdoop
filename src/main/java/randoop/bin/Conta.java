package randoop.bin;

public class Conta {
    private Pessoa cliente;
    private double saldo;


    public Conta(Pessoa cliente){
        this.cliente = cliente;
        this.saldo = 100;
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
}
