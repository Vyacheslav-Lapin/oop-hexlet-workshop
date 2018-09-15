package ru.vlapin.geo.model;

import lombok.NoArgsConstructor;
import lombok.Value;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

@Value
@NoArgsConstructor(force = true)
//@AllArgsConstructor
@XmlAccessorType(FIELD)
@XmlRootElement(name = "ip")
public final class IpLocation {
    @XmlAttribute(name = "value")
    String ip; // TODO: 2018-09-15 change to InetAddress

    @XmlElement(name = "inetnum")
    String diapason;  // TODO: 2018-09-15 change to Tuple2<InetAddress, InetAddress>

    Country country;
    String city;
    String region;
    String district;
    double lat;
    double lng;
}
