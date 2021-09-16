package com.auto.service;

import com.google.auto.service.AutoService;

@AutoService(BaseDataService.class)
public class DBDataImpl implements BaseDataService {

    @Override
    public String getData() {
        return "from DB";
    }
}
