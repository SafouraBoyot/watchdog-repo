package com.safara.watchdog.common;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLEventReader;
import javax.xml.transform.Source;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Created by safoura on 1/1/16.
 */
public class XmlMarshaler {

    public static Heartbeat marshaler(InputStream inputStream) throws JAXBException {

        JAXBContext jaxbContext =
                JAXBContext.newInstance(Heartbeat.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Heartbeat heartbeat = (Heartbeat)unmarshaller.unmarshal(inputStream);

        return heartbeat;
    }
    
    
    public static Heartbeat marshaler(ByteBuffer byteBuffer) throws JAXBException {

        JAXBContext jaxbContext =
                JAXBContext.newInstance(Heartbeat.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Heartbeat heartbeat = (Heartbeat)unmarshaller.unmarshal((Source) byteBuffer);

        return heartbeat;
    }

}
