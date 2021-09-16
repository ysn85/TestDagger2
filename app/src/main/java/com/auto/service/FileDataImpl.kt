package com.auto.service

import com.google.auto.service.AutoService

@AutoService(BaseDataService::class)
class FileDataImpl : BaseDataService {
    override fun getData(): String {
        return "from File"
    }
}