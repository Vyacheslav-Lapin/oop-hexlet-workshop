package ru.vlapin.geo;

import io.vavr.CheckedFunction0;
import io.vavr.CheckedFunction1;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import ru.vlapin.geo.model.IpLocation;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import java.net.InetAddress;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

import static lombok.AccessLevel.PRIVATE;

@UtilityClass
@FieldDefaults(level = PRIVATE)
@SuppressWarnings("WeakerAccess")
public class GeoBase {

    String URL_PREFIX = "http://ipgeobase.ru:7020/geo?ip=";
    String TAG_NAME = IpLocation.class.getAnnotation(XmlRootElement.class).name();

    Function<InetAddress, XMLStreamReader> TO_XML_STREAM_READER = CheckedFunction1.<String, URL>of(URL::new)
            .compose((InetAddress inetAddress) -> URL_PREFIX + inetAddress.getHostAddress())
            .andThen(URL::openStream)
            .andThen(StreamSource::new)
            .andThen(streamSource -> XMLInputFactory.newFactory().createXMLStreamReader(streamSource))
            .unchecked();

    @NotNull
    @SneakyThrows
    @SuppressWarnings("WeakerAccess")
    public CompletableFuture<IpLocation> getIpLocation(InetAddress inetAddress) {

        Supplier<IpLocation> ipLocationSupplier = CheckedFunction0.of(() -> {

            @Cleanup val xmlStreamReader = TO_XML_STREAM_READER.apply(inetAddress);

            do
                xmlStreamReader.nextTag();
            while (!xmlStreamReader.getLocalName().equals(TAG_NAME));

            return JAXBContext.newInstance(IpLocation.class)
                    .createUnmarshaller()
                    .unmarshal(xmlStreamReader, IpLocation.class)
                    .getValue();

        }).unchecked();

        return CompletableFuture.supplyAsync(ipLocationSupplier);
    }


}
