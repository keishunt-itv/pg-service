package com.itv.product.pg_service.model

object CICache {
    var cacheString: String = ""
    fun updateCache(newCache: String): String {
        cacheString = newCache
        return cacheString
    }
}
