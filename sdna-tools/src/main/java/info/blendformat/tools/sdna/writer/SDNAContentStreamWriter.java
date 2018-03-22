package info.blendformat.tools.sdna.writer;

import info.blendformat.tools.sdna.model.SDNAFileContent;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SDNAContentStreamWriter implements ContentStreamEventPublisher {

    private final ArrayList<ContentStreamEventSubscriber> subscribers
            = new ArrayList<>();

    private InputStream writeContent(WriterConfig config,
                                     SDNAFileContent content) throws IOException {
        return null;
    }

    @Override
    public void addSubscriber(ContentStreamEventSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(ContentStreamEventSubscriber subscriber) {
        subscribers.remove(subscriber);
    }
}
