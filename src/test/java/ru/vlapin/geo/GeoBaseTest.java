package ru.vlapin.geo;// import ru.vlapin.geo.GeoBase

import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vlapin.geo.model.Country;

import java.net.InetAddress;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;

@FieldDefaults(level = PRIVATE, makeFinal = true)
class GeoBaseTest {

    static String IP = "144.206.192.6";

    @Test
    @SneakyThrows
    @DisplayName("\"getIpLocation\" method works correctly")
    void testGetIpLocation() {

        // given
        val inetAddress = InetAddress.getByName(IP);

        // when
        GeoBase.getIpLocation(inetAddress).thenAccept(ipLocation -> {
            // then
            assertThat(ipLocation.getIp()).isEqualTo(IP);
            assertThat(ipLocation.getDiapason()).isEqualTo("144.206.132.0 - 144.206.255.255");
            assertThat(ipLocation.getCountry()).isEqualTo(Country.RU);
            assertThat(ipLocation.getCity()).isEqualTo("Москва");
            assertThat(ipLocation.getRegion()).isEqualTo("Москва");
            assertThat(ipLocation.getDistrict()).isEqualTo("Центральный федеральный округ");
            assertThat(ipLocation.getLat()).isCloseTo(55.755787, Offset.offset(0.0005));
            assertThat(ipLocation.getLng()).isCloseTo(37.617634, Offset.offset(0.0005));
        }).join();
    }
}