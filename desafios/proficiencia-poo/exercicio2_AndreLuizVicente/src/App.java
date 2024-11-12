public class App {
    public static void main(String[] args) {
        Vetor<Pessoa> pessoas = inicializarPessoas();
        Vetor<Cidade> cidades = inicializarCidades();

        String[] nomesParaProcurar = {"Maria Aparecida Lemos", "Félix Moreira", "Jander Pereira Borges", "Milena Alves"};
        processarConsultas(pessoas, cidades, nomesParaProcurar);
    }

    private static Vetor<Pessoa> inicializarPessoas() {
        Pessoa[] pessoasArray = {
            new Pessoa("Maria Aparecida Lemos", "F", "Uberlândia"),
            new Pessoa("Félix Moreira", "M", "Cuiabá"),
            new Pessoa("Jander Pereira Borges", "M", "Juazeiro do Norte"),
            new Pessoa("Milena Alves", "F", "")
        };
        return new Vetor<>(pessoasArray, pessoasArray.length);
    }

    private static Vetor<Cidade> inicializarCidades() {
        Cidade[] cidadesArray = {
            new Cidade("Uruguaiana", "uruguaianense", "Rio Grande do Sul"),
            new Cidade("Uberlândia", "uberlandense", "Minas Gerais"),
            new Cidade("Cuiabá", "cuiabano/cuiabana", "Mato Grosso"),
            new Cidade("Manaus", "manauara", "Amazonas")
        };
        return new Vetor<>(cidadesArray, cidadesArray.length);
    }

    private static void processarConsultas(Vetor<Pessoa> pessoas, Vetor<Cidade> cidades, String[] nomesParaProcurar) {
        for (String nomeProcurado : nomesParaProcurar) {
            Pessoa pessoaEncontrada = encontrarPessoa(pessoas, nomeProcurado);
            if (pessoaEncontrada != null) {
                Cidade cidadeEncontrada = encontrarCidade(cidades, pessoaEncontrada.getNaturalidade());
                imprimirResultado(pessoaEncontrada, cidadeEncontrada);
            } else {
                System.out.printf("%s não foi encontrado.%n", nomeProcurado);
            }
        }
    }

    private static Pessoa encontrarPessoa(Vetor<Pessoa> pessoas, String nomeProcurado) {
        for (Pessoa pessoa : pessoas.getVetor()) {
            if (pessoa.getNome().equalsIgnoreCase(nomeProcurado)) {
                return pessoa;
            }
        }
        return null;
    }

    private static Cidade encontrarCidade(Vetor<Cidade> cidades, String nomeCidade) {
        for (Cidade cidade : cidades.getVetor()) {
            if (cidade.getNome().equalsIgnoreCase(nomeCidade)) {
                return cidade;
            }
        }
        return null;
    }

    private static void imprimirResultado(Pessoa pessoa, Cidade cidade) {
        if (cidade != null) {
            String artigo = pessoa.getSexo().equalsIgnoreCase("F") ? "A" : "O";
            String adjetivo = obterAdjetivoPorGenero(cidade.getAdjetivo(), pessoa.getSexo());

            System.out.printf("%s %s %s nasceu em %s - %s.%n",
                    artigo,
                    capitalize(adjetivo),
                    pessoa.getNome(),
                    cidade.getNome(),
                    cidade.getEstado());
        } else {
            System.out.printf("%s nasceu em cidade desconhecida.%n", pessoa.getNome());
        }
    }

    private static String obterAdjetivoPorGenero(String adjetivo, String sexo) {
        if (adjetivo.contains("/")) {
            return sexo.equalsIgnoreCase("F") ? adjetivo.split("/")[1] : adjetivo.split("/")[0];
        }
        return adjetivo;
    }

    private static String capitalize(String palavra) {
        return palavra.substring(0, 1).toUpperCase() + palavra.substring(1).toLowerCase();
    }
}
