import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Restaurante implements EnumsFuncionarios{
    static Scanner scanner = new Scanner(System.in);
    static SleepMetod sleep = new SleepMetod();
    static SoundTrack sound = new SoundTrack();

    public static void main(String[] args){
        sound.MusicFundo();

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        ArrayList<Itens> itens = new ArrayList<>();
        ArrayList<Pedido> pedidos = new ArrayList<>();
        ArrayList<Ingredientes> ingredientes = new ArrayList<>();

        
        try {
            // Bebida instances
            itens.add(new Bebida("Nome1", 10.0, 5.0, itens, "TipoEmbalagem1", "TamanhoEmbalagem1"));
            itens.add(new Bebida("Nome2", 15.0, 8.0, itens, "TipoEmbalagem2", "TamanhoEmbalagem2"));

            // Sobremesa instances
            itens.add(new Sobremesa("Nome1", "Descricao1", 20.0, 12.0, 7.0,  13000.0,itens));
            itens.add(new Sobremesa("Nome2", "Descricao2", 25.0, 18.0, 10.0, 1200.0, itens));

            // PratoPrincipal instances
            itens.add(new PratoPrincipal("Nome1", "Descricao1", 30.0, 25.0, 15.0, itens));
            itens.add(new PratoPrincipal("Nome2", "Descricao2", 35.0, 30.0, 20.0, itens));
        } catch (ErroCodigoException e) {
            System.out.println(e);
        }

        // Cozinheiro instances
        funcionarios.add(new Cozinheiro("Nome1", "Endereco1", EstadoCivil.DIVORCIADA, 4, "CPF1", "RG1"));
        funcionarios.add(new Cozinheiro("Nome2", "Endereco2", EstadoCivil.CASADA, 3, "CPF2", "RG2"));

        // Garcom instances
        funcionarios.add(new Garcom("Nome1", "Endereco1", EstadoCivil.DIVORCIADA, 2, "CPF1", "RG1", 1500, DiaSemana.SEXTA));
        funcionarios.add(new Garcom("Nome2", "Endereco2", EstadoCivil.CASADA, 1, "CPF2", "RG2", 1800, DiaSemana.DOMINGO));

        // Ingredientes instances
        ingredientes.add(new Ingredientes("NomeIngrediente1", "Quantidade1"));
        ingredientes.add(new Ingredientes("NomeIngrediente2", "Quantidade2"));

        int opcao;

        do {
            System.out.println("BEM-VINDO AO RESTAURANTE\n");
            System.out.println("============== Menu ==============");
            System.out.println("1. Cadastrar Funcionário");
            System.out.println("2. Cadastrar Ingrediente");
            System.out.println("3. Cadastrar Item");
            System.out.println("4. Cadastrar Pedido");
            System.out.println("0. Sair");
            System.out.println("==================================");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    funcionarios.add(cadastrarFuncionario());
                    sound.MusicConcluido();
                    funcionarios.get(funcionarios.size() - 1).mostrarFuncionario();
                    break;
                case 2:
                    ingredientes.add(cadastrarIngrediente());
                    ingredientes.get(ingredientes.size() - 1).mostrarIngrediente();
                    break;
                case 3:
                    try {
                        itens.add(cadastrarItens(itens, ingredientes));

                        if(itens.get(itens.size() - 1) instanceof Prato){
                            String c;
                            int op;
                            do{
                                do {
                                    System.out.println("\nLista ingredientes: ");
                                    for (int index = 0; index < ingredientes.size(); index++) {
                                        System.out.println(index + "." + ingredientes.get(index).getNome());
                                    }

                                    System.out.println("Digite o index do ingrediente para adicionar: ");
                                    op = scanner.nextInt();
                                    scanner.nextLine();

                                    ingredientes.get(op).mostrarIngrediente();

                                    System.out.println("Confirmar ingrediente? s/n");
                                    c = scanner.nextLine();
                                } while (!c.equals("s"));

                                ((Prato) itens.get(itens.size() - 1)).adicionarIngredientes(ingredientes.get(op));

                                System.out.println("Quer adicionar outro ingrediente? s/n");
                                c = scanner.nextLine();
                            } while(!c.equals("n"));
                        }

                        itens.get(itens.size() - 1).mostrarItem();
                    } catch (ErroCodigoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        pedidos.add(cadastrarPedido(funcionarios));
                        
                        String c;
                        int op;
                        do
                        {
                            do {
                                System.out.println("\nLista itens: ");
                                for (int index = 0; index < itens.size(); index++) {
                                    System.out.println(index + "." + itens.get(index).getNome());
                                }

                                System.out.println("Digite a opcao do item que deseja adicionar: ");
                                op = scanner.nextInt();
                                scanner.nextLine();

                                itens.get(op).mostrarItem();

                                System.out.println("Confirmar item? s/n");
                                c = scanner.nextLine();
                            } while(!c.equals("s"));
                            
                            System.out.println("Digite a quantidade deseja adicionar: ");
                            int qtd = scanner.nextInt();
                            scanner.nextLine();

                            ItemPedido item = new ItemPedido(itens.get(op), qtd);
                            pedidos.get(pedidos.size() - 1).adicionarItem(item);

                            System.out.println("Deseja adicionar outro item? s/n");
                            c = scanner.nextLine();
                        } while(c.equals("s"));

                        //pedidos.get(pedidos.size() - 1).calcularValorTotal();

                        System.out.println("Digite a forma de pagamento: ");
                        String formaPag = scanner.nextLine();
                        pedidos.get(pedidos.size() - 1).setFormaPag(formaPag);
                        pedidos.get(pedidos.size() - 1).confirmarPagamento();

                        pedidos.get(pedidos.size() - 1).mostrarPedido();
                    } catch (PagamentoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 0:
                    System.out.println("Saindo do programa. Até mais!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
        
    }

    public static Funcionario cadastrarFuncionario() {
        System.out.println("CADASTRAMENTO DE FUNCIONÁRIOS\n");

        sleep.Sleeping(1000);

        System.out.println("Iniciando cadastro...\n");

        sleep.Sleeping(1000);

        String x;
        do {
            System.out.println("Deseja cadastrar um garçom ou um cozinheiro?");
            x = scanner.nextLine();
            x = x.toUpperCase();

            if (!x.equals("GARCOM") && !x.equals("GARÇOM") && !x.equals("COZINHEIRO")) {
                System.out.println("Entrada inválida!\n");
            }
        } while(!x.equals("GARCOM") && !x.equals("GARÇOM") && !x.equals("COZINHEIRO"));

        System.out.print("\nDigite o nome do funcionário: ");
        String nome = scanner.nextLine();

        String cpf;
        do {
            System.out.print("\nDigite o CPF do funcionário: ");
            cpf = scanner.nextLine();

            if(!ValidaCPF.validaCPF(cpf)){
                System.out.println("CPF inválido!");
            }
        } while (!ValidaCPF.validaCPF(cpf));

        System.out.print("\nDigite o RG do funcionário: ");
        String rg = scanner.nextLine();

        System.out.print("\nDigite o endereço do funcionário (Rua ..., número): ");
        String end = scanner.nextLine();

        System.out.print("\nDigite o estado civil do funcionário: ");
        String ec = scanner.nextLine();
        ec = ec.toUpperCase();

        System.out.print("\nDigite o número da carteira de trabalho do funcionário: ");
        int nroC = scanner.nextInt();
        scanner.nextLine();

        if(x.equals("GARCOM") || x.equals("GARÇOM")){
            System.out.println("\nDigite o salário base do garçom: ");
            double sal = scanner.nextDouble();
            scanner.nextLine();

            System.out.println("\nSelecione o dia de folga do garçom: ");
            System.out.println("1. Domingo | 2.Segunda-Feira | 3.Terça-feira | 4.Quarta-feira | 5.Quinta-feira | 6.Sexta-feira | 7.Sábado");

            int folga = scanner.nextInt();

            return new Garcom(nome, end, converterEstadoCivil(ec), nroC, cpf, rg, sal, converterFolga(folga));
        }

        return new Cozinheiro(nome, end, converterEstadoCivil(ec), nroC, cpf, rg);
    }

    public static EnumsFuncionarios.EstadoCivil converterEstadoCivil(String ec){
        if(Objects.equals(ec, "SOLTEIRO") || Objects.equals(ec, "SOLTEIRA")){
            return EstadoCivil.SOLTEIRA;
        }
        if(Objects.equals(ec, "CASADO") || Objects.equals(ec, "CASADA")){
            return EstadoCivil.CASADA;
        }
        if(Objects.equals(ec, "DIVORCIADO") || Objects.equals(ec, "DIVORCIADA")){
            return EstadoCivil.DIVORCIADA;
        }
        if(Objects.equals(ec, "SEPARADO") || Objects.equals(ec, "SEPARADA")){
            return EstadoCivil.SEPARADA;
        }
        if(Objects.equals(ec, "VIUVO") || Objects.equals(ec, "VIUVA") || Objects.equals(ec, "VIÚVO") || Objects.equals(ec, "VIÚVA")){
            return EstadoCivil.VIUVA;
        }
        else {
            return null;
        }
    }

    public static EnumsFuncionarios.DiaSemana converterFolga(int folga){
        return switch (folga){
            case 1 -> DiaSemana.DOMINGO;
            case 2 -> DiaSemana.SEGUNDA;
            case 3 -> DiaSemana.TERCA;
            case 4 -> DiaSemana.QUARTA;
            case 5 -> DiaSemana.QUINTA;
            case 6 -> DiaSemana.SEXTA;
            case 7 -> DiaSemana.SABADO;
            default -> null;
        };
    }

    public static Ingredientes cadastrarIngrediente(){
        System.out.println("CADASTRAMENTO DE INGREDIENTES\n");

        sleep.Sleeping(1000);

        System.out.println("Iniciando cadastro...\n");

        sleep.Sleeping(1000);

        System.out.print("\nDigite o nome do ingrediente: ");
        String nome = scanner.nextLine();
        
        System.out.print("\nDigite a quantidade do ingrediente: (Ex: 1kg, 1L, 1 unidade)");
        String qtd = scanner.nextLine();

        return new Ingredientes(nome, qtd);
    }

    public static Itens cadastrarItens(ArrayList<Itens> itens, ArrayList<Ingredientes> ingredientes) throws ErroCodigoException{
        System.out.println("CADASTRAMENTO DE ITENS\n");
        Itens item = null;

        sleep.Sleeping(1000);

        System.out.println("Iniciando cadastro...\n");

        sleep.Sleeping(1000);

        String x;
        do {
            System.out.println("Deseja cadastrar um prato principal, sobremesa ou bebida?");
            x = scanner.nextLine();
            x = x.toUpperCase();

            if (!x.equals("BEBIDA") && !x.equals("PRATO PRINCIPAL") && !x.equals("SOBREMESA")) {
                System.out.println("Entrada inválida!\n");
            }
        } while(!x.equals("BEBIDA") && !x.equals("PRATO PRINCIPAL") && !x.equals("SOBREMESA"));

        System.out.print("Nome do item: ");
        String nome = scanner.nextLine();

        System.out.print("Preço unitário: ");
        double precoUnitario = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Preço de custo: ");
        double precoCusto = scanner.nextDouble();
        scanner.nextLine();

        if (x.equals("PRATO PRINCIPAL") || x.equals("SOBREMESA")) {
            System.out.print("Descrição: ");
            String descricao = scanner.nextLine();

            System.out.print("Tempo de preparo: ");
            double tempoPreparo = scanner.nextDouble();
            scanner.nextLine();

            if (x.equals("PRATO PRINCIPAL")) {
                item = new PratoPrincipal(nome, descricao, tempoPreparo, precoUnitario, precoCusto, itens);
            } else{
                System.out.print("Número de calorias: ");
                double nroCal = scanner.nextDouble();
                scanner.nextLine();

                item = new Sobremesa(nome, descricao, tempoPreparo, precoUnitario, precoCusto, nroCal, itens);
            }
        } else {
            System.out.print("Tipo de embalagem: ");
            String tipoEmbalagem = scanner.nextLine();

            System.out.print("Tamanho da embalagem: ");
            String tamanhoEmbalagem = scanner.nextLine();

            item = new Bebida(nome, precoUnitario, precoCusto, itens, tipoEmbalagem, tamanhoEmbalagem);
        }

        return item;
    }

    public static Pedido cadastrarPedido(ArrayList<Funcionario> funcionarios){
        System.out.println("CADASTRAMENTO DE PEDIDOS\n");
        Garcom garcom = null;
        Cozinheiro cozinheiro = null;

        sleep.Sleeping(1000);

        System.out.println("Iniciando cadastro...\n");

        sleep.Sleeping(1000);

        String c;
        int op;
        do 
        {
            do {
                System.out.println("\nLista de garçons: ");
                for (int index = 0; index < funcionarios.size(); index++) {
                    if(funcionarios.get(index) instanceof Garcom){
                        System.out.println(index + "." + funcionarios.get(index).getNome());
                    }
                }
                System.out.println("Digite o codigo do garçom: ");
                op = scanner.nextInt();
                scanner.nextLine();

                funcionarios.get(op).mostrarFuncionario();

                System.out.println("Confirmar o garçom? s/n");
                c = scanner.nextLine();
            } while (!c.equals("s"));

            if (funcionarios.get(op) instanceof Garcom) {
                garcom = (Garcom) funcionarios.get(op);
            } else {
                garcom = null;
            }
            
        } while (garcom == null);

        do 
        {
            do {
                System.out.println("\nLista de cozinheiros: ");
                for (int index = 0; index < funcionarios.size(); index++) {
                    if(funcionarios.get(index) instanceof Cozinheiro){
                        System.out.println(index + "." + funcionarios.get(index).getNome());
                    }
                }
                System.out.println("Digite o codigo do garçom: ");
                op = scanner.nextInt();
                scanner.nextLine();

                funcionarios.get(op).mostrarFuncionario();

                System.out.println("Confirmar o cozinheiro? s/n");
                c = scanner.nextLine();
            } while (!c.equals("s"));

            if (funcionarios.get(op) instanceof Cozinheiro) {
                cozinheiro = (Cozinheiro) funcionarios.get(op);
            } else {
                cozinheiro = null;
            }
            
        } while (cozinheiro == null);

        return new Pedido(garcom, cozinheiro);
    }

    /*public static void fecharMes(){

    }*/
}
