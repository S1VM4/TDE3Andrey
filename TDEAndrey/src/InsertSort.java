import java.util.Random;

public class InsertSort {
    private static long copiaContador = 0;   // Contador de cópias (aproximadamente para "trocas")
    private static long iteracaoContador = 0; // Contador de iterações

    public static void main(String[] args) {
        int[] tamanhos = {1000, 10000, 100000, 500000, 1000000};
        int rodadas = 5;

        for (int tamanho : tamanhos) {
            System.out.println("Tamanho do vetor: " + tamanho);
            long tempoTotal = 0;
            long copiaTotal = 0;
            long iteracaoTotal = 0;

            for (int i = 0; i < rodadas; i++) {
                int[] vetor = gerarVetorAleatorio(tamanho, i); // Gerar vetor com seed para replicação

                // Resetar contadores
                copiaContador = 0;
                iteracaoContador = 0;

                // Medir tempo de execução
                long inicio = System.currentTimeMillis();
                insertionSort(vetor);
                long fim = System.currentTimeMillis();

                long tempoExecucao = fim - inicio;
                tempoTotal += tempoExecucao;
                copiaTotal += copiaContador;
                iteracaoTotal += iteracaoContador;

                System.out.printf("Rodada %d: Tempo = %d ms, Cópias = %d, Iterações = %d%n",
                        i + 1, tempoExecucao, copiaContador, iteracaoContador);
            }

            System.out.printf("Média para tamanho %d -> Tempo: %d ms, Cópias: %d, Iterações: %d%n%n",
                    tamanho, tempoTotal / rodadas, copiaTotal / rodadas, iteracaoTotal / rodadas);
        }
    }

    // Função principal do Insertion Sort
    private static void insertionSort(int[] vetor) {
        for (int i = 1; i < vetor.length; i++) {
            int key = vetor[i];
            int j = i - 1;

            // Move os elementos que são maiores que key para uma posição à frente
            while (j >= 0 && vetor[j] > key) {
                vetor[j + 1] = vetor[j];
                j--;
                iteracaoContador++; // Contando iterações
                copiaContador++;     // Contando cópias
            }
            vetor[j + 1] = key;
            copiaContador++; // Contando a cópia do key
            iteracaoContador++; // Contando a atribuição do key
        }
    }

    // Função para gerar vetor aleatório com seed
    private static int[] gerarVetorAleatorio(int tamanho, int seed) {
        Random random = new Random(seed);
        int[] vetor = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = random.nextInt();
        }
        return vetor;
    }
}
