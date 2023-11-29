import java.lang.IllegalArgumentException;

public class Garcom extends Funcionario {
    private double salarioBase;
    private EnumsFuncionarios.DiaSemana diaFolga;
    private int advertencias = 0;
    private int nroPedidosAtendidos = 0;
    
    private static double limite = 3;

    public Garcom(String nome, String endereco, EnumsFuncionarios.EstadoCivil estadoCivil, int nroCarteiraTrabalho, String cpf, String rg, double salarioBase, EnumsFuncionarios.DiaSemana diaFolga){
        super(nome, endereco, estadoCivil, nroCarteiraTrabalho, cpf, rg);
        this.salarioBase = salarioBase;
        this.diaFolga = diaFolga;
    }

    public double calcularSalario(){
        return salarioBase;
    }

    public double calcularSalario(int nroPedidos){
        if(nroPedidos < limite){
            return salarioBase;
        } else {
            return salarioBase + 2000;
        }
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(float salarioBase) {
        if(salarioBase < 1320){
            throw new IllegalArgumentException("Valor abaixo do salário mínimo!");
        } else {
            this.salarioBase = salarioBase;
        }
    }

    public EnumsFuncionarios.DiaSemana getDiaFolga() {
        return diaFolga;
    }

    public void setDiaFolga(EnumsFuncionarios.DiaSemana diaFolga) {
        this.diaFolga = diaFolga;
    }

    public void mostrarFuncionario() {
        super.mostrarFuncionario();
        System.out.println("Numero de pedidos atendidos: " + nroPedidosAtendidos);
        System.out.println("Advertências: " + advertencias);
        System.out.println("Salario base: " + salarioBase);
        System.out.println("Dia de folga: " + mostrarDia());
    }

    public String mostrarDia(){
        switch (diaFolga) {
            case DOMINGO:
                return "Domingo";
            case SEGUNDA:
                return "Segunda-feira";
            case TERCA:
                return "Terça-feira";
            case QUARTA:
                return "Quarta-feira";
            case QUINTA:
                return "Quinta-feira";
            case SEXTA:
                return "Sexta-feira";
            case SABADO:
                return "Sábado";
            default:
                return "Dia inválido";
        }
    }

    public void registrarPedido(){
        nroPedidosAtendidos++;
    }

    public int getNroPedidosAtendidos(){
        return nroPedidosAtendidos;
    }

    public void verificarPerformance(int nroPedidosTotais){
        if(this.nroPedidosAtendidos < (nroPedidosTotais * 0.05)){
            advertencias++;
        } else {
            advertencias = 0;
        }
    }

    public boolean verificarDemissao(){
        if(advertencias >= 3){
            return true;
        } else {
            return false;
        }
    }
}

