package info.blendformat.tools.sdna.api.service;

import info.blendformat.tools.sdna.api.Messages;
import info.blendformat.tools.sdna.api.dao.SDNACatalogTypeDAO;
import info.blendformat.tools.sdna.api.dao.SDNAFileBlockDAO;
import info.blendformat.tools.sdna.api.dao.SDNAFileDAO;
import info.blendformat.tools.sdna.api.dao.SDNAStructFieldDAO;
import info.blendformat.tools.sdna.api.model.*;
import info.blendformat.tools.sdna.model.*;
import info.blendformat.tools.sdna.reader.events.FileStreamEventSubscriber;

import javax.ejb.Stateful;
import javax.inject.Inject;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

@Stateful
public class FileUploadService implements FileStreamEventSubscriber {

    @Inject
    private SDNAFileDAO fileDAO;

    @Inject
    private SDNAFileBlockDAO blockDAO;

    @Inject
    private SDNACatalogTypeDAO typeDAO;

    @Inject
    private SDNAStructFieldDAO fieldDAO;

    @Inject
    private Messages messages;

    private UploadInfo uploadInfo;
    private SDNAProject project;
    private SDNAFile file = new SDNAFile();
    private String filename;

    public UploadInfo getUploadInfo() {
        return uploadInfo;
    }

    public void setProject(SDNAProject project) {
        this.project = project;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public void onReadProcessStarted() {
        if (null == project) {
            throw new IllegalStateException(
                    messages.getMessage("error_project_must_be_set"));
        }
        uploadInfo = new UploadInfo();
        file.setProject(project);
        file.setName(filename);
    }

    @Override
    public void onHeaderRead(SDNAHeader header) {
        file.setLittleEndian(header.isLittleEndian());
        file.setPointerSize(header.getPointerSize());
        this.file = fileDAO.persist(file);
    }

    @Override
    public void onBlockMetaDataRead(SDNABlockMetaData metaData) {
    }

    @Override
    public void onBlockDataRead(SDNABlockMetaData metaData,
                                byte[] data) {
        SDNAFileBlock block = new SDNAFileBlock();
        block.setFile(file);
        block.setCode(metaData.getCode());
        block.setAddress(metaData.getAddress());
        block.setSize(metaData.getSize());
        block.setContent(ByteBuffer.wrap(Base64.getEncoder().encode(data))
                .toString());
        blockDAO.persist(block);
    }

    @Override
    public void onSDNACatalogRead(SDNACatalog catalog) {
        HashMap<String, Integer> typeSizeMap =
                catalog.getTypeSizeMap();
        HashMap<String, SDNACatalogType> typeObjectMap =
                new HashMap<>();

        for (String type : typeSizeMap.keySet()) {
            Integer size = typeSizeMap.get(type);
            SDNAStructDescriptor descriptor = catalog.getStructDescriptor(type);

            SDNACatalogType catalogType = new SDNACatalogType();
            catalogType.setFile(file);
            catalogType.setName(type);
            catalogType.setSize(size);
            catalogType = typeDAO.persist(catalogType);

            if (null != descriptor) {
                typeObjectMap.put(type, catalogType);
            }
        }

        for (String type : typeSizeMap.keySet()) {
            SDNAStructDescriptor descriptor = catalog.getStructDescriptor(type);
            if (null != descriptor) {
                SDNACatalogType catalogType = typeObjectMap.get(type);
                ArrayList<SDNAFieldDescriptor> fieldDescriptors =
                        descriptor.getFieldDescriptors();
                short index = 0;
                for (SDNAFieldDescriptor fieldDescriptor : fieldDescriptors) {

                    FieldType fieldType = fieldDescriptor.getFieldType();
                    String structType = fieldDescriptor.getStructType();
                    Integer structSize = catalog.getSize(structType);
                    String code = fieldDescriptor.getCode();
                    SDNAStructDescriptor structDescriptor = catalog
                            .getStructDescriptor(structType);

                    SDNAStructField typeField = new SDNAStructField();
                    typeField.setType(catalogType);
                    if (null != structDescriptor) {
                        typeField.setReferenceType(typeObjectMap.get(structType));
                    }
                    typeField.setFieldType(fieldType);
                    typeField.setName(fieldType.getFieldName(
                            structDescriptor,
                            file.getPointerSize(),
                            structSize,
                            code));
                    typeField.setSize(fieldType.getSize(
                            structDescriptor,
                            file.getPointerSize(),
                            structSize,
                            code));
                    typeField.setCode(code);
                    typeField.setIndex(index);

                    fieldDAO.persist(typeField);

                    index++;
                }
            }
        }
    }

    @Override
    public void onReadProcessComplete() {
    }
}
