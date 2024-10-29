import java.util.Random;

public class MergeSort {
    private static int copiaContador = 0;   // Contador de cópias (aproximadamente para "trocas")
    private static int iteracaoContador = 0; // Contador de iterações

    public static void main(String[] args) {
        int[] tamanhos = {1000, 10000, 100000, 500000, 1000000};
        int rodadas = 5;

        for (int tamanho : tamanhos) {
            System.out.println("Tamanho do vetor: " + tamanho);
            long tempoTotal = 0;
            int copiaTotal = 0;
            int iteracaoTotal = 0;

            for (int i = 0; i < rodadas; i++) {
                int[] vetor = gerarVetorAleatorio(tamanho, i); // Gerar vetor com seed para replicação

                // Resetar contadores
                copiaContador = 0;
                iteracaoContador = 0;

                // Medir tempo de execução
                long inicio = System.nanoTime();
                mergeSort(vetor, 0, vetor.length - 1);
                long fim = System.nanoTime();

                long tempoExecucao = fim - inicio;
                tempoTotal += tempoExecucao;
                copiaTotal += copiaContador;
                iteracaoTotal += iteracaoContador;

                System.out.printf("Rodada %d: Tempo = %d ns, Cópias = %d, Iterações = %d%n",
                        i + 1, tempoExecucao, copiaContador, iteracaoContador);
            }

            System.out.printf("Média para tamanho %d -> Tempo: %d ns, Cópias: %d, Iterações: %d%n%n",
                    tamanho, tempoTotal / rodadas, copiaTotal / rodadas, iteracaoTotal / rodadas);
        }
    }

    // Função principal do Merge Sort
    private static void mergeSort(int[] vetor, int inicio, int fim) {
        if (inicio < fim) {
            iteracaoContador++;  // Contando as chamadas recursivas
            int meio = (inicio + fim) / 2;
            mergeSort(vetor, inicio, meio);
            mergeSort(vetor, meio + 1, fim);
            merge(vetor, inicio, meio, fim);
        }
    }

    // Função para combinar duas metades
    private static void merge(int[] vetor, int inicio, int meio, int fim) {
        int[] auxiliar = new int[fim - inicio + 1];
        int i = inicio, j = meio + 1, k = 0;

        while (i <= meio && j <= fim) {
            if (vetor[i] <= vetor[j]) {
                auxiliar[k++] = vetor[i++];
            } else {
                auxiliar[k++] = vetor[j++];
            }
            copiaContador++;  // Contando cada cópia para o vetor auxiliar
        }

        while (i <= meio) {
            auxiliar[k++] = vetor[i++];
            copiaContador++;
        }

        while (j <= fim) {
            auxiliar[k++] = vetor[j++];
            copiaContador++;
        }

        for (i = inicio, k = 0; i <= fim; i++, k++) {
            vetor[i] = auxiliar[k];
            copiaContador++;  // Contando a cópia de volta para o vetor original
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