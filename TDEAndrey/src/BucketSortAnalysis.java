import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class BucketSortAnalysis {
    private static long copiaContador = 0;   // Contador de cópias (para simular "trocas")
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
                long[] vetor = gerarVetorAleatorio(tamanho, i); // Gerar vetor com seed para replicação

                // Resetar contadores
                copiaContador = 0;
                iteracaoContador = 0;

                // Medir tempo de execução
                long inicio = System.nanoTime();
                bucketSort(vetor);
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

    // Função para realizar Bucket Sort
    private static void bucketSort(long[] vetor) {
        int numeroBuckets = (int) Math.sqrt(vetor.length); // Define o número de buckets baseado no tamanho do vetor
        ArrayList<Long>[] buckets = new ArrayList[numeroBuckets];

        // Inicializar buckets
        for (int i = 0; i < numeroBuckets; i++) {
            buckets[i] = new ArrayList<>();
        }

        // Encontrar o valor máximo para escalar os valores ao bucket correto
        long maxValue = encontrarMaximo(vetor);

        // Distribuir elementos para os buckets apropriados
        for (long num : vetor) {
            iteracaoContador++;  // Contando cada iteração de distribuição
            int bucketIndex = (int) ((double) num / maxValue * (numeroBuckets - 1));
            buckets[bucketIndex].add(num);
            copiaContador++;  // Contando cada cópia para o bucket
        }

        // Ordenar cada bucket individualmente
        int indice = 0;
        for (ArrayList<Long> bucket : buckets) {
            iteracaoContador++;  // Contando cada iteração de bucket
            Collections.sort(bucket);
            for (long num : bucket) {
                vetor[indice++] = num;
                copiaContador++;  // Contando cada cópia de volta ao vetor original
            }
        }
    }

    // Função para encontrar o valor máximo no vetor
    private static long encontrarMaximo(long[] vetor) {
        long max = vetor[0];
        for (int i = 1; i < vetor.length; i++) {
            if (vetor[i] > max) {
                max = vetor[i];
            }
        }
        return max;
    }

    // Função para gerar vetor aleatório com seed
    private static long[] gerarVetorAleatorio(int tamanho, int seed) {
        Random random = new Random(seed);
        long[] vetor = new long[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = random.nextInt(100000); // Limita os valores para comparação com o valor máximo
        }
        return vetor;
    }
}
