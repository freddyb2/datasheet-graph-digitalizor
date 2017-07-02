package com.fredericboisguerin.pdf.model.datasheet;

import com.fredericboisguerin.pdf.infrastructure.DatasheetRepository;
import com.fredericboisguerin.pdf.model.DatasheetGraphExtraInfo;

import java.util.Collection;

public class DatasheetService {

    private final DatasheetRepository datasheetRepository;

    public DatasheetService(DatasheetRepository datasheetRepository) {
        this.datasheetRepository = datasheetRepository;
    }

    public void createDatasheet(DatasheetReference datasheetReference, DatasheetSupplier supplier) {
        Datasheet datasheet = new Datasheet(new DatasheetMetaInfo(datasheetReference, supplier));
        datasheetRepository.save(datasheet);
    }

    public Collection<Datasheet> getAllDatasheets() {
        return datasheetRepository.findAll();
    }

    public Datasheet findById(String datasheetId) {
        return datasheetRepository.findById(datasheetId)
                                  .orElseThrow(IllegalAccessError::new);
    }

    public void addGraphFromPDF(String datasheetId, DatasheetGraph datasheetGraph) {
        Datasheet datasheet = findById(datasheetId);
        datasheet.addGraph(datasheetGraph);
        datasheetRepository.save(datasheet);
    }

    public Collection<DatasheetGraph> getAllDatasheetGraphs(String datasheetId) {
        Datasheet datasheet = findById(datasheetId);
        return datasheet.getDatasheetGraphs();
    }

    public DatasheetGraph getPDFFile(String datasheetId, String graphId) {
        return findById(datasheetId).getDatasheetGraphs(graphId)
                                    .orElseThrow(IllegalStateException::new);
    }

    public DatasheetMetaInfo getDatasheetMetaInfo(String datasheetId) {
        return findById(datasheetId).getDatasheetMetaInfo();
    }

    public DatasheetGraphExtraInfo getDatasheetGraphExtraInfo(String datasheetId, String graphId) {
        return findById(datasheetId).getDatasheetGraphs(graphId)
                                    .map(DatasheetGraph::getDatasheetGraphExtraInfo)
                                    .orElseThrow(IllegalStateException::new);
    }

    public void setDatasheetMetaInfo(String datasheetId, DatasheetMetaInfo datasheetMetaInfo) {
        Datasheet datasheet = findById(datasheetId);
        datasheet.setMetadata(datasheetMetaInfo);
        datasheetRepository.save(datasheet);
    }
}
