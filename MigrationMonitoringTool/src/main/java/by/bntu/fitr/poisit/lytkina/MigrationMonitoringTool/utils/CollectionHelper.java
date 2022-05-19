package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectionHelper {
    public static <F, T> List<T> mapCollect(Collection<F> from, Function<F, T> map) {
        return from.stream().map(map).collect(Collectors.toList());
    }
}
