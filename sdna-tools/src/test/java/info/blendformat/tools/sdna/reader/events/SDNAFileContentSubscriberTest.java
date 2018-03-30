package info.blendformat.tools.sdna.reader.events;

import info.blendformat.tools.sdna.defaults.DefaultCatalogPrimitives;
import info.blendformat.tools.sdna.model.SDNABlockMetaData;
import info.blendformat.tools.sdna.model.SDNAFileContent;
import info.blendformat.tools.sdna.model.SDNAHeader;
import info.blendformat.tools.sdna.reader.AssertSDNAFileContent;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class SDNAFileContentSubscriberTest {

    @Test
    public void test() {
        FileStreamEventPublisher publisher
                = new FileStreamEventPublisherFileContent();

        SDNAFileContentSubscriber subscriber = new SDNAFileContentSubscriber();
        publisher.addSubscriber(subscriber);
        publisher.fireReadProcessStarted();

        SDNAHeader header = new SDNAHeader();
        header.setIdentifier("TESTER");
        publisher.fireHeaderRead(header);

        SDNABlockMetaData metaData = new SDNABlockMetaData();
        metaData.setCode("TST1");
        metaData.setAddress(1);
        metaData.setSdnaIndex(1);
        metaData.setSize(1);
        metaData.setCount(1);
        publisher.fireBlockMetaDataRead(metaData);
        publisher.fireBlockDataRead(metaData, new byte[]{'B'});

        metaData = new SDNABlockMetaData();
        metaData.setCode("TST2");
        metaData.setAddress(2);
        metaData.setSdnaIndex(2);
        metaData.setSize(1);
        metaData.setCount(1);
        publisher.fireBlockMetaDataRead(metaData);
        publisher.fireBlockDataRead(metaData, new byte[]{'L'});

        DefaultCatalogPrimitives catalog = new DefaultCatalogPrimitives();
        catalog.registerSize("ufloat", 12);
        publisher.fireSDNACatalogRead(catalog);
        publisher.fireReadProcessComplete();
        publisher.removeSubscriber(subscriber);

        SDNAFileContent fileContent = subscriber.getFileContent();

        AssertSDNAFileContent assertSDNAFileContent
                = new AssertSDNAFileContent();
        assertSDNAFileContent.assertFileContent(fileContent);
        assertSDNAFileContent.assertIdentifierEquals(
                "TESTER", fileContent);
        assertSDNAFileContent.assertCatalogSize(
                "ufloat",
                12,
                fileContent);

        ArrayList<SDNABlockMetaData> metaDataList = fileContent.getMetaDataList();
        assertEquals("TST1", metaDataList.get(0).getCode());
        assertEquals("TST2", metaDataList.get(1).getCode());

        ArrayList<byte[]> dataList = fileContent.getDataList();
        assertEquals('B', (char) dataList.get(0)[0]);
        assertEquals('L', (char) dataList.get(1)[0]);
    }
}
