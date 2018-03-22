package info.blendformat.tools.sdna.writer;

public interface ContentStreamEventPublisher {

    void addSubscriber(ContentStreamEventSubscriber subscriber);

    void removeSubscriber(ContentStreamEventSubscriber subscriber);
}
