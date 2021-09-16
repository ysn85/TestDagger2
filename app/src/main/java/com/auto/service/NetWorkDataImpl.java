package com.auto.service;

import com.google.auto.service.AutoService;

@AutoService(BaseDataService.class)
public class NetWorkDataImpl implements BaseDataService {

    @Override
    public String getData() {
        return "from netWork";
    }
}
