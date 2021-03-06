package com.theoryinpractise.halbuilder;

import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.api.RepresentationReader;
import com.theoryinpractise.halbuilder.api.ReadableRepresentation;
import org.testng.annotations.Test;

import java.io.InputStreamReader;
import java.io.Reader;

import static org.fest.assertions.api.Assertions.assertThat;

public class RepresentationFactoryTest {

    @Test
    public void testWithXmlReader() {
        RepresentationFactory representationFactory = new DefaultRepresentationFactory()
                .withReader(RepresentationFactory.HAL_XML, DummyRepresentationReader.class);
        ReadableRepresentation representation = representationFactory.readRepresentation(new InputStreamReader(
                RepresentationFactoryTest.class.getResourceAsStream("/example.xml")));
        assertThat(representation.getProperties().get("name")).isEqualTo("dummy");
    }

    @Test
    public void testWithJsonReader() {
        RepresentationFactory representationFactory = new DefaultRepresentationFactory()
                .withReader(RepresentationFactory.HAL_JSON, DummyRepresentationReader.class);
        ReadableRepresentation representation = representationFactory.readRepresentation(new InputStreamReader(
                RepresentationFactoryTest.class.getResourceAsStream("/example.json")));
        assertThat(representation.getProperties().get("name")).isEqualTo("dummy");
    }

    public static class DummyRepresentationReader implements RepresentationReader {
        private final RepresentationFactory representationFactory;

        public DummyRepresentationReader(RepresentationFactory representationFactory) {
            this.representationFactory = representationFactory;
        }

        public ReadableRepresentation read(Reader source) {
            return representationFactory.newRepresentation("/dummy").withProperty("name", "dummy");
        }
    }
}
