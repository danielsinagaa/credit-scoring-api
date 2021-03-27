package com.enigma.creditscoringapi.models.pages;

import org.springframework.data.domain.Sort.Direction;

import javax.validation.constraints.Max;

public class PageSearch {
    @Max(100)
    private Integer size =  50;
    private Integer page = 0;
    private Direction sort = Direction.DESC;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Direction getSort() {
        return sort;
    }

    public void setSort(Direction sort) {
        this.sort = sort;
    }
}
