import java.util.Random;

public class QuickSort {
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
                quickSort(vetor, 0, vetor.length - 1);
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

    // Função principal do Quick Sort
    private static void quickSort(int[] vetor, int inicio, int fim) {
        if (inicio < fim) {
            iteracaoContador++; // Contando a chamada recursiva
            int pivoIndex = partition(vetor, inicio, fim);
            quickSort(vetor, inicio, pivoIndex - 1);
            quickSort(vetor, pivoIndex + 1, fim);
        }
    }

    // Função para particionar o vetor
    private static int partition(int[] vetor, int inicio, int fim) {
        int pivo = vetor[fim]; // Escolhendo o último elemento como pivô
        int i = inicio - 1;

        for (int j = inicio; j < fim; j++) {
            iteracaoContador++; // Contando iterações
            if (vetor[j] <= pivo) {
                i++;
                swap(vetor, i, j);
                copiaContador++; // Contando a cópia para o vetor
            }
        }
        swap(vetor, i + 1, fim);
        copiaContador++; // Contando a cópia para o vetor
        return i + 1;
    }

    // Função para trocar elementos e contar as cópias
    private static void swap(int[] vetor, int i, int j) {
        int temp = vetor[i];
        vetor[i] = vetor[j];
        vetor[j] = temp;
        copiaContador++; // Contando a cópia
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
