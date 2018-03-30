package info.blendformat.tools.sdna.reader.events;

import info.blendformat.tools.sdna.defaults.DefaultCatalogPrimitives;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class SDNACatalogSubscriberTest {

    @Test
    public void test() {
        FileStreamEventPublisher publisher
                = new FileStreamEventPublisherSDNACatalog();

        SDNACatalogSubscriber subscriber = new SDNACatalogSubscriber();
        publisher.addSubscriber(subscriber);
        publisher.fireSDNACatalogRead(new DefaultCatalogPrimitives());
        publisher.removeSubscriber(subscriber);

        assertNotNull("A catalog should have been read.",
                subscriber.getCatalog());
    }
}
