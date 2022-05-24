package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution;

import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {
        testExecutionGraph();
    }

    public static void testFutures() {
        Runnable A = getFuture("A");
        Runnable B = getFuture("B");
        Runnable C = getFuture("C");
        Runnable D = getFuture("D");
        Runnable E = getFuture("E");
        Runnable F = getFuture("F");
        Runnable G = getFuture("G");
        Runnable I = getFuture("I");

        CompletableFuture<Void> Af = CompletableFuture.runAsync(A);
        CompletableFuture<Void> Bf = CompletableFuture.runAsync(B);

        CompletableFuture<Void> Cf = Af.thenRun(C);
        CompletableFuture<Void> Df = CompletableFuture.allOf(Af, Bf).thenRun(D);
        CompletableFuture<Void> Gf = Bf.thenRun(G);
        CompletableFuture<Void> Ff = Cf.thenRun(F);
        CompletableFuture<Void> Ef = CompletableFuture.allOf(Cf, Df).thenRun(E);
        CompletableFuture<Void> If = Ef.thenRun(I);

        CompletableFuture.allOf(
            If,
            Ff,
            Gf
        );
    }

    public static Runnable getFuture(String value) {
        return () -> {
            System.out.println(value);
        };
    }

    public static void testExecutionGraph() {
        ExecutionGraph graph = new GraphBuilder().fakeBuildGraph();
        CompletableFuture<Void> future = graph.run();
    }
}
