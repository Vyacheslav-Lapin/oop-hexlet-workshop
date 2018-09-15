package ru.vlapin.calc;

import lombok.SneakyThrows;
import lombok.experimental.ExtensionMethod;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@UtilityClass
@ExtensionMethod(Ext.class)
public final class ExtEnum {

    @SneakyThrows
    public static void main(String... __) {
                Paths.get(".")
                        .files()
                        .map(Path::getFileName)
                        .map(Path::toString)
                        .filter(path -> !path.startsWith("."))
                        .sorted()
                        .middle()
                        .plural("s")
                        .toUpperCase()
                        .println();

    }
}

@UtilityClass
final class Ext {

    @NotNull
    @SneakyThrows
    public Stream<Path> files(@NotNull Path path) {
        return stream(Files.newDirectoryStream(path));
    }

    @NotNull
    public <T> Stream<T> stream(@NotNull Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    @Contract(pure = true)
    public <T> T middle(@NotNull Stream<T> stream) {
        List<T> list = stream.collect(Collectors.toList());
        return list.get(list.size() / 2);
    }

    @Contract(pure = true)
    public String plural(@NotNull String str, String symbol) {
        return str.endsWith("s") ? str : str + symbol;
    }

    public void println(Object o) {
        System.out.println(o);
    }
}
