package com.makaia.flightReservation.dto;

import com.makaia.flightReservation.model.Flight;

import java.util.List;

public class FlightCustomPage {
    private int page;
    private int pageSize;
    private int numberOfElements;
    private long totalElements;
    private List<FlightResponseDTO> content;

    public FlightCustomPage(int page, int pageSize, int numberOfElements, long totalElements, List<FlightResponseDTO> content) {
        this.page = page;
        this.pageSize = pageSize;
        this.numberOfElements = numberOfElements;
        this.totalElements = totalElements;
        this.content = content;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public List<FlightResponseDTO> getContent() {
        return content;
    }

    public void setContent(List<FlightResponseDTO> content) {
        this.content = content;
    }
}
