package randoop.bin;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class PessoaFisica extends Pessoa{
    private String CPF;
    private LocalDate dataNascimento;
    private double salario;
    private char letraFavorita;

    public PessoaFisica(
        String nome,
        Endereco endereco,
        String cpf,
        LocalDate dataNascimento,
        double salario,
        char letraFavorita
    ){
        this.nome = nome;
        this.endereco = endereco;
        this.CPF = cpf;
        this.dataNascimento = dataNascimento;
        this.salario = salario;
        this.letraFavorita = letraFavorita;
    }

    public double calcularSalarioLiquido(){
        if(this.salario<1900)
            return this.salario;
        if(this.salario<2827)
            return this.salario*0.925;
        if (this.salario<3751)
            return this.salario*0.85;
        if(this.salario<4664)
            return this.salario*0.775;
        return this.salario*0.725;
    }

    public String getCPF(){
        return this.CPF;
    }

    public void setCPF(String cpf){
        this.CPF = cpf;
    }

    public double getSalario(){
        return this.salario;
    }

    public char getLetraFavorita(){
        return this.letraFavorita;
    }

    public void setSalario(double salario){
        this.salario = salario;
    }
    
    public void setLetraFavorita(char letra){
        this.letraFavorita = letra;
    }

    public int getIdade(){
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }
}
